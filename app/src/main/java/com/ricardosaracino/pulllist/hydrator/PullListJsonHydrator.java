package com.ricardosaracino.pulllist.hydrator;

import com.ricardosaracino.pulllist.model.PullList;
import org.json.JSONObject;

public class PullListJsonHydrator extends AbstractHydrator<PullList, JSONObject> {

    public PullList create(JSONObject jsonObject) throws Exception {

        String plTitle = jsonObject.getString("title");

        String plDesc = jsonObject.getString("description");

        return new PullList(plTitle, plDesc);
    }
}
