package com.yunk.carousellnews.ViewUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import static com.yunk.carousellnews.DataUtilities.ArticleConstant.*;

/**
 * Created by bradley on 2019/3/8.
 */

public class Article { //Article object
    private int id, time_created, rank;
    private String title, description, banner_url;

    public Article(JSONObject rawJsonObject) {
        try {
            id = Integer.parseInt(rawJsonObject.getString(ID));
            title = rawJsonObject.getString(TITLE);
            description = rawJsonObject.getString(DESCRIPTION);
            banner_url = rawJsonObject.getString(BANNER_URL);
            time_created = Integer.parseInt(rawJsonObject.getString(TIME));
            rank = Integer.parseInt(rawJsonObject.getString(RANK));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime_created() {
        return time_created;
    }

    public void setTime_created(int time_created) {
        this.time_created = time_created;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }
}
