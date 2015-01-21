package production.bcf.com.appnetclient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigcatfamility.application.widget.MenuItem;

import production.bcf.com.appnetclient.AppConstant;
import production.bcf.com.appnetclient.MainActivity;
import production.bcf.com.appnetclient.R;

public class SlideMenuFragment extends com.bigcatfamility.application.fragment.SlideMenuFragment {

	private MenuItem menuMain, menuHelp, menuInfo, menuRate;
	private int selectedMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_slide_menu, null);
        menuMain = ((MenuItem) view.findViewById(R.id.mnu_main_page));
        menuHelp = ((MenuItem) view.findViewById(R.id.mnu_helps));
		menuInfo = ((MenuItem) view.findViewById(R.id.mnu_information));
		menuRate = ((MenuItem) view.findViewById(R.id.mnu_rate_app));

		menuHelp.setOnClickListener(this);
		menuInfo.setOnClickListener(this);
		menuRate.setOnClickListener(this);

		selectedMenu = R.id.mnu_main_page;
        menuMain.setCurrentMenu(true, 0);

		return view;
	}

	@Override
	public void onClick(View v) {
		mActivity.hideKeyboard();

		if (selectedMenu == v.getId()) {
			((MainActivity) mActivity).closeLeftMenu();
			return;
		}

		selectedMenu = v.getId();

		switch (v.getId()) {
		case R.id.mnu_main_page:
			clearFocusMenu();
            menuMain.setCurrentMenu(true, 0);
			changeCurrentMenu(AppConstant.MENU_MAIN);
			break;
		case R.id.mnu_helps:
			clearFocusMenu();
			menuHelp.setCurrentMenu(true, 0);
			changeCurrentMenu(AppConstant.MENU_HELP);
			break;
		case R.id.mnu_information:
			clearFocusMenu();
			menuInfo.setCurrentMenu(true, 0);
			changeCurrentMenu(AppConstant.MENU_INFO);
			break;
		case R.id.mnu_rate_app:
			mActivity.ReteApplication();
			break;
		default:
			break;
		}
	}

	@Override
	public void ClearInstance() {

	}

	@Override
	public void clearFocusMenu() {
        menuMain.setCurrentMenu(false, 0);
		menuHelp.setCurrentMenu(false, 0);
		menuInfo.setCurrentMenu(false, 0);
		menuRate.setCurrentMenu(false, 0);
	}

}
