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
package com.bigcatfamily.networkhandler.db;

public abstract class BaseMethodParams {

	public abstract Object getInputData();

	public abstract void setInputData(Object inputData);

	public abstract boolean isOnly4Cache();

	public abstract void setOnly4Cache(boolean only4Cache);

	public abstract int getSize();

	public abstract int getOffset();

	public abstract String getTag();
}
