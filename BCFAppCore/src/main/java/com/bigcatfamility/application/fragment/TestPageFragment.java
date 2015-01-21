package com.bigcatfamility.application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigcatfamility.application.base.BaseFragment;
import com.bigcatfamily.application.R;

public class TestPageFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (View) inflater.inflate(R.layout.fragment_test_page, container, false);
		mActivity.setTitle(getString(R.string.title_test_page));

		return mRootView;
	}

	@Override
	public void ClearInstance() {

	}

}
