package production.bcf.com.appnetclient.network;

import android.content.Context;

import com.bigcatfamily.networkhandler.DataHelperCore;
import com.bigcatfamily.networkhandler.ParserDataHelperCore;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import production.bcf.com.appnetclient.AppConstant;

/*extended observable as well*/
public class DataHelper extends DataHelperCore {
    protected DataHelper(Context context, ParserDataHelperCore parseHelper) {
        super(context, parseHelper);
    }

    private volatile static DataHelper uniqueInstance;

    public static DataHelper getInstance(Context context, ParserDataHelperCore parseHelper) {
        if (uniqueInstance == null) {
            synchronized (DataHelper.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DataHelper(context, parseHelper);
                }
            }
        }
        return uniqueInstance;
    }

    public void getFeedData() throws UnsupportedEncodingException,
            JSONException {
        excute(AppConstant.FEED_URL, GET, "", null,
                new MethodParams(Methods.GET_FEED_DATA, null), "GET_FEED_DATA", true);
    }


}
