package com.yunk.carousellnews.DataUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.yunk.carousellnews.DataUtilities.ArticleConstant.TIME;

/**
 * Created by bradley on 2019/3/8.
 */

public class JsonArraySorter {
    private JSONArray originJsonArray, sortedJsonArray;
    private String keyToSort;

    public JsonArraySorter(JSONArray jsonArr, String key) {
        originJsonArray = jsonArr;
        keyToSort=key;
        doSorting();
    }

    private void doSorting() {
        sortedJsonArray = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (int i = 0; i < originJsonArray.length(); i++) {
            try {
                jsonList.add(originJsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort( jsonList, new Comparator<JSONObject>() {
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = a.getString(keyToSort);
                    valB = b.getString(keyToSort);
                }
                catch (JSONException e) {
                    //do something
                }
                if (keyToSort.equals(TIME)) { //Because the time order is descending so return opposite result
                    return valB.compareTo(valA);
                } else {
                    return valA.compareTo(valB);
                }
            }
        });

        for (int i = 0; i < originJsonArray.length(); i++) {
            sortedJsonArray.put(jsonList.get(i));
        }
    }

    public JSONArray getSortedJsonArray() {
        return sortedJsonArray;
    }
}
