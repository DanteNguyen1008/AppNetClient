/*
 * Defines
 *
 * Define for all FLAGs
 *
 * @author quaych@nexlesoft.com
 * @date 2011/08/06
 * @lastChangedRevision:
 * @lastChangedDate: 2011/08/06
 */

package com.bigcatfamily.networkhandler.constant;

public interface Defines {
	public static final boolean IS_DISPLAY_DEBUG_DATA = true;//should be false;
	
	
	/** Enables all android.util.Logs from CCLog.java **/
	public static final boolean USE_RELEASE = true;// should be: true
	public static final boolean USE_JSON = true; // should be true
	public static final boolean USE_CHAT_1_1 = true; // should be true
	public static final boolean USE_GZIP = !true;
	/** Enables all android.util.Logs from CCLog.java **/
	public static final boolean USE_DEBUG = true;// !false && !USE_RELEASE;//
	public static final boolean USE_WAITING_TO_CHAT_XMPP_CONNECTED = true;
	// should be: false

	public static final boolean USE_CHAT_DEBUG = !false; // should be false

	/** Enable flurry log */
	public static final boolean USE_FLURRY_LOG = !true;// should be true

	/** Enables all android.util.Logs from CCLog.java **/
	public static final boolean ENABLE_LOG = !false && USE_DEBUG;// should be:
	                                                             // false
	/**
	 * Enables all Logs when insert item to database. Only enable when
	 * ENABLE_LOG=true
	 **/
	public static final boolean ENABLE_LOG_DB = ENABLE_LOG && false;// should
	                                                                // be: false

	public static final boolean ENABLE_MEMORY_LOG = ENABLE_LOG && false;// should
	                                                                    // be:
	                                                                    // false
	public static final boolean SHOW_ERROR_LOG = ENABLE_LOG && !false;// should
	                                                                  // be:
	                                                                  // false
	/** Enables print all stack traces in try catch exception **/
	public static final boolean ENABLE_PRINT_STACK_TRACES = USE_DEBUG && !false;// should
	                                                                            // be:
	/** Enables Google Analytics Tracker **/
	public static final boolean ENABLE_GOOGLE_ANALYTICS_TRACKER = true; // default:true

	/** Enables Google Push notification **/
	public static final boolean ENABLE_PUSH_NOTIFICATION = !true; // default:true

	public static final boolean USE_DATA_TEMP = USE_DEBUG && !true;// should be:
	                                                               // false
	/** Enables cheat code **/
	public static final boolean USE_CHEAT = false;// should be: false

	public static final boolean USE_TEST_SERVICE = true && !USE_RELEASE;// should
	                                                                    // be:
	                                                                    // false

	/**
	 * Enable use System.gc() method in order to garbage memory
	 */
	public static final boolean USE_SYSTEM_GC = false;

	/**
	 * Enable auto login
	 */
	public static final boolean ENABLE_AUTO_LOGIN = !true;
}
