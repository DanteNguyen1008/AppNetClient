package production.bcf.com.appnetclient.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import production.bcf.com.appnetclient.R;
import production.bcf.com.appnetclient.Utility.Utility;
import production.bcf.com.appnetclient.models.Feed;

/**
 * Created by annguyenquocduy on 1/18/15.
 */

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 6;

    private ArrayList<Feed> mDataList;
    private int mIAvatarCircleWidth;
    private Context context;
    private int lastAnimatedPosition = -1;
    private int itemsCount = 0;

    public AdapterFeed(Context context, ArrayList<Feed> dataList) {
        this.context = context;
        this.mDataList = dataList;
        this.mIAvatarCircleWidth = (int) context.getResources().getDimension(R.dimen.avatar_rounded_width);
    }

    public void notifyDataSetChanged(ArrayList<Feed> datas) {
        mDataList = datas;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mDataList != null)
            return mDataList.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);

        Feed feed = mDataList.get(position);

        ImageLoader.getInstance().displayImage(feed.avatarUrl, holder.avatar,
                Utility.getDisplayImageOptionWithRoundedImage(mIAvatarCircleWidth));

        holder.username.setText(feed.username);
        holder.content.setText(feed.content);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_feed, parent, false);
        return new ViewHolder(v);
    }

    private void runEnterAnimation(View view, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (position >= ANIMATED_ITEMS_COUNT - 1) {
                return;
            }

            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utility.getScreenHeight(context));
                view.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(1000)
                        .start();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username, content;

        public ViewHolder(View v) {
            super(v);
            avatar = (ImageView) v.findViewById(R.id.img_avatar);
            username = (TextView) v.findViewById(R.id.txt_username);
            content = (TextView) v.findViewById(R.id.txt_content);
        }
    }
}
