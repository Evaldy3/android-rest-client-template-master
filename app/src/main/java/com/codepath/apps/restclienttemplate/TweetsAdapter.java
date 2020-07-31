package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    final int NORMAL = 0,IMAGE = 1 ;
    static int REQUEST_CODE = 99;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case NORMAL:
                View tweetTextView = inflater.inflate(R.layout.item_tweet,parent,false);
                viewHolder = new ViewHolderTweetText(tweetTextView);
                break;

            case IMAGE:
                View tweetImageView = inflater.inflate(R.layout.item_tweet_image,parent,false);
                viewHolder = new ViewHolderTweetImage(tweetImageView);
                break;

            default:

        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(tweets.get(position).getEntities().getType() != null){
            if(tweets.get(position).getEntities().getType().contentEquals("photo")){
                return IMAGE;
            }else{
                return NORMAL;
            }
        }else{
            return NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case NORMAL:
                ViewHolderTweetText viewHolderTweetText = (ViewHolderTweetText) holder;
                bindDatatoViewHolderTweetText(viewHolderTweetText,position);
                break;
            case IMAGE:
                ViewHolderTweetImage viewHolderTweetImage = (ViewHolderTweetImage) holder;
                bindDatatoViewHolderTweetImage(viewHolderTweetImage,position);
                break;

        }

    }
    private void bindDatatoViewHolderTweetText(ViewHolderTweetText viewHolderTweetText, final int position) {
        final Tweet tweet = tweets.get(position);

        viewHolderTweetText.tvScreenName.setText(tweet.getUser().getScreenName());

        viewHolderTweetText.tvBody.setText(tweet.getBody());

        viewHolderTweetText.tvCreateAt.setText("•"+TimeFormatter.getTimeDifference(tweet.getCreatedAt()));

        viewHolderTweetText.tvUserName.setText("@"+tweet.getUser().getName());


        Glide.with(context).load(tweet.getUser().getPublicImageUrl()).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into(viewHolderTweetText.ivProfile);
        viewHolderTweetText.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context , DetailedView.class);
                i.putExtra("position",position);
                i.putExtra("tweet", Parcels.wrap(tweet));
                ((TimelineActivity) context).startActivityForResult(i,REQUEST_CODE);

            }
        });



    }
    private void bindDatatoViewHolderTweetImage(ViewHolderTweetImage viewHolderTweetImage, final int position) {
        final Tweet tweet = tweets.get(position);

        viewHolderTweetImage.tvScreenName.setText(tweet.getUser().getScreenName());

        viewHolderTweetImage.tvBody.setText(tweet.getBody());

        viewHolderTweetImage.tvCreateAt.setText("•"+TimeFormatter.getTimeDifference(tweet.getCreatedAt()));

        viewHolderTweetImage.tvUserName.setText("@"+tweet.getUser().getName());

        Glide.with(context).load(tweet.getUser().getPublicImageUrl()).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into(viewHolderTweetImage.ivProfile);

        Glide.with(context).load(tweet.getEntities().getMedia_url()).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into( viewHolderTweetImage.ivImage);

        viewHolderTweetImage.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context , DetailedView.class);
                i.putExtra("position",position);
                i.putExtra("tweet", Parcels.wrap(tweet));
                ((TimelineActivity) context).startActivityForResult(i,REQUEST_CODE);

            }
        });

    }







    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addAll(List<Tweet> tweets) {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();

    }

    public class ViewHolderTweetText extends RecyclerView.ViewHolder {
        public  ImageView ivProfile;
        public  TextView tvScreenName;
        public  TextView tvBody;
        public   TextView tvCreateAt;
        public TextView tvUserName;
        public RelativeLayout container;


        public ViewHolderTweetText(@NonNull View itemView) {
            super(itemView);
            ivProfile =  itemView.findViewById(R.id.ivProfile);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvUserName=  itemView.findViewById(R.id.tvUserName);
            tvBody =  itemView.findViewById(R.id.tvBody);
            tvCreateAt =  itemView.findViewById(R.id.tvCreateAt);
            container =  itemView.findViewById(R.id.container);
        }
    }

    public class ViewHolderTweetImage extends RecyclerView.ViewHolder{
        public ImageView ivProfile;
        public  TextView tvScreenName;
        public  TextView tvBody;
        public  TextView tvCreateAt;
        public TextView tvUserName;
        public ImageView ivImage;
        public  RelativeLayout container;


        public ViewHolderTweetImage(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvUserName= itemView.findViewById(R.id.tvUserName);
            tvBody =  itemView.findViewById(R.id.tvBody);
            tvCreateAt = itemView.findViewById(R.id.tvCreateAt);
            ivImage =  itemView.findViewById(R.id.ivImage);
            container =  itemView.findViewById(R.id.container);
        }
    }





}

