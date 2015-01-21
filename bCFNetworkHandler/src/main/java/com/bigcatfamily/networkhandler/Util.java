/*
 * Util.java
 *
 * Utilities functions
 *
 * @author quaych@nexlesoft.com
 * @date 2011/08/06
 * @lastChangedRevision:
 * @lastChangedDate: 2011/08/06
 */
package com.bigcatfamily.networkhandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;

public class Util {

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
	 * @param str
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

}
