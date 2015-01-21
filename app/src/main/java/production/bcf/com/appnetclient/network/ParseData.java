package production.bcf.com.appnetclient.network;

import android.content.Context;

import com.bigcatfamily.networkhandler.ParserDataHelperCore;
import com.bigcatfamily.networkhandler.db.BaseMethodParams;
import com.bigcatfamily.networkhandler.db.DataResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import production.bcf.com.appnetclient.models.Feed;

public class ParseData extends ParserDataHelperCore {
	private static final String TAG = ParseData.class.getSimpleName();
	private Context context;

	private ParseData(Context context) {
		this.context = context;
	}

	private volatile static ParseData uniqueInstance;

	public static ParseData getInstance(Context context) {
		if (uniqueInstance == null) {
			synchronized (DataHelper.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new ParseData(context);
				}
			}
		}
		return uniqueInstance;
	}

	@Override
	public DataResult parseData(JSONObject json, BaseMethodParams methodparam) {
		DataResult result = new DataResult();
		try {
			switch (((MethodParams) methodparam).getName()) {
			case GET_FEED_DATA:
				result = parseFeedRequest(json);
				break;

			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

    private boolean isError(JSONObject json) {
		return !json.isNull("errors");
	}

	private DataResult parseFeedRequest(JSONObject json) throws JSONException {
		DataResult dataResult = new DataResult();

		if (!isError(json)) {
			ArrayList<Feed> feeds = new ArrayList<Feed>();

			JSONArray jsonArray = json.getJSONArray("data");

			for (int i = 0; i < jsonArray.length(); i++) {
				Feed image = parseFeed(jsonArray.getJSONObject(i));
                feeds.add(image);
			}

			dataResult.setData(feeds);
		} else {
			JSONArray errorsJSON = json.getJSONArray("errors");
			String error = errorsJSON.getJSONObject(0).getString("error_message");
			dataResult.setErrorMessage(error);
		}

		return dataResult;
	}


	private Feed parseFeed(JSONObject jsonObject) throws JSONException {
        String content = getJSONString(jsonObject, "text");
        String contentHTML = getJSONString(jsonObject, "html");

        JSONObject userJSON = jsonObject.getJSONObject("user");

        String avatarUrl = getJSONString(userJSON.getJSONObject("avatar_image"), "url");
		String username = getJSONString(userJSON, "username");

		
		return new Feed(avatarUrl,username,content,contentHTML);

	}
}
