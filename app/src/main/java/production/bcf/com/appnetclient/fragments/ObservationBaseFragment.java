package production.bcf.com.appnetclient.fragments;

import android.util.Log;

import com.bigcatfamility.application.base.BaseFragment;
import com.bigcatfamily.networkhandler.db.DataResult;

import java.util.Observable;
import java.util.Observer;

import production.bcf.com.appnetclient.Utility.Utility;
import production.bcf.com.appnetclient.network.DataHelper;
import production.bcf.com.appnetclient.network.ParseData;

public abstract class ObservationBaseFragment extends BaseFragment implements Observer {

	@Override
	public void onResume() {
		super.onResume();
		DataHelper.getInstance(mActivity, ParseData.getInstance(mActivity)).addObserver(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		DataHelper.getInstance(mActivity, ParseData.getInstance(mActivity)).deleteObserver(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof DataResult) {
			DataResult result = (DataResult) data;
			if (result.getData() != null && Utility.IsEmpty(result.getErrorMessage())) {
				update(result);
			} else {
				/* general error handle place */
				Log.d(getTag(), result.getErrorMessage());
			}
		}
	}

	protected abstract void update(DataResult result);
}
