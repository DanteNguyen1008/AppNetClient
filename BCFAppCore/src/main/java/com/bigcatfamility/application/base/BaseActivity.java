/*
 * NLBaseActivity
 *
 * Abstract class for inheritant activities. Ex: Splash, Login, SignUp,...
 *
 * @author 
 * @date 
 * @lastChangedRevision: 1.0
 * @lastChangedDate: 2011/08/12
 */
package com.bigcatfamility.application.base;

import java.util.HashMap;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigcatfamility.application.contranst.BaseAppConstant;
import com.bigcatfamility.application.utility.Utility;
import com.bigcatfamily.application.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public abstract class BaseActivity extends ActionBarActivity {
	private Toast toast;
	private long lastBackPressTime = 0;
	private boolean isVisible;
	protected HashMap<String, Stack<Fragment>> mStacks;
	protected Fragment mFrag;
	protected ProgressDialog mLoadingDialog;
	protected String currentMenu, title;
	protected FragmentManager mFragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTheme(R.style.AppTheme);
		SetContentView();
		mFragmentManager = getSupportFragmentManager();
		initLeftMenu(savedInstanceState);
		initFragmentScreen();
	}

	@Override
	protected void onPause() {
		super.onPause();
		isVisible = false;
	}

	protected void onResume() {
		super.onResume();
		isVisible = true;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * Prevent accidental app exit by requiring users to press back twice when
	 * the app is exiting w/in 8sec
	 */
	public void onexitNotify() {
		if (this.lastBackPressTime < System.currentTimeMillis() - 1000) {
			toast = Toast.makeText(this, getString(R.string.str_press_back_exit), Toast.LENGTH_LONG);
			toast.show();
			this.lastBackPressTime = System.currentTimeMillis();
		} else {
			if (toast != null) {
				toast.cancel();
			}
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		isVisible = false;
		MyGarbageCollection();
		super.onDestroy();
	}

	public boolean isActivityVisible() {
		return isVisible;
	}

	public Context getContext() {
		return BaseActivity.this;
	}

	public void hideKeyboard() {
		System.out.println("==> hideKeyboard");
		try {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null && getCurrentFocus() != null)
				// imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
				// InputMethodManager.RESULT_UNCHANGED_SHOWN);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				        InputMethodManager.RESULT_UNCHANGED_SHOWN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public void setTitle(String title) {
		this.title = title;
		getSupportActionBar().setTitle(title);
	}

	public void ShowArletDialog(final ArletDialog nlDialog) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		if (!Utility.IsEmpty(nlDialog.getTitle())) {
			dialog.setTitle(nlDialog.getTitle());
		}
		dialog.setMessage(nlDialog.getMessage());
		if (!Utility.IsEmpty(nlDialog.getOKString())) {
			dialog.setPositiveButton(nlDialog.getOKString(), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					if (nlDialog.GetINLDialogActionListener() != null) {
						nlDialog.GetINLDialogActionListener().OnOKButtonClick();
					}
				}
			});
		}

		if (!Utility.IsEmpty(nlDialog.getCancelString())) {
			dialog.setNegativeButton(nlDialog.getCancelString(), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					if (nlDialog.GetINLDialogActionListener() != null) {
						nlDialog.GetINLDialogActionListener().OnCancelButtonClick();
					}
				}
			});
		}

		Dialog _dialog = dialog.create();
		_dialog.setCanceledOnTouchOutside(false);
		_dialog.setCancelable(false);
		_dialog.show();
	}

	public void MyGarbageCollection() {
		try {
			SetNullForCustomVariable();
			// UnbindReferences(findViewById(GetRootViewId()));
		} catch (Exception e) {
			Log.e("ERROR", "Unbind References" + e.toString());
		}
	}

	@SuppressWarnings("unused")
	private void UnbindReferences(View view) {
		try {
			if (view != null) {
				UnbindReferences(view);
				if (view instanceof ViewGroup)
					UnbindViewGroupReferences((ViewGroup) view);
			}
			System.gc();
		} catch (Throwable e) {
			// whatever exception is thrown just ignore it because a crash is
			// always worse than this method not doing what it's supposed to do
		}
	}

	private void UnbindViewGroupReferences(ViewGroup viewGroup) {
		if (viewGroup == null)
			return;

		int nrOfChildren = viewGroup.getChildCount();
		for (int i = 0; i < nrOfChildren; i++) {
			View view = viewGroup.getChildAt(i);
			UnbindViewReferences(view);
			if (view instanceof ViewGroup)
				UnbindViewGroupReferences((ViewGroup) view);
		}
		try {
			viewGroup.removeAllViews();
		} catch (Throwable mayHappen) {
			// AdapterViews, ListViews and potentially other ViewGroups don't
			// support the removeAllViews operation
		}
	}

	@SuppressWarnings("deprecation")
	private void UnbindViewReferences(View view) {
		if (view == null)
			return;
		// set all listeners to null (not every view and not every API level
		// supports the methods)
		try {
			view.setOnClickListener(null);
		} catch (Throwable mayHappen) {
		}
		;
		try {
			view.setOnCreateContextMenuListener(null);
		} catch (Throwable mayHappen) {
		}
		;
		try {
			view.setOnFocusChangeListener(null);
		} catch (Throwable mayHappen) {
		}
		;
		try {
			view.setOnKeyListener(null);
		} catch (Throwable mayHappen) {
		}
		;
		try {
			view.setOnLongClickListener(null);
		} catch (Throwable mayHappen) {
		}
		;
		try {
			view.setOnClickListener(null);
		} catch (Throwable mayHappen) {
		}
		;

		// set background to null
		Drawable d = view.getBackground();
		if (d != null)
			d.setCallback(null);
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			d = imageView.getDrawable();
			if (d != null)
				d.setCallback(null);
			imageView.setImageDrawable(null);
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				imageView.setBackgroundDrawable(null);
			} else {
				imageView.setBackground(null);
			}
		}

		// destroy webview
		if (view instanceof WebView) {
			((WebView) view).destroyDrawingCache();
			((WebView) view).destroy();
		}
	}

	protected void pushFragments(String tag, Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
		// popTutorialFragments();
		if (shouldAdd)
			mStacks.get(tag).push(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate)
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();

		// removeActionExpandButton();
	}

	public void changeCurrentMenu(String menuName) {
		if (!menuName.equals(currentMenu)) {
			// mDrawerLayout.closeDrawer(mMenuLayout);
			closeLeftMenu();
			currentMenu = menuName;
			if (mStacks.get(menuName).size() == 0) {
				/*
				 * We are adding a new fragment which is not present in stack.
				 */
				ChangeFragmentOnChangeCurrentMenu(menuName);
			} else {
				/*
				 * We are switching tabs, and target tab is already has atleast
				 * one fragment. No need of animation, no need of stack pushing.
				 * Just show the target fragment
				 */
				// pushFragments(menuName, mStacks.get(menuName).lastElement(),
				// false, false);
				Fragment fragment = mStacks.get(menuName).firstElement();
				mStacks.get(menuName).clear();
				mStacks.get(menuName).add(fragment);
				pushFragments(menuName, mStacks.get(menuName).firstElement(), false, false);
			}
		}
	}

	public void popFragments() {
		Fragment fragment = mStacks.get(currentMenu).elementAt(mStacks.get(currentMenu).size() - 2);

		mStacks.get(currentMenu).pop();
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();

		// removeActionExpandButton();
	}

	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			currentMenu = bundle.getString(BaseAppConstant.INTENT_AL_SCREEEN_NAME_EXTRA);
			if (currentMenu != null) {
				ChangeFragmentOnChangeCurrentMenu(currentMenu);
			}
		}
	}

	public void onBackPressed() {
		if (mStacks.get(currentMenu).size() > 1) {
			popFragments();
		} else {
			onexitNotify();
		}
	}

	public void ShowLoadingDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!mLoadingDialog.isShowing())
					mLoadingDialog.show();
			}
		});
	}

	public void CloseLoadingDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mLoadingDialog.isShowing())
					mLoadingDialog.cancel();
			}
		});
	}

	public void pushFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
		pushFragments(currentMenu, fragment, shouldAnimate, shouldAdd);
	}

	public BaseFragment getCurrentFragment() {
		return (BaseFragment) mStacks.get(currentMenu).elementAt(mStacks.get(currentMenu).size() - 1);
	}

	public void ReteApplication() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("market://details?id=" + getPackageName()));
		startActivity(i);
	}

	/**
	 * if (menuName.equals(AppConstant.STREAMABLE_LIST_MENU)) {
	 * pushFragments(menuName, new StreamTrackListFragment(), false, true); }
	 * else if (menuName.equals(AppConstant.DOWNLOADABLE_LIST_MENU)) {
	 * pushFragments(menuName, new DownloadableTrackListFragment(), false,
	 * true); } else if (menuName.equals(AppConstant.MY_TRACK_LIST_MENU)) {
	 * pushFragments(menuName, new MyTracksFragment(), false, true); } else if
	 * (menuName.equals(AppConstant.SEARCH_LIST_MENU)) { pushFragments(menuName,
	 * new SearchTrackFragment(), false, true); } else if
	 * (currentMenu.equals(AppConstant.ITUNE_TOP_HIT_MENU)) pushFragments(new
	 * TopHitFragment(), false, true);
	 * */
	protected abstract void ChangeFragmentOnChangeCurrentMenu(String menuName);

	public abstract String GetActivityID();

	protected abstract void SetNullForCustomVariable();

	protected abstract void SetContentView();

	protected abstract void closeLeftMenu();

	protected abstract void openLeftMenu();

	/*
	 * if (savedInstanceState == null) { FragmentTransaction t =
	 * mFragmentManager.beginTransaction(); mFrag = new SlideMenuFragment();
	 * t.replace(R.id.menu_frame, mFrag); t.commit(); } else mFrag =
	 * (SlideMenuFragment) mFragmentManager.findFragmentById(R.id.menu_frame);
	 */
	protected abstract void initLeftMenu(Bundle savedInstanceState);

	protected abstract void initFragmentScreen();
}
