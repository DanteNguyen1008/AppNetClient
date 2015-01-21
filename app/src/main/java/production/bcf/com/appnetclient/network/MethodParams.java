/**
 * MethodParams.java
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
package production.bcf.com.appnetclient.network;

import com.bigcatfamily.networkhandler.db.BaseMethodParams;

import java.util.ArrayList;

public class MethodParams extends BaseMethodParams {
	private Methods mName;
	private int mSize;
	private int mOffset = 0;
	private boolean mOnly4Cache = false;
	private Object mInputData;
	private String mTag;
	public ArrayList<Object> backData;

	public Object getInputData() {
		return mInputData;
	}

	public void setInputData(Object inputData) {
		mInputData = inputData;
	}

	public boolean isOnly4Cache() {
		return mOnly4Cache;
	}

	public void setOnly4Cache(boolean only4Cache) {
		this.mOnly4Cache = only4Cache;
	}

	public MethodParams(Methods method) {
		this.mName = method;
	}

	public MethodParams(Methods method, Object inputData) {
		this.mName = method;
		this.mInputData = inputData;
	}

	public MethodParams(Methods method, ArrayList<Object> backData) {
		this.mName = method;
		this.backData = backData;
	}

	public MethodParams(Methods method, int offset, int size) {
		this.mName = method;
		this.mSize = size;
		this.mOffset = offset;
	}

	public MethodParams(Methods method, int offset, int size, Object inputData) {
		this.mName = method;
		this.mSize = size;
		this.mOffset = offset;
		this.mInputData = inputData;
	}

	public MethodParams(Methods method, int offset, int size, Object inputData, String tag) {
		this.mName = method;
		this.mSize = size;
		this.mOffset = offset;
		this.mInputData = inputData;
		this.mTag = tag;
	}

	public Methods getName() {
		return mName;
	}

	public int getSize() {
		return mSize;
	}

	public int getOffset() {
		return mOffset;
	}

	public String getTag() {
		return mTag;
	}
}
