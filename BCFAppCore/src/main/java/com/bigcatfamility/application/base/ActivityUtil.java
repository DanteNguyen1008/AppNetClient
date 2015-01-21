/*
 * ActivityUtil
 *
 * This class is used for store all open activities. So that, you can finish all open activities incase exit application by call CloseAllOpenActivities method
 *
 * @author quaych@nexlesoft.com
 * @date 2011/08/06
 * @lastChangedRevision:
 * @lastChangedDate: 2011/08/06
 */
package com.bigcatfamility.application.base;

import java.util.ArrayList;
import java.util.UUID;

import com.bigcatfamility.application.utility.Utility;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

public class ActivityUtil {
	private static final String TAG = ActivityUtil.class.getSimpleName();

	/**
	 * Check whether client is emulator flatform or not
	 */
	public static boolean IsEmulator() {
		return Build.MANUFACTURER.compareToIgnoreCase("unknown") == 0;
	}

	/**
	 * Check whether network is available or not
	 */
	public static boolean IsNetworkAvailable(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;

		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isConnectedOrConnecting();
	}

	/**
	 * Check whether device has sdcard(sdcard is mount) or not
	 */
	public static boolean HasSDCard() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * Get devices' screen width
	 */
	public static int GetScreenWidth(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**
	 * Get device id
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String GetDeviceID(Context context) {
		String result = null, macAddr, serialId;
		try {
			WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInf = wifiMan.getConnectionInfo();
			macAddr = wifiInf.getMacAddress();
			serialId = Build.SERIAL;
			if (!Utility.IsEmpty(macAddr))
				result = new UUID(serialId.hashCode(), macAddr.hashCode()).toString();
			else
				result = serialId;

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Utility.IsEmpty(result))
			result = "Device id is unavailale";
		return result;
	}

	/**
	 * Get devices' screen height
	 */
	public static int GetScreenHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	public static int ConvertDpToPixel(Context context, float dp) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	/**
	 * @param drawable
	 * @param v
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void SetDrawableBackGroundForView(Drawable drawable, View v) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			v.setBackground(drawable);
		} else {
			v.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * @param context
	 * @param url
	 */
	public static void OpenBrowser(Context context, String url) {
		if (!url.startsWith("http://") && !url.startsWith("https://"))
			url = "http://" + url;
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(browserIntent);
	}
}
