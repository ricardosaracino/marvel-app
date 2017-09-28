package com.ricardosaracino.pulllist.datasource;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;


public class JSONParser {

    private String encoding;
    private String requestMethod;


    public JSONParser() {
        this("UTF-8", "GET");
    }


    public JSONParser(String encoding, String requestMethod) {
        this.encoding = encoding;
        this.requestMethod = requestMethod;
    }


    public JSONObject getJSONFromUrl(String urlString, List<NameValuePair> params) throws HttpStatusException, IOException, URISyntaxException, JSONException {


        URIBuilder uriBuilder = new URIBuilder(urlString);

        uriBuilder.addParameters(params);

        URL url = new URL(uriBuilder.build().toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(requestMethod);

        urlConnection.setConnectTimeout(1500);
        urlConnection.setReadTimeout(1500);

        JSONObject json;

        try {

            InputStream in = urlConnection.getInputStream();

            if (urlConnection.getResponseCode() != HTTP_OK) {
                throw new HttpStatusException("", urlConnection.getResponseCode());
            }

            InputStream bin = new BufferedInputStream(in);

            String jsonString = readStream(bin);

            json = new JSONObject(jsonString);

        } finally {
            urlConnection.disconnect();
        }

        return json;
    }


    private String readStream(InputStream is) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(is, this.encoding), 8);

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = in.readLine()) != null) {
            sb.append(line + "\n");
        }

        is.close();

        return sb.toString();
    }
}