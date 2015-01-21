package com.bigcatfamily.networkhandler.db;

import com.bigcatfamily.networkhandler.constant.Defines;

/**
 * Urls.java
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
public interface Urls {
	final public static String MAIN_REAL_URL = "http://talklife.com.au/Talklife43/TalklifeHandler.ashx"; // "http://talklife.com.au/Talklife4/TalklifeHandler.ashx";

	final public static String MAIN_TEST_SERVICE_URL = "http://192.168.45.204/Talklife4/talklifehandler.ashx";

	final public static String MAIN_URL = Defines.USE_TEST_SERVICE ? MAIN_TEST_SERVICE_URL : MAIN_REAL_URL;

	final public static String BLOG_URL = "http://talklife.com.au/Blog/?feed=rss";

	final public static String BLOG_URL_JSON = "http://talklife.com.au/Blog/?feed=json";

	final public static String TEST_XMPP_URL = "192.168.45.204";

	final public static int XMPP_PORT = 5222;

	final public static String TEST_XMPP_SERVICE = "win01";

	final public static String MAIN_XMPP_URL = "xmpp.talklife.com.au";

	final public static String MAIN_XMPP_SERVICE = "xmpp.talklife.com.au";

	final public static String XMPP_URL = Defines.USE_TEST_SERVICE ? TEST_XMPP_URL : MAIN_XMPP_URL;

	final public static String XMPP_SERVICE = Defines.USE_TEST_SERVICE ? TEST_XMPP_SERVICE : MAIN_XMPP_SERVICE;

	final public static String XMPP_SERVICE_FOR_ACC = "@"
	        + (Defines.USE_TEST_SERVICE ? TEST_XMPP_SERVICE : MAIN_XMPP_SERVICE);
}
