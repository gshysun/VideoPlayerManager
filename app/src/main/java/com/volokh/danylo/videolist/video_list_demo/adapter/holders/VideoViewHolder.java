package com.volokh.danylo.videolist.video_list_demo.adapter.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.videolist.R;


public class VideoViewHolder extends RecyclerView.ViewHolder{

    public final VideoPlayerView mPlayer;
    public final TextView mTitle;
    public final ImageView mCover;
    public final ImageView mContributeButton;
    public final TextView mPlace;
    public final View mContributeLL;

    public VideoViewHolder(View view) {
        super(view);
        mPlayer = (VideoPlayerView) view.findViewById(R.id.player);
        mTitle = (TextView) view.findViewById(R.id.title);
        mCover = (ImageView) view.findViewById(R.id.cover);
        mContributeButton = (ImageView) view.findViewById(R.id.contribute);
        mPlace = (TextView) view.findViewById(R.id.place);
        mContributeLL = (View) view.findViewById(R.id.contributell);

    }
}
