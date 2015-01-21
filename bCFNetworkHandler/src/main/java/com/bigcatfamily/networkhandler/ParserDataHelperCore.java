/**
 * ParserDataHelper.java
 * 
 * Purpose              : parse xml data received from webservice
 * 
 * Optional info        :
 *
 * @author              : quaych@nexlesoft.com
 * 
 * @date                : Aug 06, 2013
 * 
 * @lastChangedRevision :
 *
 * @lastChangedDate     : Aug 06, 2013
 *
 */
package com.bigcatfamily.networkhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.util.Log;

import com.bigcatfamily.networkhandler.constant.Defines;
import com.bigcatfamily.networkhandler.db.BaseMethodParams;
import com.bigcatfamily.networkhandler.db.DataResult;

/**
 * ParserDataHelper.java
 * 
 * <br>
 * <b>Purpose</b> :
 * 
 * <br>
 * <b>Optional info</b> :
 * 
 * <br>
 * <b>author</b> :
 * 
 * <br>
 * <b>date</b> : Aug 06, 2013
 * 
 * <br>
 * <b>lastChangedRevision</b> :
 * 
 * <br>
 * <b>lastChangedDate</b> :
 * 
 */
@SuppressLint("NewApi")
public abstract class ParserDataHelperCore implements IParseDataHelper {
	private static final String TAG = ParserDataHelperCore.class.getSimpleName();
	protected static final String ERROR_CODE = "error_code";
	protected static final String BODY = "result";
	protected static final String STATUS = "status";
	final private static String CHARSET = "UTF-8";

	/**
	 * Parse xml string
	 * 
	 * @param data
	 * @param method
	 * @return All parser method return object ApiServiceResult
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws JSONException
	 */
	public DataResult parse(InputStream inputstream, BaseMethodParams methodparam) throws ParserConfigurationException,
	        SAXException, IOException, JSONException {
		if (inputstream == null) {
			return null;
		}

		JSONObject jObj = null;

		if (Defines.USE_GZIP)
			inputstream = new GZIPInputStream(inputstream);

		BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputstream, CHARSET), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader1.readLine()) != null) {
			sb.append(line + "\n");
		}
		try {
			jObj = new JSONObject(sb.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (Defines.IS_DISPLAY_DEBUG_DATA)
			Log.d(TAG, jObj.toString());

		DataResult result = parseData(jObj, methodparam);

		if (result != null) {
			result.setMethodName(methodparam);
		}

		return result;
	}

	protected String getJSONString(JSONObject json, String name) {
		try {
			if (!json.isNull(name)) {
				return json.getString(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	protected int getJSONInt(JSONObject json, String name) {
		try {
			if (!json.isNull(name)) {
				return json.getInt(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}

	protected boolean getJSONBoolean(JSONObject json, String name) {
		try {
			if (!json.isNull(name)) {
				return json.getBoolean(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;
	}

	protected long getJSONLong(JSONObject json, String name) {
		try {
			if (!json.isNull(name)) {
				return json.getLong(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}

	protected double getJSONDouble(JSONObject json, String name) {
		try {
			if (!json.isNull(name)) {
				return json.getDouble(name);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
