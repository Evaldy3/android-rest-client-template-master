package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.List;

public class DetailedView extends AppCompatActivity {
    List<Tweet> tweets;
    Tweet tweet;
    Context context;
    int position;
  ImageView ivProfile;
  TextView tvScreenName;
  TextView tvUserName;
  TextView tvBody;
  TextView tvCreateAt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivProfile = findViewById(R.id.ivProfile);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvUserName= findViewById(R.id.tvUserName);
        tvBody = findViewById(R.id.tvBody);
        tvCreateAt = findViewById(R.id.tvCreateAt);


        position = getIntent().getIntExtra("position",-1);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.user.name);
        tvScreenName.setText(tweet.user.getScreenName());
        tvCreateAt.setText(""+TimeFormatter.getTimeDifference(tweet.createdAt));
        Glide.with(getApplicationContext())

                    .load(tweet.getUser().getPublicImageUrl())
                    .apply(new RequestOptions()
                            .centerInside().transform(new RoundedCorners(30)))
                    .into(ivProfile);




    }
}