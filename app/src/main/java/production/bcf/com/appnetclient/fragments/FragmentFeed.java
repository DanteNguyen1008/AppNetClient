package production.bcf.com.appnetclient.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigcatfamily.networkhandler.db.DataResult;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import production.bcf.com.appnetclient.R;
import production.bcf.com.appnetclient.adapter.AdapterFeed;
import production.bcf.com.appnetclient.models.Feed;
import production.bcf.com.appnetclient.network.DataHelper;
import production.bcf.com.appnetclient.network.MethodParams;
import production.bcf.com.appnetclient.network.ParseData;

/**
 * Created by annguyenquocduy on 1/18/15.
 */

public class FragmentFeed extends ObservationBaseFragment {

    private RecyclerView mListView;
    private SwipeRefreshLayout mRefreshLayout;
    private AdapterFeed mAdapter;
    private int mCurrentPage;
    private ArrayList<Feed> mDataList;

    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getTag(), "onCreateView listFragment");
        mRootView = inflater.inflate(R.layout.fragment_feed, container, false);
        mActivity.setTitle(getString(R.string.title_feed));

        requestData();

        mListView = (RecyclerView) mRootView.findViewById(R.id.recycler_feed_list);

        mLayoutManager = new LinearLayoutManager(mActivity);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        requestData();
                        mCurrentPage++;
                    }
                }
            }
        });

        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_container);

        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mCurrentPage = 0;
                requestData();
            }
        });


        mAdapter = new AdapterFeed(mActivity, null);
        mListView.setAdapter(mAdapter);

        return mRootView;
    }

    private void requestData() {
        try {
            DataHelper.getInstance(mActivity, ParseData.getInstance(mActivity)).getFeedData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ClearInstance() {

    }

    @Override
    protected void update(DataResult result) {
        switch (((MethodParams) result.getMethodName()).getName()) {
            case GET_FEED_DATA:
                ArrayList<Feed> feeds = (ArrayList<Feed>) result.getData();

                if(mCurrentPage > 0)
                    mDataList.addAll(feeds);
                else
                    mDataList = feeds;

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged(mDataList);
                else {
                    mAdapter = new AdapterFeed(mActivity, mDataList);
                    mListView.setAdapter(mAdapter);
                }

                loading = true;
                mRefreshLayout.setRefreshing(false);

                break;


            default:
                break;
        }
    }
}
