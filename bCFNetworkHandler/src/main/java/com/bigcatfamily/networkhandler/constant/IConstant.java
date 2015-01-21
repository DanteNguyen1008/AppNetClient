/*
 * IConstant
 *
 * Class for all constant value
 *
 * @author quaych@nexlesoft.com
 * @date 2011/08/06
 * @lastChangedRevision:
 * @lastChangedDate: 2011/08/06
 */
package com.bigcatfamily.networkhandler.constant;

public interface IConstant {

	public static final String TL_APP_VERSION = "4.2";

	public static final String BROADCAST_MSG_RESPONSE = "com.bearpty.talklife.action.MESSAGE_PROCESSED";
	public static final String INTENT_RESULT_UPDATE = "talklife.intent.update";
	public static final String INTENT_SPLASH_IMAGE_URL_ARRAYLIST = "talklife.intent.splash_img_url";
	public static final String INTENT_SIGNUP_FIRST_NAME = "talklife.intent.signup_first_name";
	public static final String INTENT_SIGNUP_LAST_NAME = "talklife.intent.signup_last_name";
	public static final String INTENT_SIGNUP_AVATAR_PATH = "talklife.intent.signup_avatar_path";
	public static final String INTENT_TALKLIFE_USER_PARCELABLE = "talklife.intent.talklife_user_parcelable";
	public static final String INTENT_TALKLIFE_CREATEDBY_PARCELABLE = "talklife.intent.talklife_createdby_parcelable";
	public static final String INTENT_TALKLIFE_QUESTION_ID = "talklife.intent.talklife_question_id";
	public static final String INTENT_TALKLIFE_POST_DETAIL_ID = "talklife.intent.post_detail_id";
	public static final String INTENT_TALKLIFE_POST_DETAIL_CREATEDBY_ID = "talklife.intent.post_detail_createdby_id";
	public static final String INTENT_TALKLIFE_USER_ID = "talklife.intent.user_id";
	public static final String INTENT_TALKLIFE_FLAG_OBJECT_ID = "talklife.intent.flag_object_id";
	public static final String INTENT_TALKLIFE_FLAG_REQUEST_METHOD_PARCELABLE = "talklife.intent.talklife_methods_parcelable";
	public static final String INTENT_TALKLIFE_ADS_FULL_SCREEN_DISPLAY = "talklife.intent.ads.fullscreen.display";
	public static final String INTENT_TALKLIFE_BLOG_LINK = "talklife.intent.blog.link";
	public static final String INTENT_TALKLIFE_TERM_URL = "talklife.intent.term.url";
	public static final String INTENT_TALKLIFE_CHAT_ROOM = "talklife.intent.chat.room";
	public static final String INTENT_TALKLIFE_CHAT_ROOM_IS_LOAD_USER = "talklife.intent.chat.room.is.load.user";
	public static final String INTENT_TALKLIFE_SETTING_SCREEN = "talklife.intent.setting.screen";
	public static final String INTENT_TALKLIFE_QUESTION_DTO = "talklife.intent.question.dto";
	public static final String INTENT_TALKLIFE_IS_ANONYMOUS = "talklife.intent.is.anonymous";
	public static final String INTENT_TALKLIFE_CHAT_SPACE_BUNDLE = "talklife.intent.chat.space.bundle";
	public static final String INTENT_TALKLIFE_CHAT_SPACE_SCREEN = "talklife.intent.chat.space.screen";

	public static final int TL_APP_STATUS_UNKNOW = 0;
	public static final int TL_APP_STATUS_CLOSE = 1;
	public static final int TL_APP_STATUS_BACKGROUND = 2;
	public static final int TL_APP_STATUS_OPEN = 3;

	public static final String INTRO_SCREEN_CIRCLE_INDICATOR_SELECTED_COLOR = "#FF614C";
	public static final String INTRO_SCREEN_CIRCLE_INDICATOR_NORMAL_COLOR = "#DCDCD3";

	/* Feed ListView item, status layout, status nothing color */
	public static final String FEED_SCREEN_LISTVIEW_ITEM_STATUS_NOTHING_COLOR = "#E5EAE3";
	public static final String FEED_SCREEN_LISTVIEW_ITEM_STATUS_GENDER_MALE_COLOR = "#72C8F9";
	public static final String FEED_SCREEN_LISTVIEW_ITEM_STATUS_GENDER_FEMALE_COLOR = "#FF9CD8";
	public static final boolean FEED_SCREEN_USE_SORT_QuestionDTO_LIST = false;

	public static final int LOGIN_SCREEN_BACKGROUND_COLOR = 0xFFFBFBF3;

	/* Request code */
	public static final int GIFT_LIST_REQUEST_CODE = 1;
	public static final int GIFT_GIVEN_REQUEST_CODE = GIFT_LIST_REQUEST_CODE + 1;
	public static final int FLAG_REQUEST_CODE = GIFT_GIVEN_REQUEST_CODE + 1;
	public static final int BOOT_REQUEST_CODE = FLAG_REQUEST_CODE + 1;

	/* Setting constant */
	public static final String PREF_SETTING = "pref_setting";
	public static final String PREF_SETTING_PUSH_NOTIFICATION = "pref_setting_push_notification";
	public static final String PREF_SETTING_PASSWORD_PROTECT = "pref_setting_password_protect";

	/*
	 * Flurry API Key
	 */
	public static final String TL_FLU_API_KEY = "83PDGW5DV887KZN2M4H7";// "3676P7Y7PC978SZ6GVVG";
	                                                                   // test
	                                                                   // key;
	public static final String TL_FLU_BANNER = "BANNER_MAIN_VIEW";
	public static final String TL_FLU_FULL_SCREEN = "INTERSTITIAL_MAIN_VIEW";
	public static final String TL_FLU_BANNER_ADS = "FeedAds";
	public static final String TL_FLU_FULL_SCREEN_ADS = "FullScreenFeedAds";
	/*
	 * News screen
	 */

	public static final boolean TL_NEWS_ONLY_UNREAD = false;
	public static final int NEWS_REQUEST_CODE = FLAG_REQUEST_CODE + 1;
	public static final int WRITE_NEW_POST_CODE = NEWS_REQUEST_CODE + 1;
	public static final String NEWS_UNREAD_NOTIFICATION_DATA = "talklife.intent.news.unread.notification";

	/*
	 * Chat
	 */

	public static final String INTENT_TL_CHAT_SCREEN = "talklife.intent.chat.screen";
	public static final String INTENT_TL_CHAT_ROOM_ID = "talklife.intent.chat.room.id";
	public static final String INTENT_TL_CHAT_ROOM_FIRST_FRIEND = "talklife.intent.chat.room.first.friend";
	public static final String INTENT_TL_CHAT_ROOM_NAME_FIRST_TIME = "talklife.intent.chat.room.name.first.time";
	public static final String INTENT_TL_CHAT_ROOM_ROOM_NAME = "talklife.intent.chat.room.room.name";
	public static final String INTENT_TL_CHAT_ROOM_IS_LOAD_USER = "talklife.intent.chat.room.is.load.user";
	public static final String INTENT_TL_CHAT_ROOM_FIRST_FRIEND_OBJ = "talklife.intent.chat.room.first.friend.object";
	public static final String INTENT_TL_CHAT_SINGLE_ROOM_RECEIVER_XMPP_ACC = "talklife.intent.chat.single.room.receiver.xmpp.account";
	public static final String INTENT_TL_CHAT_ROOM_IS_MUC = "talklife.intent.chat.room.is.muc";
	public static final String INTENT_TL_CHAT_BLOCK_USER_IS_BLOCK_SCREEN = "talklife.intent.chat.block.user.is.block.screen";
	public static final String INTENT_TL_CHAT_ROOM_IS_LOADED_ROOM = "talklife.intent.chat.room.is.loaded.room";

	public static final int INTENT_TL_CHAT_VIBRATE_TIME = 500;
	public static final int CHAT_TL_MAXIMUM_HISTORY = 0;
	public static final int CHAT_TL_SPACE_MAXIMUM_HISTORY = 0;
	public static final int CHAT_TL_MAXIMUM_HISTORY_TO_LOAD = 10;

	public static final String PREF_CHAT_PRIVACY = "pref_chat_privacy";
	public static final String PREF_CHAT_PRIVACY_SOUND = "pref_chat_privacy_sound";
	public static final String PREF_CHAT_PRIVACY_VIBRATION = "pref_chat_privacy_vibration";
	public static final String CHAT_INVITE_MESSAGE = "Join chat with us";

	/* Push notification */
	public static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";
	public static final String BOOT_MESSAGE_ACTION = "talklife.broadcastreceiver.boot";
	public static final String PUSH_NOTIFICATION = "talklife.push.notification";
	public static final String PUSH_NOTIFICATION_MESSAGE_EXTRA = "talklife.push.notification.message.extra";
	public static final String PUSH_NOTIFICATION_ID_EXTRA = "talklife.push.notification.id.extra";
	public static final String BOOT_MESSAGE_KEY_VOTE = "talklife.broadcastreceiver.key.vote";
	public static final String BOOT_MESSAGE_CONTENT = "talklife.broadcastreceiver.content";
	public static final String EXTRA_MESSAGE = "talklife.broadcastreceiver.extra.message";
	public static final String EXTRA_MESSAGE_ID = "talklife.broadcastreceiver.extra.message.regid";

	/* Profile animation time */
	public static final int PROFILE_ANIM_TIME = 500;

	/* Attention animation time */
	public static final int ATTENTION_ANIM_TIME = 500;

	public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	/**
	 * CircleClippingSlidingImageView: sliding time delay
	 */
	public static final int CircleClippingSlidingImageView_SLIDING_DISTANCE = 1;

	/**
	 * ImageButton color filter on touch
	 */
	final static int DEFAULT_IMAGEBUTTON_FILTER_COLOR_ON_TOUCH = 0xff605d5d;

	public static final String PRO_SERVICE_PAGE_LINK = "http://talklife.com.au/Blog/?p=260";

	/* Introduction */
	public static final String PREF_INTRO = "talklife.pref.intro";
	public static final String PREF_INTRO_FLAG = "talklife.pref.intro.flag";

	public static final String TEMP_PHOTO_FILE_NAME = "talklife_temp_photo.png";

	public static final int SEARCH_BAR_ANIMATION_DELAY = 1000;

	public static final int DIMEN_NEXUS_2X_HEIGHT = 1184;
	public static final int DIMEN_NEXUS_4_WIDTH = 768;

	public static final String AD_BANNER_TYPE_BANNER = "0";

	public static final String AD_BANNER_TYPE_FULL_AD = "1";

	public static final long TIMER_PROCESS_DELAY = 10000;

	public static final int DIALOG_BIRTHDAY_DATE_TIME = 2183712;

	public static final int AVATAR_FADE_IN_TIME = 500;

	public static final int CHAT_ROOM_GET_USER_COUNT = 2;

	public static final int RATE_PLAY_STORE_APP_COUNT_LIMIT = 10;
	public static final int RATE_GOOGLE_PLUS_APP_COUNT_LIMIT = 5;
	public static final int BIRTHDAY_TIMER_CHANGE_DELAY = 10000;

	public static final int PLUS_ONE_REQUEST_CODE = 555;

	public static final int INIT_UNREAD_NOTIFICATION_INDEX = 0;
	public static final int UNREAD_NOTIFICATION_DF_SIZE = 0;

	/* ad mob */
	public static final String AD_MOB_APP_UNIT = "a14e6591fe7f521";
	
	public static final long WAITING_TIME_CONNECT_XMPP = 5000;

	public static final class NLMediaConfig {
		public static final String MEDIA_NEW_IMAGE = "new_img";

		public static final int MEDIA_IMAGE_MAX_SIZE = 2 * 1024 * 1024; // 2MB

		public static final int MEDIA_VIDEOE_MAX_SIZE = 50 * 1024 * 1024; // 50MB

		public static final int MEDIA_IMAGE_MAX_W = 1024;

		public static final int MEDIA_IMAGE_MAX_H = 768;

		public static final int MEDIA_RESULT_SUCCESS = 0;

		public static final int MEDIA_RESULT_FILE_NOT_FOUND = 1;

		public static final int MEDIA_RESULT_EXCEED_MAXSIZE = 2;

		public static final int MEDIA_RESULT_ERROR_UNDEFINED = 10;
	}

	public static final class NLGender {
		public static final int UNIDENTIFY = -1;
		public static final int MALE = 0;
		public static final int FEMALE = 1;
		public static final int OTHER = 2;
	}

	public static final class WSCodeResult {
		public static final int UNKNOW = -1;
		public static final int NO_ERROR = 0;
		public static final int SUCCESSFUL = 1;
		public static final int FOLLOW_BUG = 9;
	}
}
