package production.bcf.com.appnetclient;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.bigcatfamility.application.base.BaseActivity;
import com.bigcatfamility.application.contranst.BaseAppConstant;

import java.util.HashMap;
import java.util.Stack;

import production.bcf.com.appnetclient.fragments.FragmentFeed;
import production.bcf.com.appnetclient.fragments.SlideMenuFragment;


public class MainActivity extends BaseActivity {

    private FrameLayout mMenuLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private MaterialMenuIconToolbar mMaterialIcon;
    private boolean isDrawerOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLeftMenu(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction t = mFragmentManager.beginTransaction();
            mFrag = new SlideMenuFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();
        } else
            mFrag = (SlideMenuFragment) mFragmentManager.findFragmentById(R.id.menu_frame);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mMaterialIcon = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {

            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };


        mMaterialIcon.setNeverDrawTouch(true);

        mMenuLayout = (FrameLayout) findViewById(R.id.left_drawer);


        /*drawer*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_close, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mMaterialIcon.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset);
            }

            /** Called when a drawer has settled in a completely closed state. */
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(title);
                // invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                isDrawerOpened = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                // invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened)
                        mMaterialIcon.setState(MaterialMenuDrawable.IconState.ARROW);
                    else
                        mMaterialIcon.setState(MaterialMenuDrawable.IconState.BURGER);
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.closeDrawer(mMenuLayout);

        /*toolbar with material icon*/
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDrawerOpened)
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                else
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

            }
        });
    }

    @Override
    protected void ChangeFragmentOnChangeCurrentMenu(String menuName) {
        if (currentMenu.equals(AppConstant.MENU_MAIN))
            pushFragments(new FragmentFeed(), false, true);
        if (currentMenu.equals(AppConstant.MENU_HELP))
            pushFragments(new FragmentFeed(), false, true);
        if (currentMenu.equals(AppConstant.MENU_INFO))
            pushFragments(new FragmentFeed(), false, true);
    }

    @Override
    protected void SetNullForCustomVariable() {

    }


    @Override
    protected void SetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void closeLeftMenu() {
        mDrawerLayout.closeDrawer(mMenuLayout);
    }

    @Override
    protected void openLeftMenu() {
        mDrawerLayout.openDrawer(mMenuLayout);
    }

    @Override
    public String GetActivityID() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    protected void initFragmentScreen() {
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstant.MENU_MAIN, new Stack<Fragment>());
        mStacks.put(AppConstant.MENU_HELP, new Stack<Fragment>());
        mStacks.put(AppConstant.MENU_INFO, new Stack<Fragment>());

        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setMessage(getString(R.string.loading));

        Bundle bundle = getIntent().getExtras();
        currentMenu = AppConstant.MENU_MAIN;

        if (bundle != null) {
            currentMenu = bundle.getString(BaseAppConstant.INTENT_AL_SCREEEN_NAME_EXTRA);
        }

        if (currentMenu != null) {
            ChangeFragmentOnChangeCurrentMenu(currentMenu);
        }
    }


}
