package com.bigcatfamility.application.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public abstract class BasePopupActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Dialog);
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
