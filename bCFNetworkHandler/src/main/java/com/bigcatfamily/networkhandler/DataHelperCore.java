/**
 * ApiHepler.java
 * 
 * Purpose              : Manage all api call.
 * 
 * Optional info        :
 *
 * @author              :
 * 
 * @date                :
 * 
 * @lastChangedRevision :
 *
 * @lastChangedDate     : Aug 06, 2013
 *
 */
package com.bigcatfamily.networkhandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONException;
import org.xml.sax.SAXException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.bigcatfamily.networkhandler.constant.Defines;
import com.bigcatfamily.networkhandler.db.DataResult;
import com.bigcatfamily.networkhandler.db.ErrorCode;
import com.bigcatfamily.networkhandler.db.FileCache;
import com.bigcatfamily.networkhandler.db.BaseMethodParams;

/**
 * DataHelper.java
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
 * <b>date</b> : Jul 23, 2013
 * 
 * <br>
 * <b>lastChangedRevision</b> :
 * 
 * <br>
 * <b>lastChangedDate</b> :
 * 
 */
@TargetApi(11)
public class DataHelperCore extends Observable {

	private static final String TAG = "DataHelper";
	protected static final String GET = "GET";
	protected static final String POST = "POST";
	final protected static String CHARSET = "UTF-8";
	final private static String USER_AGENT = "Talklife/4.0";
	final private static String ACCEPT_CHARSET = CHARSET;
	final private static String CONTENT_TYPE = "application/json";
	final private static String CONTENT_TYPE_MULTI_PART = "multipart/form-data";
	final private static String BOUNDARY = "Boundary+0xAbCdEfGbOuNdArY";
	final private static String ACCEPT_ENCODING = Defines.USE_GZIP ? "gzip, deflate" : "";
	final private static String KEEP_ALIVE = "Keep-Alive";

	private final int MAX_SIZE_POOL = 6;
	/** The time it takes for our client to timeout */
	private final int CONNECTION_TIMEOUT = 30 * 1000;
	private final int SOCKET_TIMEOUT = 30 * 1000;
	public static final int INITIAL_SIZE = 15;

	private Map<String, HttpTask> mPoolExcutingTask;
	private Queue<HttpTask> mWaitingTask;
	@SuppressWarnings("unused")
	private FileCache fileCache;
	protected ParserDataHelperCore parserData;
	protected Context mContext;

	// private ProgressDialog mDialog;

	/**
	 * Get our single instance of our DataHelper object.
	 * 
	 * @return a DataHelper object
	 */

	protected DataHelperCore(Context context, ParserDataHelperCore parseHelper) {
		super();
		this.mContext = context;
		this.parserData = parseHelper;
		mPoolExcutingTask = new LinkedHashMap<String, DataHelperCore.HttpTask>(MAX_SIZE_POOL) {
			private static final long serialVersionUID = 7777162779227793511L;

			@Override
			protected boolean removeEldestEntry(java.util.Map.Entry<String, HttpTask> eldest) {
				if (size() > MAX_SIZE_POOL) {
					Log.e(TAG, "remove eldest task " + eldest.getValue().getUrl() + " current size is : " + size());
					eldest.getValue().disconect();
					return true;
				} else {
					return false;
				}
			}
		};

		mWaitingTask = new ConcurrentLinkedQueue<DataHelperCore.HttpTask>();
		fileCache = new FileCache(mContext);

	}

	protected ParserDataHelperCore getParser() {
		return parserData;
	}

	/**
	 * Performs an HTTP request to the specified url with the specified
	 * parameters.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @param params
	 *            The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */
	private class HttpTask extends AsyncTask<Void, Void, DataResult> {
		HttpURLConnection mHttpUrlConnection;
		String mHttpUrl;
		String mTaskName = "";
		String mProtocolMethod;
		byte[] xmlContent;
		MultipartEntity reqEntity;
		BaseMethodParams methodparam;
		boolean m_bIsDiplayError;

		public HttpTask(String url, String protocolMethod, byte[] xmlContent, MultipartEntity reqEntity,
		        BaseMethodParams methodparam, String taskName) {
			this(url, protocolMethod, xmlContent, reqEntity, methodparam, taskName, false);
		}

		public HttpTask(String url, String protocolMethod, byte[] xmlContent, MultipartEntity reqEntity,
		        BaseMethodParams methodparam, String taskName, boolean isDisplayError) {
			this.mTaskName = taskName;
			mHttpUrl = url;
			this.mProtocolMethod = protocolMethod;
			this.xmlContent = xmlContent;
			this.reqEntity = reqEntity;
			this.methodparam = methodparam;
			this.m_bIsDiplayError = isDisplayError;
		}

		public String getUrl() {
			return mHttpUrl;
		}

		public void disconect() {
			if (mHttpUrlConnection != null)
				mHttpUrlConnection.disconnect();
			cancel(true);
			mWaitingTask.add(new HttpTask(mHttpUrl, mProtocolMethod, xmlContent, reqEntity, methodparam, mTaskName));
			Log.e(TAG, "waiting queue size: " + mWaitingTask.size());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressLint("DefaultLocale")
		@Override
		protected DataResult doInBackground(Void... params) {
			try {
				URL u = new URL(mHttpUrl);
				Log.d(TAG, "==> request API: " + mHttpUrl);
				if (u.getProtocol().toLowerCase().equals("https")) {
					HttpsURLConnection https = (HttpsURLConnection) u.openConnection();
					mHttpUrlConnection = https;
				} else {
					mHttpUrlConnection = (HttpURLConnection) u.openConnection();
				}

				mHttpUrlConnection.setRequestMethod("GET");
				mHttpUrlConnection.setRequestProperty("User-Agent", USER_AGENT + "(Android-" + android.os.Build.MODEL
				        + "-" + android.os.Build.MANUFACTURER + "-" + android.os.Build.PRODUCT + ")");
				mHttpUrlConnection.setRequestProperty("Accept-Charset", ACCEPT_CHARSET);
				mHttpUrlConnection.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
				mHttpUrlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
				mHttpUrlConnection.setRequestProperty("Connection", KEEP_ALIVE);
				mHttpUrlConnection.setReadTimeout(SOCKET_TIMEOUT);
				if (reqEntity != null) {
					mHttpUrlConnection.addRequestProperty("Content-Length", reqEntity.getContentLength() + "");
					mHttpUrlConnection.setRequestProperty("Content-Type", CONTENT_TYPE_MULTI_PART + "; boundary="
					        + BOUNDARY);
				} else {
					mHttpUrlConnection.setRequestProperty("Content-Type", CONTENT_TYPE);
				}

				mHttpUrlConnection.setUseCaches(false);
				mHttpUrlConnection.setDoInput(true);
				mHttpUrlConnection.setDoOutput(false);

				return request(mHttpUrl, mHttpUrlConnection, xmlContent, reqEntity, methodparam);

			} catch (FileNotFoundException e) {
				Log.d(TAG, "==> FileNotFoundException: " + e.getMessage());
				if (m_bIsDiplayError) {
					DataResult result = new DataResult();
					result.setErrorCode(ErrorCode.SERVER_ERROR);
					result.setMethodName(methodparam);
					result.setErrorMessage(e.getMessage());
					return result;
				} else
					return null;

			} catch (SocketTimeoutException e) {
				Log.d(TAG, "==> SocketTimeoutException: " + e.getMessage());
				if (m_bIsDiplayError) {
					DataResult result = new DataResult();
					result.setErrorCode(ErrorCode.TIME_OUT);
					result.setMethodName(methodparam);
					result.setErrorMessage(e.getMessage());

					return result;
				} else
					return null;

			} catch (IOException e) {
				Log.d(TAG, "==> IOException: response => " + e.getMessage());
				if (m_bIsDiplayError) {
					DataResult result = new DataResult();
					result.setErrorCode(ErrorCode.UNKNOWN_ERROR);
					result.setMethodName(methodparam);
					result.setErrorMessage(e.getMessage());

					return result;
				} else
					return null;

			} catch (Exception e) {
				Log.d(TAG, "==> Exception111111111111111111111111");
				e.printStackTrace();
				if (m_bIsDiplayError) {
					DataResult result = new DataResult();
					result.setErrorCode(ErrorCode.UNKNOWN_ERROR);
					result.setMethodName(methodparam);
					result.setErrorMessage(e.getMessage());

					return result;
				} else
					return null;

			} finally {
				if (mHttpUrlConnection != null) {
					mHttpUrlConnection.disconnect();
				}
			}
		}

		@TargetApi(11)
		@Override
		protected void onPostExecute(DataResult result) {

			String sCondition = (mTaskName.equals("")) ? mHttpUrl : mTaskName;
			mPoolExcutingTask.remove(sCondition);
			if (mPoolExcutingTask.size() < MAX_SIZE_POOL) {
				HttpTask task = mWaitingTask.poll();
				if (task != null) {
					mPoolExcutingTask.put(sCondition, task);
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
						task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
					} else {
						task.execute((Void) null);
					}
				}
			}

			Log.d(TAG, "Current Size pool " + mPoolExcutingTask.size() + "Waiting task " + mWaitingTask.size());

			if (result != null) {
				ErrorCode errorCode = result.getErrorCode();
				if (errorCode != null) {
					Log.w(TAG, "ErrorCode: " + errorCode.toString());
				}

				setChanged();
				notifyObservers(result);
			} else {
				Log.w(TAG, "http task result = null");
			}
		}
	}

	@SuppressWarnings("unused")
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private DataResult request(String url, HttpURLConnection conn, byte[] data, MultipartEntity reqEntity,
	        BaseMethodParams method) throws SocketTimeoutException, FileNotFoundException, IOException {

		InputStream in = null;
		DataResult result = null;
		if (data != null) {
			OutputStream out = conn.getOutputStream();
			if (reqEntity != null) {
				reqEntity.writeTo(out);
			} else {
				out.write(data);
			}
			out.flush();
			out.close();
		}
		conn.connect();

		int httpCode = conn.getResponseCode();
		if (httpCode == HttpURLConnection.HTTP_OK || httpCode == HttpURLConnection.HTTP_CREATED) {
			in = new BufferedInputStream(conn.getInputStream());
			Log.d(TAG, "request URL " + url);
			if (in != null) {
				try {
					result = getParser().parse(in, method);
				} catch (ParserConfigurationException e) {
					Log.d("request", "ParserConfigurationException............ ");
					e.printStackTrace();
					result = new DataResult();
					result.setErrorCode(ErrorCode.ERROR_CODE_PARSING);
				} catch (SAXException e) {
					Log.d("request", "SAXException............ ");
					e.printStackTrace();
					result = new DataResult();
					result.setErrorCode(ErrorCode.ERROR_CODE_PARSING);
				} catch (JSONException e) {
					Log.d("request", "SAXException............ ");
					e.printStackTrace();
					result = new DataResult();
					result.setErrorCode(ErrorCode.ERROR_CODE_PARSING);
				}
			}

			result.setMethodName(method);
			return result;
		} else if (httpCode == HttpURLConnection.HTTP_BAD_REQUEST) {
			Log.d("request", "HttpURLConnection.HTTP_BAD_REQUEST");
			result = new DataResult();
			result.setMethodName(method);
			result.setErrorCode(ErrorCode.APP_EMAIL_EXISTS);
		} else if (httpCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
			Log.d("request", "HttpURLConnection.HTTP_UNAUTHORIZED");
			result = new DataResult();
			result.setMethodName(method);
			result.setErrorCode(ErrorCode.UNAUTHORIZED);
		} else if (httpCode == HttpURLConnection.HTTP_FORBIDDEN) {
			Log.d("request", "HttpURLConnection.HTTP_FORBIDDEN");
			result = new DataResult();
			result.setMethodName(method);
		} else if (httpCode == HttpURLConnection.HTTP_NOT_FOUND) {
			Log.d("request", "HttpURLConnection.HTTP_NOT_FOUND");
			result = new DataResult();
			result.setMethodName(method);
		} else {
			Log.d("request", "HttpURLConnection.SERVER_ERROR");
			result = new DataResult();
			result.setMethodName(method);
			result.setErrorCode(ErrorCode.SERVER_ERROR);
		}
		return result;
	}

	public String ConvertStreamToString(InputStream is) {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, CHARSET));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} catch (IOException e) {
				return "";
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	protected void excute(String url, String protocolMethod, String content, MultipartEntity reqEntity,
	        BaseMethodParams methodparam, String taskName, boolean isDisplayError) {

		Log.d(TAG, "excute url " + url + " method " + protocolMethod);
		String sCondition = (taskName.equals("")) ? url : taskName;

		// if (mPoolExcutingTask.get(sCondition) == null) {
		byte[] bytes = Util.IsEmpty(content) ? null : content.getBytes();
		HttpTask task = new HttpTask(url, protocolMethod, bytes, reqEntity, methodparam, taskName, isDisplayError);
		mPoolExcutingTask.put(sCondition, task);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
		} else {
			task.execute((Void) null);
		}
		// }
		Log.e(TAG, "place task to pool : " + url + " current size : " + mPoolExcutingTask.size());
	}

	protected String getParamsString(Map<String, String> params) {
		if (params == null)
			return null;
		String ret = "";
		for (String key : params.keySet()) {
			try {
				String value = params.get(key);
				ret += key + "=" + URLEncoder.encode(value, "UTF-8") + "&";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Log.w(TAG, "Exception getParamsString_" + ret + "_" + key);
				e.printStackTrace();
			}
		}
		if (ret.length() == 0)
			return null;
		return ret.substring(0, ret.length() - 1);
	}
}
