package com.ricardosaracino.pulllist.datasource;

import android.util.Log;
import com.ricardosaracino.pulllist.hydrator.AbstractHydrator;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HttpJsonDataSource<T> extends AbstractDataSource {

    private String url;

    private List<NameValuePair> params;

    private JSONParser jsonParser;

    private AbstractHydrator<T, JSONObject> hydrator;

    public HttpJsonDataSource(AbstractHydrator<T, JSONObject> hydrator, String url) {

        this(hydrator, url, new ArrayList<NameValuePair>());
    }

    public HttpJsonDataSource(AbstractHydrator<T, JSONObject> hydrator, String url, List<NameValuePair> params) {

        this.hydrator = hydrator;

        this.url = url;

        this.params = params;

        jsonParser = new JSONParser();
    }

    @Override
    public List<T> read() {

        JSONObject jsonObject = jsonParser.getJSONFromUrl(url, params);

        List<T> list = new ArrayList<>();

        try {

            JSONArray array = jsonObject.getJSONArray("results");

            for (int i = 0; i < array.length(); i++) {

                list.add(this.hydrator.create(array.getJSONObject(i)));
            }
        } catch (Exception e) {

            Log.e("HttpJsonDataSource", "Error parsing json");
        }

        return list;
    }

}
