/**
 * ErrorCode.java
 * 
 * Purpose              :
 * 
 * Optional info        : 
 *
 * @author              : Van Hoang Phuong
 * 
 * @date                : May 31, 2012
 * 
 * @lastChangedRevision : 
 *
 * @lastChangedDate     :
 *
 */
package com.bigcatfamily.networkhandler.db;

/**
 * ErrorCode.java
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
public enum ErrorCode {
	NO_ERROR(0), NETWORK_ERROR(1), SERVER_ERROR(2), UNKNOWN_ERROR(3), TIME_OUT(4), UNAUTHORIZED(5), OK(200), FILE_NOT_FOUND(
	        7),MULTI_PART_ERROR(8),

	APP_API_ERROR(300), APP_LOGIN_OK(200), APP_EMAIL_EXISTS(400), APP_INVALID_REGISTRATION_INFO(401), APP_EMAIL_NOT_VERIFIED(
	        403), APP_RFID_LIMIT_EXCEEDED(403), APP_RFID_DOESNT_EXISTS(404), APP_RFID_BLOCKED(404), APP_CONTACT_ALREADY_EXISTS(
	        404), APP_CONTACT_INVALID_USERID(404), APP_LOGIN_ERROR(411), ERROR_CODE_FROM_WS(10001), ERROR_CODE_PARSING(
	        10002), CHAT_FORBITDENT_ERROR_CODE(10003), CHAT_SERVER_EXCEPTION(10004), CHAT_PRESENCE_CODE_EXCEPTION(10005);

	private int mErrorCode;

	ErrorCode(int errorCode) {
		mErrorCode = errorCode;
	}

	public static ErrorCode getErrorCode(int errorCode) {
		switch (errorCode) {
		case 0:
			return ErrorCode.NO_ERROR;
		case 1:
			return ErrorCode.NETWORK_ERROR;
		case 2:
			return ErrorCode.SERVER_ERROR;
		case 3:
			return ErrorCode.UNKNOWN_ERROR;
		case 4:
			return ErrorCode.TIME_OUT;
		case 5:
			return ErrorCode.UNAUTHORIZED;
		case 403:
			return ErrorCode.CHAT_FORBITDENT_ERROR_CODE;
		case 10001:
			return ErrorCode.ERROR_CODE_FROM_WS;
		case 10002:
			return ErrorCode.ERROR_CODE_PARSING;

		default:
			return ErrorCode.UNKNOWN_ERROR;
		}
	}

	public static boolean IsChatException(ErrorCode code) {
		boolean result = false;
		switch (code) {
		case CHAT_FORBITDENT_ERROR_CODE:
		case CHAT_SERVER_EXCEPTION:
		case CHAT_PRESENCE_CODE_EXCEPTION:
			result = true;
			break;

		default:
			break;
		}

		return result;
	}

	@Override
	public String toString() {
		// Resources res = TLApplication.getInstance().getResources();
		//
		// switch (mErrorCode) {
		// case 1:
		// return res.getString(R.string.error_network);
		//
		// case 2:
		// return res.getString(R.string.error_server);
		//
		// case 3:
		// return res.getString(R.string.error_unknown);
		//
		// case 4:
		// return res.getString(R.string.error_time_out);
		//
		// default:
		// return res.getString(R.string.error_server);
		// }

		// cheat here - quaych
		return "";
	}

	public int getCode() {
		return this.mErrorCode;
	}
}
