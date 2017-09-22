package com.ricardosaracino.pulllist.datasource;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;


public class JSONParser {

    static InputStream is = null;
    static JSONObject json = null;
    static String outPut = "";

    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) throws JSONParserException, HttpStatusException {

        // Making the HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();


            // post data
            /*HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
*/

            HttpGet request = new HttpGet();

            URIBuilder uriBuilder = new URIBuilder(url);

            uriBuilder.addParameters(params);

            request.setURI(uriBuilder.build());


            HttpResponse httpResponse = httpClient.execute(request);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new HttpStatusException("Error Status Code", httpResponse.getStatusLine().getStatusCode());
            }


            HttpEntity httpEntity = httpResponse.getEntity();

            is = httpEntity.getContent();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            outPut = sb.toString();
            Log.e("JSON", outPut);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            json = new JSONObject(outPut);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return json;
    }
}