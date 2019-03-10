package com.yunk.carousellnews.DataUtilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.yunk.carousellnews.ViewUtilities.Article;
import com.yunk.carousellnews.ViewUtilities.ArticleViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.yunk.carousellnews.DataUtilities.ArticleConstant.TIME;

/**
 * Created by bradley on 2019/3/8.
 */

public class JsonDataParser {
    private static final String TAG = "JsonDataParser";
    private Context mContext;
    private ProgressDialog pDialog;
    private JSONArray rawJsonArray;
    private ArticleViewAdapter mAdapter;
    private Article article;
    private List<Article> ArticleDataset;
    private JsonArraySorter sorter;

    private String jsonUrl;


    public JsonDataParser(String url, Context context, ArticleViewAdapter adapter, List<Article> articleList) {
        jsonUrl=url;
        mContext=context;
        mAdapter = adapter;
        ArticleDataset = articleList;
        new RetrieveDataTask().execute(jsonUrl);
    }

    class RetrieveDataTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext); // show loading dialog before done
            pDialog.setMessage("Please wait for loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) { //for download json file from Internet
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line); //here to get whole response
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            try {
                rawJsonArray = new JSONArray(result); //get json array from downloaded string
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sorter = new JsonArraySorter(rawJsonArray, TIME); // The document demand the recent order for default, so sort it first
            rawJsonArray = sorter.getSortedJsonArray();
            articleDataArrange(rawJsonArray);
        }
    }

    public void articleDataArrange(JSONArray jsonArray) {
        ArticleDataset.clear();
        try {
            for(int i = 0; i < jsonArray.length(); i++){
                article = new Article(jsonArray.getJSONObject(i)); //new article object and add them into list, caution! when data is massive this might cause memory leak
                ArticleDataset.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged(); //notify UI change
    }

    public JSONArray getRawJsonArray() {
        return rawJsonArray;
    }
}
