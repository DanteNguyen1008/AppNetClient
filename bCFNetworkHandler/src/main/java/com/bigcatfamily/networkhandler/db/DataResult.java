/**
 * DataResult.java
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
package com.bigcatfamily.networkhandler.db;

import java.util.ArrayList;

public class DataResult {

	private Object mData;
	private ErrorCode mErrorCode;
	private String mErrorName;
	private String ErrorMessage;
	private int CodeResult;
	private BaseMethodParams mMethodParam;
	private boolean mIsLastData = true;
	public ArrayList<Object> backData;

	public DataResult() {
		super();
	}

	public DataResult(Object data) {
		mData = data;
	}

	public Object getData() {
		return mData;
	}

	public void setData(Object data) {
		this.mData = data;
	}

	public BaseMethodParams getMethodName() {
		return mMethodParam;
	}

	public void setMethodName(BaseMethodParams method) {
		this.mMethodParam = method;
	}

	public ErrorCode getErrorCode() {
		return mErrorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		mErrorCode = errorCode;
	}

	public String getErrorName() {
		return mErrorName;
	}

	public void setErrorName(String errorName) {
		mErrorName = errorName;
	}

	public void setIsLastData(boolean value) {
		mIsLastData = value;
	}

	public boolean isLastData() {
		return mIsLastData;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String mErrorMessage) {
		this.ErrorMessage = mErrorMessage;
	}

	public int getCodeResult() {
		return CodeResult;
	}

	public void setCodeResult(int codeResult) {
		CodeResult = codeResult;
	}
}
