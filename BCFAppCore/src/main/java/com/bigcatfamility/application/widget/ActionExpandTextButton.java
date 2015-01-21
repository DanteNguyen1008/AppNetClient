package com.bigcatfamility.application.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

import com.bigcatfamily.application.R;

public abstract class ActionExpandTextButton extends SAutoBgButton implements OnClickListener {
	public ActionExpandTextButton(Context context, String text) {
		super(context);
		this.setText(text);
		this.setBackgroundResource(R.drawable.header_icon_bg_text);
		this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		ColorStateList colorStateList = new ColorStateList(new int[][] { new int[] { android.R.attr.state_pressed },
		        new int[] { android.R.attr.state_focused }, new int[0] }, new int[] { 0xffCCCCCC, Color.WHITE,
		        Color.WHITE });
		this.setTextColor(colorStateList);
		this.setOnClickListener(this);
	}

	public void onClick(View v) {
		onClick();
	}

	public abstract void onClick();
}
