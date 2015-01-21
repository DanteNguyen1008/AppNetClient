package com.bigcatfamility.application.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class ActionExpandImageButton extends SAutoBgImageButton
		implements OnClickListener {
	public ActionExpandImageButton(Context context, int ResId) {
		super(context);
		this.setBackgroundResource(ResId);
		this.setOnClickListener(this);
	}

	public void onClick(View v) {
		onClick();
	}

	public abstract void onClick();
}
