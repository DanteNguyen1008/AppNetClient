package com.bigcatfamily.networkhandler;

import org.json.JSONObject;

import com.bigcatfamily.networkhandler.db.BaseMethodParams;
import com.bigcatfamily.networkhandler.db.DataResult;

public interface IParseDataHelper {
	public DataResult parseData(JSONObject jObj, BaseMethodParams methodparam);
}
