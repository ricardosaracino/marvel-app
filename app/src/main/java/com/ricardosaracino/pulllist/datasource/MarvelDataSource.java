package com.ricardosaracino.pulllist.datasource;

import android.util.Log;
import com.ricardosaracino.pulllist.hydrator.AbstractHydrator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MarvelDataSource<T> extends AbstractDataSource {

    private static final String PRIVATE_API_KEY = "cdd305574f7626a3554062099082edeedb28c200";
    private static final String PUBLIC_API_KEY = "39754936249867122be92159703640da";
    // java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
    private static final String API_BASE_URL = "https://gateway.marvel.com:443";
    protected String url;
    protected List<NameValuePair> params;
    private int resultOffset;
    private int resultLimit;
    private int resultTotal;
    private int resultCount;
    private JSONParser jsonParser;

    private AbstractHydrator hydrator;


    public MarvelDataSource(AbstractHydrator hydrator, String apiCall) {

        this(hydrator, apiCall, new ArrayList<BasicNameValuePair>());
    }

    private MarvelDataSource(AbstractHydrator hydrator, String apiCall, List params) {


        this.jsonParser = new JSONParser();


        this.hydrator = hydrator;

        this.url = API_BASE_URL + apiCall;


        String timestamp = String.valueOf(System.currentTimeMillis());

        params.add(new BasicNameValuePair("ts", timestamp));

        params.add(new BasicNameValuePair("apikey", PUBLIC_API_KEY));

        params.add(new BasicNameValuePair("hash", generateHash(timestamp, PUBLIC_API_KEY, PRIVATE_API_KEY)));

        this.params = params;

    }


    private String generateHash(String timestamp, String publicKey, String privateKey) {
        try {
            String value = timestamp + privateKey + publicKey;
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; ++i) {
                md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {

            Log.e("asdfasdf", "asdfsadfsadf");

            //throw new MarvelApiException("cannot generate the api key", e);
        }
        return "";
    }

    @Override
    public T read() throws DataSourceException {

        try {
            JSONObject jsonObject = jsonParser.getJSONFromUrl(url, params);

            JSONObject data = jsonObject.getJSONObject("data");

            resultOffset = data.getInt("offset");
            resultLimit = data.getInt("limit");
            resultTotal = data.getInt("total");
            resultCount = data.getInt("count");

            JSONArray results = data.getJSONArray("results");

            return (T) this.hydrator.create(results);

        } catch (Throwable e) {
            throw new DataSourceException(e.getMessage());
        }
    }


    public boolean addStringParam(String k, String v) {
        return params.add(new BasicNameValuePair(k, v));
    }

    public boolean addIntParam(String k, int v) {
        return params.add(new BasicNameValuePair(k, String.valueOf(v)));
    }

    public int getResultOffset() {
        return resultOffset;
    }

    public int getResultLimit() {
        return resultLimit;
    }

    public int getResultTotal() {
        return resultTotal;
    }

    public int getResultCount() {
        return resultCount;
    }
}
