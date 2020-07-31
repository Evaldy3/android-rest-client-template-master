package com.codepath.apps.restclienttemplate;

import com.codepath.apps.restclienttemplate.models.Entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel

public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public long id;
    private Entities entities;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Entities getEntities() {
        return entities;
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user =User.fromJson(jsonObject.getJSONObject("user")) ;
        tweet.id = jsonObject.getLong("id");
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        return tweet;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++ ){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }


}
