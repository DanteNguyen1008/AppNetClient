package com.bigcatfamility.application.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bigcatfamility.application.base.BaseActivity;
import com.bigcatfamility.application.base.BaseFragment;
import com.bigcatfamility.application.widget.MenuItem;
import com.bigcatfamily.application.R;

@SuppressLint("ValidFragment")
public abstract class SlideMenuFragment extends BaseFragment implements OnClickListener {
	@SuppressWarnings("unused")
	// private int selectedMenu = DHConstant.MENU_ACTIVITY_SUMMARY_INDEX;
	// public MenuItem menuSummary, menuSetting, menuHelp, menuRate,
	// menuContact;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View view = inflater.inflate(R.layout.fragment_slide_menu, null);
	// // menuSummary = ((DHMenuItem)
	// // view.findViewById(R.id.mnu_activity_summary));
	// // menuSetting = ((DHMenuItem) view.findViewById(R.id.mnu_setting));
	// // menuHelp = ((DHMenuItem) view.findViewById(R.id.mnu_help));
	// // menuRate = ((DHMenuItem) view.findViewById(R.id.mnu_rate));
	// // menuContact = ((DHMenuItem) view.findViewById(R.id.mnu_contact));
	// //
	// // menuSummary.setOnClickListener(this);
	// // menuSetting.setOnClickListener(this);
	// // menuHelp.setOnClickListener(this);
	// // menuRate.setOnClickListener(this);
	// // menuContact.setOnClickListener(this);
	// //
	// // selectedMenu = DHConstant.MENU_ACTIVITY_SUMMARY_INDEX;
	// // menuSummary.setCurrentMenu(true, 0);
	//
	// return view;
	// }

	// @Override
	// public void onClick(View v) {
	// // mActivity.hideKeyboard();
	// //
	// // switch (v.getId()) {
	// // case R.id.mnu_activity_summary:
	// // clearFocusMenu();
	// // menuSummary.setCurrentMenu(true, 0);
	// // changeCurrentMenu(DHConstant.MENU_ACTIVITY_SUMMARY);
	// // break;
	// //
	// // default:
	// // break;
	// // }
	// }

	/**
	 * User Change the current focus menu. We call changeCurrentMenu() function
	 * of MainActivity
	 * 
	 * @param menuName
	 */
	protected void changeCurrentMenu(String menuName) {
		if (mActivity == null)
			return;
		mActivity.changeCurrentMenu(menuName);
	}

	public abstract void clearFocusMenu();

	// @SuppressWarnings("unused")
	// private void expand() {
	// // set Visible
	// // mPanel.setVisibility(View.VISIBLE);
	// //
	// // final int widthSpec = View.MeasureSpec.makeMeasureSpec(0,
	// // View.MeasureSpec.UNSPECIFIED);
	// // final int heightSpec = View.MeasureSpec.makeMeasureSpec(0,
	// // View.MeasureSpec.UNSPECIFIED);
	// // mPanel.measure(widthSpec, heightSpec);
	// //
	// // ValueAnimator mAnimator = slideAnimator(0,
	// // mPanel.getMeasuredHeight());
	// // mAnimator.start();
	// }

	// @SuppressWarnings("unused")
	// private void collapse() {
	// // int finalHeight = mPanel.getHeight();
	// //
	// // ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
	// // mAnimator.addListener(new AnimatorListener() {
	// // @Override
	// // public void onAnimationEnd(Animator animator) {
	// // // Height = 0, but it set visibility to GONE
	// // mPanel.setVisibility(View.GONE);
	// // }
	// //
	// // @Override
	// // public void onAnimationCancel(Animator animation) {
	// // }
	// //
	// // @Override
	// // public void onAnimationRepeat(Animator animation) {
	// // }
	// //
	// // @Override
	// // public void onAnimationStart(Animator animation) {
	// // }
	// // });
	// // mAnimator.start();
	// }

	// @SuppressWarnings("unused")
	// private ValueAnimator slideAnimator(int start, int end) {
	// // ValueAnimator animator = ValueAnimator.ofInt(start, end);
	// // animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
	// // {
	// // @Override
	// // public void onAnimationUpdate(ValueAnimator valueAnimator) {
	// // // Update Height
	// // int value = (Integer) valueAnimator.getAnimatedValue();
	// // ViewGroup.LayoutParams layoutParams = mPanel.getLayoutParams();
	// // layoutParams.height = value;
	// // mPanel.setLayoutParams(layoutParams);
	// // }
	// // });
	// // return animator;
	//
	// return null;
	// }

	// public void updatePushNotify(ManageNotification pushNotify) {
	// menuNotification.setBadgeCount(pushNotify.getUnread_notifications());
	// menuMessage.setBadgeCount(pushNotify.getUnread_messages());
	// }
}
