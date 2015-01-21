package production.bcf.com.appnetclient.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import production.bcf.com.appnetclient.R;

/**
 * Created by annguyenquocduy on 1/18/15.
 */
public class Utility extends com.bigcatfamility.application.utility.Utility {
    private static int screenHeight = 0;

    public static DisplayImageOptions getDisplayImageOptionWithRoundedImage(int mIAvatarCircleWidth) {
        return new DisplayImageOptions.Builder().resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
                .displayer(new BCFRoundedBitmapDisplayer(mIAvatarCircleWidth))
                .showImageForEmptyUri(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnLoading(R.drawable.ic_launcher).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }
}
