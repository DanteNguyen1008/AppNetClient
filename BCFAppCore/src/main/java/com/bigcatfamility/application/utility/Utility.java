package com.bigcatfamility.application.utility;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.CursorLoader;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utility {
	/**
	 * Obtains character set of the entity, if known.
	 * 
	 * @param entity
	 *            must not be null
	 * @return the character set, or null if not found
	 * @throws ParseException
	 *             if header elements cannot be parsed
	 * @throws IllegalArgumentException
	 *             if entity is null
	 */
	public static String getContentCharSet(final HttpEntity entity) throws ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		String charset = null;

		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}

		return charset;
	}

	/**
	 * Get the entity content as a String, using the provided default character
	 * set if none is found in the entity. If defaultCharset is null, the
	 * default "ISO-8859-1" is used.
	 * 
	 * @param entity
	 *            must not be null
	 * @param defaultCharset
	 *            character set to be applied if none found in the entity
	 * @return the entity content as a String
	 * @throws ParseException
	 *             if header elements cannot be parsed
	 * @throws IllegalArgumentException
	 *             if entity is null or if content length > Integer.MAX_VALUE
	 * @throws IOException
	 *             if an error occurs reading the input stream
	 */
	public static String toString(final HttpEntity entity, final String defaultCharset) throws IOException,
	        ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();

		if (instream == null) {
			return "";
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}
		// Log.d( "HTTP", "entity.getContentLength( ) = " +
		// entity.getContentLength( ) );
		// Log.d( "HTTP", "instream.available( ) = " + instream.available( ) );
		// int i = ( int ) entity.getContentLength( );
		//
		// if ( i < 0 )
		// {
		// i = 4096;
		// }

		String charset = getContentCharSet(entity);

		if (charset == null) {
			charset = defaultCharset;
		}

		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}

		Reader reader = new InputStreamReader(instream, charset);

		StringBuilder buffer = new StringBuilder();

		try {
			char[] tmp = new char[1024];

			int l;

			while ((l = reader.read(tmp)) != -1) {
				// Log.d( "HTTP", "readerrrrrrrrrrrrrrrrrrrr l = " + l );
				buffer.append(tmp, 0, l);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		String str = buffer.toString();
		// Log.d( "HTTP", "readerrrrrrrrrrrrrrrrrrrr str = " + str );
		return str;
	}

	/**
	 * Read the contents of an entity and return it as a String. The content is
	 * converted using the character set from the entity (if any), failing that,
	 * "ISO-8859-1" is used.
	 * 
	 * @param entity
	 * @return String containing the content.
	 * @throws ParseException
	 *             if header elements cannot be parsed
	 * @throws IllegalArgumentException
	 *             if entity is null or if content length > Integer.MAX_VALUE
	 * @throws IOException
	 *             if an error occurs reading the input stream
	 */
	public static String toString(final HttpEntity entity) throws IOException, ParseException {
		return toString(entity, null);
	}

	/**
	 * Convert string to md5 string
	 * 
	 * @param s
	 * @return md5 string
	 */
	public static final String MD5(final String s) {
		if (!IsEmpty(s))
			try {
				// Create MD5 Hash
				MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
				digest.update(s.getBytes());
				byte messageDigest[] = digest.digest();

				// Create Hex String
				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < messageDigest.length; i++) {
					String h = Integer.toHexString(messageDigest[i] & 0xFF);
					while (h.length() < 2)
						h = "0" + h;
					hexString.append(h);
				}
				return hexString.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
		return "";
	}

	/**
	 * Check whether string is null/empty or not
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Check whether talklife email address is valid or not
	 * 
	 * @param email_address
	 * @return
	 */
	public static boolean IsTalkLifeEmailValid(String email_address) {
		if (IsEmpty(email_address))
			return false;

		if (!email_address.contains("@"))
			return false;
		String temp = email_address.replaceFirst("@", "");
		if (temp.contains("@"))
			return false;
		int str_len = email_address.length();
		int a_index = email_address.indexOf("@");
		if (a_index >= str_len - 3)
			return false;
		String domain = email_address.substring(a_index);
		if (!domain.contains("."))
			return false;
		int dot_index = domain.indexOf(".");
		if (dot_index >= domain.length() - 1)
			return false;
		if (domain.contains(".."))
			return false;

		return true;
	}

	public static String GetBirthday(String date) {
		if (IsEmpty(date))
			return "unidentified";

		int yr_year = 0, yr_month = 0, yr_date = 0;
		try {
			String[] str = date.split("-");
			yr_year = Integer.parseInt(str[0]);
			yr_month = Integer.parseInt(str[1]);
			yr_date = Integer.parseInt(str[2]);
		} catch (Exception e) {
			return "unidentified";
		}

		long time = System.currentTimeMillis();
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(time);

		Calendar birthDate = Calendar.getInstance();
		birthDate.set(yr_year, yr_month - 1, yr_date);

		if (birthDate.after(today)) {
			return "unidentified";
		}

		int year = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH);
		int day = today.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH);
		if (month > 0) {
			year += 1;
		} else if (month == 0) {
			if (day >= 0)
				year += 1;
		}

		return String.valueOf(year);
	}

	@SuppressLint("InlinedApi")
	public static int DetectDiviceScreenKind(Context context) {
		Configuration config = context.getResources().getConfiguration();
		if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			return Configuration.SCREENLAYOUT_SIZE_XLARGE;
		} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			return Configuration.SCREENLAYOUT_SIZE_NORMAL;
		} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			return Configuration.SCREENLAYOUT_SIZE_SMALL;
		} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			return Configuration.SCREENLAYOUT_SIZE_LARGE;
		} else {
			return Configuration.SCREENLAYOUT_SIZE_UNDEFINED;
		}
	}

	public static float GetScaleForCurrentDiviceSize(Context context, int nBaseSize) {
		if (DetectDiviceScreenKind(context) == nBaseSize) {
			return 0;
		} else {
			switch (DetectDiviceScreenKind(context)) {
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:

				break;

			default:
				break;
			}
		}

		return 0;
	}

	public static String GetStringWithLimit(String string, int limit) {
		String ellip = "...";
		if (string == null || string.length() <= limit || string.length() < ellip.length()) {
			return string;
		}
		return string.substring(0, limit - ellip.length()).concat(ellip);
	}

	public static int GetAge(String birthDate) throws java.text.ParseException {
		Date currentDate = new Date();
		Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);
		now.setTime(currentDate);
		// if (dob.after(now)) {
		// throw new IllegalArgumentException("Can't be born in the future");
		// }
		int year1 = now.get(Calendar.YEAR);
		int year2 = dob.get(Calendar.YEAR);
		int age = year1 - year2;
		int month1 = now.get(Calendar.MONTH);
		int month2 = dob.get(Calendar.MONTH);
		if (month2 > month1) {
			age--;
		} else if (month1 == month2) {
			int day1 = now.get(Calendar.DAY_OF_MONTH);
			int day2 = dob.get(Calendar.DAY_OF_MONTH);
			if (day2 > day1) {
				age--;
			}
		}

		return age;
	}

	public static Date GetTimeFromDateString(String date) {
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
		try {
			result = new Date(sdf.parse(date).getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String GetUTCStringTimeFromDate(Date date) {
		String result = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
		result = sdf.format(date);

		return result;
	}

	public static void ShowKeyBoard(Context context, EditText edittext) throws Exception {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edittext,
		        InputMethodManager.SHOW_FORCED);
	}

	public static void HideKeyBoard(Activity activity) {
		if (activity.getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
			        .getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}

	/* Capture and choose exising photo */
	public static final int CAMERA_PIC_REQUEST = 1336;
	public static final int GALLERY_PIC_REQUEST = 1337;
	public static final int PIC_CROP = 1338;

	public static void copyStream(InputStream input, OutputStream output) throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	public static int GetRandomNumberInRange(int from, int to) {
		return from + (int) (Math.random() * ((to - from) + 1));
	}

	public static String getUTCGMTTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		return f.format(new Date());
	}

	public static String getUTCGMTStringTimeFromDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		return f.format(date);
	}

	public static Date getUTCGMTDateFromString(String strDate) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			f.setTimeZone(TimeZone.getTimeZone("UTC"));

			return f.parse(strDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getStrTimeFromDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return f.format(new Date());
	}

	public static Date getDateFromString(String strDate) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return f.parse(strDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getStrTimeFromSimpleDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(new Date());
	}

	public static Date getDateFromSimpleString(String strDate) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			return f.parse(strDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int GetNumberInsideString(String str) throws Exception {
		String result = "";

		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(str);

		// Find all matches
		while (matcher.find()) {
			result += matcher.group();
		}

		return Integer.parseInt(result);
	}

	public static void writeToFile(String fileName, String data, Context context) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName,
			        Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	public static String readFromFile(String fileName, Context context) {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput(fileName);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static Drawable getDrawbleInAsset(Context context, String fileName) {
		try {
			// get input stream
			InputStream ims = context.getAssets().open(fileName);
			// load image as Drawable
			return Drawable.createFromStream(ims, null);
		} catch (IOException ex) {
			return null;
		}
	}

	public static Uri saveBitmapToGallery(ContentResolver cr, String title, String description, Bitmap source) {
		return Uri.fromFile(new File(Media.insertImage(cr, source, title, description)));
	}

	public static Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	/**
	 * For SAF (Storage Access Netwrok) in Kitkat 4.4
	 * */
	public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
		ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
		FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		parcelFileDescriptor.close();
		return image;
	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(Context context, Uri uri) {
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(uri);

		// Split at colon, use second item in the array
		String id = wholeID.split(":")[1];

		String[] column = { MediaStore.Images.Media.DATA };

		// where id is equal to
		String sel = MediaStore.Images.Media._ID + "=?";

		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
		        new String[] { id }, null);

		int columnIndex = cursor.getColumnIndex(column[0]);

		if (cursor.moveToFirst()) {
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String result = null;

		CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();

		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}
		return result;
	}

	public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static File getFile(Context context, String filePath) {
		File f = null;
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				f = new File(context.getExternalFilesDir(null), filePath);
				f.createNewFile();
			} else {
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				f = new File(path, filePath);
				path.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	public static File checkFileExisted(Context context, String filePath) {
		File f = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			f = new File(context.getExternalFilesDir(null), filePath);
		} else {
			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			f = new File(path, filePath);
		}

		if (f != null && f.exists())
			return f;
		return null;
	}

	public static File setBitmapToStorage(Context context, Bitmap bitmap, String filePath) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File f = getFile(context, filePath);
		try {
			// write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			fo.close();
			bytes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		//
		// f = new File(context.getExternalFilesDir(null), filePath);
		// try {
		// f.createNewFile();
		// // write the bytes in file
		// FileOutputStream fo = new FileOutputStream(f);
		// fo.write(bytes.toByteArray());
		//
		// // remember close de FileOutput
		// fo.close();
		//
		// bytes.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// } else {
		// InputStream is = null;
		// OutputStream os = null;
		// try {
		// File path =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		// f = new File(path, filePath);
		//
		// path.mkdirs();
		//
		// // is = new ByteArrayInputStream(bytes.toByteArray());
		// os = new FileOutputStream(f);
		// byte[] data = bytes.toByteArray();
		// // is.read(data);
		// os.write(data);
		//
		// // is.close();
		// os.close();
		// bytes.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		return f;
	}
	
	/**
	 * Check whether client is emulator flatform or not
	 */
	public static boolean IsEmulator() {
		return Build.MANUFACTURER.compareToIgnoreCase("unknown") == 0;
	}

	/**
	 * Check whether network is available or not
	 */
	public static boolean isNetworkAvailable(Context mContext) {
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
	public static String GetDeviceID(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = telephonyManager.getDeviceId();
		if (IsEmpty(device_id))
			device_id = "not available";
		return device_id;
	}

	/**
	 * Get devices' screen height
	 */
	public static int GetScreenHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

    public static void openUrl(Activity activity, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

}
