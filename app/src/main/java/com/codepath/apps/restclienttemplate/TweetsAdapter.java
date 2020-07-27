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
    final int NORMAL = 0,IMAGE = 1 ,VIDEO =2;
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
         //   case VIDEO:
            //    View tweetVideoView = inflater.inflate(R.layout.item_tweet_video,parent,false);
            //    viewHolder = new ViewHolderTweetVideo(tweetVideoView );
            //    break;

        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(tweets.get(position).getEntities().getType() != null){
            if(tweets.get(position).getEntities().getType().contentEquals("photo")){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 1;
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
            //    case VIDEO:
            //   ViewHolderTweetVideo viewHolderTweetVideo = (ViewHolderTweetVideo) holder;
            //   bindDatatoViewHolderTweetVideo(viewHolderTweetVideo,position);
            //    break;
        }

    }
    private void bindDatatoViewHolderTweetText(ViewHolderTweetText viewHolderTweetText, final int position) {
        final Tweet tweet = tweets.get(position);
        RelativeLayout container = viewHolderTweetText.container;

        TextView tvScreenName = viewHolderTweetText.tvScreenName;
        tvScreenName.setText(tweet.user.getScreenName());

        TextView tvBody= viewHolderTweetText.tvBody;
        tvBody.setText(tweet.getBody());

        TextView tvCreateAt = viewHolderTweetText.tvCreateAt;
        tvCreateAt.setText(""+TimeFormatter.getTimeDifference(tweet.createdAt));

        TextView tvUserName = viewHolderTweetText.tvUserName;
        tvUserName.setText(tweet.user.getName());

        ImageView ivProfile =  viewHolderTweetText.ivProfile;
        Glide.with(context).load(tweet.user.publicImageUrl).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into( ivProfile);

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
        RelativeLayout container = viewHolderTweetImage.container;

        TextView tvScreenName = viewHolderTweetImage.tvScreenName;
        tvScreenName.setText(tweet.user.getScreenName());

        TextView tvBody= viewHolderTweetImage.tvBody;
        tvBody.setText(tweet.getBody());

        TextView tvCreateAt = viewHolderTweetImage.tvCreateAt;
        tvCreateAt.setText(""+TimeFormatter.getTimeDifference(tweet.createdAt));

        TextView tvUserName = viewHolderTweetImage.tvUserName;
        tvUserName.setText(tweet.user.getName());

        ImageView ivProfile = viewHolderTweetImage.ivProfile;
        Glide.with(context).load(tweet.user.publicImageUrl).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into( ivProfile);

        ImageView ivImage = viewHolderTweetImage.ivImage;
        Glide.with(context).load(tweet.getEntities().getMedia_url()).apply(new RequestOptions().centerInside().transform(new RoundedCorners(30))).into( ivImage);

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

    //private void bindDatatoViewHolderTweetVideo(ViewHolderTweetVideo viewHolderTweetVideo, int position) {

    // }





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
        ImageView ivProfile;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvCreateAt;
        TextView tvUserName;
        RelativeLayout container;


        public ViewHolderTweetText(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvUserName= itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvCreateAt = itemView.findViewById(R.id.tvCreateAt);
            container = (RelativeLayout) itemView.findViewById(R.id.container);
        }
    }

    public class ViewHolderTweetImage extends RecyclerView.ViewHolder{
        ImageView ivProfile;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvCreateAt;
        TextView tvUserName;
        ImageView ivImage;
        RelativeLayout container;


        public ViewHolderTweetImage(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvUserName= itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvCreateAt = itemView.findViewById(R.id.tvCreateAt);
            ivImage = ivProfile.findViewById(R.id.ivImage);
            container = (RelativeLayout) itemView.findViewById(R.id.container);
        }
    }

    //  public class ViewHolderTweetVideo extends RecyclerView.ViewHolder{
    //   ImageView ivProfile;
    //     TextView tvScreenName;
    //     TextView tvBody;
    //     TextView tvCreateAt;
    //    TextView tvUserName;
    //     VideoView vvVideo;
    //    RelativeLayout container;


    //     public ViewHolderTweetVideo(@NonNull View itemView) {
    //        super(itemView);
    //        ivProfile = itemView.findViewById(R.id.ivProfile);
    //        tvScreenName = itemView.findViewById(R.id.tvScreenName);
    //        tvUserName= itemView.findViewById(R.id.tvUserName);
    //         tvBody = itemView.findViewById(R.id.tvBody);
    //        tvCreateAt = itemView.findViewById(R.id.tvCreateAt);
    //        vvVideo = itemView.findViewById(R.id.vvVideo);
    //        container = (RelativeLayout) itemView.findViewById(R.id.container);
    //     }
    // }



}

