package com.volokh.danylo.videolist.video_list_demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.volokh.danylo.videolist.demo.VideoListActivity;
import com.volokh.danylo.videolist.video_list_demo.adapter.holders.VideoViewHolder;
import com.volokh.danylo.videolist.video_list_demo.adapter.items.BaseVideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;

import java.util.List;

/**
 * Created by danylo.volokh on 9/20/2015.
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final VideoPlayerManager mVideoPlayerManager;
    private final List<BaseVideoItem> mList;
    private final Context mContext;

    public VideoRecyclerViewAdapter(VideoPlayerManager videoPlayerManager, Context context, List<BaseVideoItem> list){
        mVideoPlayerManager = videoPlayerManager;
        mContext = context;
        mList = list;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        BaseVideoItem videoItem = mList.get(position);
        View resultView = videoItem.createView(viewGroup, mContext.getResources().getDisplayMetrics().widthPixels);

        final VideoViewHolder viewHolder =  new VideoViewHolder(resultView);
        viewHolder.mContributeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Starting camera intent", Toast.LENGTH_SHORT).show();
                        VideoListActivity.launchCamera();
                    }
                }

        );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder viewHolder, int position) {
        BaseVideoItem videoItem = mList.get(position);
        videoItem.update(position, viewHolder, mVideoPlayerManager);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
