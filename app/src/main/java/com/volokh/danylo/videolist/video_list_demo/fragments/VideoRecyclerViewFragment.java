package com.volokh.danylo.videolist.video_list_demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.Toast;

import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.videolist.R;
import com.volokh.danylo.videolist.demo.VideoListActivity;
import com.volokh.danylo.videolist.video_list_demo.adapter.VideoRecyclerViewAdapter;
import com.volokh.danylo.videolist.video_list_demo.adapter.items.BaseVideoItem;
import com.volokh.danylo.videolist.video_list_demo.adapter.items.ItemFactory;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This fragment shows of how to use {@link VideoPlayerManager} with a RecyclerView.
 */
public class VideoRecyclerViewFragment extends Fragment {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = "Shyam";// VideoRecyclerViewFragment.class.getSimpleName();
    private final ArrayList<BaseVideoItem> mList = new ArrayList<>();
    private int mStartingSize;
    /**
     * Only the one (most visible) view should be active (and playing).
     * To calculate visibility of views we use {@link SingleListViewItemActiveCalculator}
     */
    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RequestQueue mRequestQueue;
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    /**
     * ItemsPositionGetter is used by {@link ListItemsVisibilityCalculator} for getting information about
     * items position in the RecyclerView and LayoutManager
     */
    private ItemsPositionGetter mItemsPositionGetter;

    /**
     * Here we use {@link SingleVideoPlayerManager}, which means that only one video playback is possible.
     */
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {

        }
    });

    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRequestQueue = Volley.newRequestQueue(getContext());

        try {
            mList.add(ItemFactory.createItemFromAsset("Obama for Hope", "video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager, "Milwaukee, Wisconsin", false));
            mList.add(ItemFactory.createItemFromAsset("Justin Timberlake Concert", "video_sample_2.mp4", R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager, "Hollywood, LA", false));
            mList.add(ItemFactory.createItemFromAsset("ManU vs FC Barcelona", "video_sample_3.mp4", R.drawable.video_sample_3_pic, getActivity(), mVideoPlayerManager, "Barcelona, Spain", false));

            mList.add(ItemFactory.createItemFromAsset("Obama for Hope", "video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager, "Milwaukee, Wisconsin", false));
            mList.add(ItemFactory.createItemFromAsset("Justin Timberlake Concert", "video_sample_2.mp4", R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager, "Hollywood, LA", false));
            mList.add(ItemFactory.createItemFromAsset("ManU vs FC Barcelona", "video_sample_3.mp4", R.drawable.video_sample_3_pic, getActivity(), mVideoPlayerManager, "Barcelona, Spain", false));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mStartingSize = mList.size();
        View rootView = inflater.inflate(R.layout.fragment_video_recycler_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(mVideoPlayerManager, getActivity(), mList);

        mRecyclerView.setAdapter(mVideoRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()){

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!mList.isEmpty()){
                    mVideoVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);

                    if (!recyclerView.canScrollVertically(-1)) {
                    } else if (!recyclerView.canScrollVertically(1)) {
                        onScrolledToBottom();
                    } else if (dy < 0) {
                    } else if (dy > 0) {
                    }
                }
            }

            public void onScrolledToBottom() {
                Toast.makeText(getContext(), "Updating list...", Toast.LENGTH_SHORT).show();
                // popualate the only live tile - demo code ;)
                //
                fetchFeedData();


            }

        });
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);

        return rootView;
    }

    private void fetchFeedData() {
        String apiurl = "http://" + VideoListActivity.GetRestApiEndPoint() + "/api/getlom";
        Log.d(TAG, "Fetching data from - " + apiurl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Got response" + response.toString());
                        try {
                            Iterator<String> keys = response.keys();
                            while(keys.hasNext()) {
                                String key = keys.next();
                                if (key.equals(VideoListActivity.DemoKeyForLiveStitching)) {
                                    //Log.d(TAG, "key - " + key + " value - " + response.getJSONObject(key));
                                    JSONObject dataCollection = response.getJSONObject(key);
                                    if (dataCollection.has("stitchedVideo")) {
                                        String sv = dataCollection.getString("stitchedVideo");
                                        Log.d(TAG, "stitched video @ = " + sv);
                                        try {
                                            String videoUrl = "http://" + VideoListActivity.GetHttpServerEndPoint() + "/" + sv;
                                            if (!URLUtil.isValidUrl(videoUrl))
                                                return;
                                            Log.d(TAG, "creating video tile from " + videoUrl);
                                            if (mList.size() > mStartingSize) {
                                                mList.remove(mList.size() - 1);
                                                mVideoRecyclerViewAdapter.notifyDataSetChanged();
                                            }
                                            mList.add(ItemFactory.createItemFromAsset(dataCollection.getString("name"), videoUrl, R.drawable.video_sample_2_pic, getActivity(), mVideoPlayerManager, "Paris, France", true));
                                            mVideoRecyclerViewAdapter.notifyDataSetChanged();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }                       }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }
        );
        mRequestQueue.add(request);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(!mList.isEmpty()){
            // need to call this method from list view handler in order to have filled list

            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // we have to stop any playback in onStop
        mVideoPlayerManager.resetMediaPlayer();
    }
}