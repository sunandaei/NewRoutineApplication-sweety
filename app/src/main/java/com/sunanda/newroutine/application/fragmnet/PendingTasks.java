package com.sunanda.newroutine.application.fragmnet;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.helper.InfiniteFeedInfo;
import com.sunanda.newroutine.application.somenath.helper.ItemView;
import com.sunanda.newroutine.application.somenath.helper.LoadMoreView;
import com.sunanda.newroutine.application.somenath.helper.Utils;

import java.util.List;

public class PendingTasks extends Fragment {

    View myView;
    private InfinitePlaceHolderView mLoadMoreView;

    public static PendingTasks newInstance() {
        return new PendingTasks();
    }

    public PendingTasks() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_pending_tasks, container, false);

        mLoadMoreView = (InfinitePlaceHolderView)myView.findViewById(R.id.loadMoreView);
        setupView();

        return myView;
    }

    private void setupView(){

        final List<InfiniteFeedInfo> feedList = Utils.loadInfiniteFeeds(getContext());
        //Log.d("DEBUG", "LoadMoreView.LOAD_VIEW_SET_COUNT " + LoadMoreView.LOAD_VIEW_SET_COUNT);
        for(int i = 0; i < LoadMoreView.LOAD_VIEW_SET_COUNT; i++){
            mLoadMoreView.addView(new ItemView(getActivity(), feedList.get(i)));
        }
        mLoadMoreView.setLoadMoreResolver(new LoadMoreView(mLoadMoreView, feedList));

        myView.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedList.clear();
                mLoadMoreView.removeAllViews();
            }
        });
    }
}