package com.bigcatfamility.application.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcatfamily.application.R;

public class MenuItem extends LinearLayout {
	public Context mContext;
	private ImageView ivMenuIcon;
	private TextView tvMenuText;
	private ImageView tvMenuDrop;
	private BadgeView bgView;
	private LinearLayout llWrapper;

	@SuppressWarnings("unused")
	private Listener listener;
	private boolean isCurrentMenu;
	private int mBg, mBgSelector;

	public interface Listener {
		void didClearText();
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public MenuItem(Context context) {
		super(context);
		mContext = context;
	}

	public MenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		init(attrs);
	}

	@SuppressLint("NewApi")
	public MenuItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.menu_item, this);
		llWrapper = (LinearLayout) findViewById(R.id.item_menu_wrapper);
		ivMenuIcon = (ImageView) findViewById(R.id.menu_item_iv);
		tvMenuText = (TextView) findViewById(R.id.menu_item_text);
		tvMenuDrop = (ImageView) findViewById(R.id.menu_item_drop);
		bgView = (BadgeView) findViewById(R.id.menu_item_counter);

		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MenuItem);
		CharSequence s = a.getString(R.styleable.MenuItem_menu_text);

		mBg = a.getResourceId(R.styleable.MenuItem_menu_background, R.drawable.menu_light);
		mBgSelector = a
		        .getResourceId(R.styleable.MenuItem_menu_background_selector, R.drawable.menuitem_light_selector);

		if (s != null)
			tvMenuText.setText(s.toString());

		int icon = a.getResourceId(R.styleable.MenuItem_menu_icon, 0);
		if (icon != 0)
			ivMenuIcon.setImageResource(icon);
		else
			ivMenuIcon.setVisibility(View.GONE);

		boolean showDefault = a.getBoolean(R.styleable.MenuItem_menu_drop, false);
		tvMenuDrop.setVisibility(showDefault ? View.VISIBLE : View.GONE);

		float textSize = a.getDimension(R.styleable.MenuItem_menu_text_size, 0);
		if (textSize > 0)
			tvMenuText.setTextSize(textSize);

		int textColor = a.getResourceId(R.styleable.MenuItem_menu_text_color, Color.WHITE);
		tvMenuText.setTextColor(getResources().getColor(textColor));

		a.recycle();
	}

	public void setTextMenu(String text) {
		tvMenuText.setText(text);
	}

	public void setIconMenu(Bitmap bm) {
		ivMenuIcon.setImageBitmap(bm);
	}

	public ImageView getIconMenu() {
		return ivMenuIcon;
	}

	public void setBadgeCount(int num) {
		bgView.setText(num + "");
		bgView.setVisibility(num > 0 ? View.VISIBLE : View.GONE);
	}

	public void setIconDrop(int icon) {
		if (tvMenuDrop.getVisibility() != View.VISIBLE)
			tvMenuDrop.setVisibility(View.VISIBLE);
		tvMenuDrop.setImageResource(icon);
	}

	/**
	 * @return the isSelected
	 */
	public boolean isCurrentMenu() {
		return isCurrentMenu;
	}

	/**
	 * @param isSelected
	 *            the isSelected to set
	 */
	public void setCurrentMenu(boolean isCurrentMenu, int icon) {
		this.isCurrentMenu = isCurrentMenu;
		if (isCurrentMenu) {
			tvMenuText.setTextColor(mContext.getResources().getColor(R.color.blue_menu));
			llWrapper.setBackgroundResource(mBgSelector);
		} else {
			tvMenuText.setTextColor(mContext.getResources().getColor(R.color.white));
			llWrapper.setBackgroundResource(mBg);
		}
		if (icon != 0)
			ivMenuIcon.setImageResource(icon);
	}
}