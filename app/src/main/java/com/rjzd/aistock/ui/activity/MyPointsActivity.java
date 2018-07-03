package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.ui.PointsChangedListener;
import com.rjzd.aistock.ui.fragment.PrivilegeFragment;
import com.rjzd.aistock.ui.fragment.RecordFragment;
import com.rjzd.aistock.ui.fragment.TaskFragment;
import com.rjzd.aistock.view.IFillDataView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的积分
 *
 * Created by Hition on 2017/7/19.
 */

public class MyPointsActivity extends BaseActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, IFillDataView ,PointsChangedListener {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.my_points)
    TextView mPointsView;
    @Bind(R.id.rg_points)
    RadioGroup mPointsGroup;


    private int userId ;
    public static final int POINTS_PRIVILEGE = 0;
    public static final int MY_TASK = 1;
    public static final int POINTS_RECORD = 2;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PrivilegeFragment privilegeFragment;
    private TaskFragment myTaskFragment;
    private RecordFragment recordFragment;

    private PointsPresenter presenter;
    private Bundle bundle;

    public static void startActivity(Context context,int currentTab) {
        Intent intent = new Intent(context, MyPointsActivity.class);
        intent.putExtra("current_tab",currentTab);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.cl_fea41a);
        setContentView(R.layout.activity_my_points);
        ButterKnife.bind(this);
        bundle = new Bundle();
        initView();
        presenter = new PointsPresenter(this);
    }

    private void initView() {
        userId = UserInfoCenter.getInstance().getUserId();
        int currentTab = getIntent().getIntExtra("current_tab",POINTS_PRIVILEGE);
        if(POINTS_PRIVILEGE == currentTab){
            mPointsGroup.check(R.id.rb_privilege);
        }else if(MY_TASK == currentTab){
            mPointsGroup.check(R.id.rb_task);
        }

        bundle.putInt("user_id",userId);
        fragmentManager = getSupportFragmentManager();
        setTab(currentTab);

        mBack.setOnClickListener(this);
        mPointsGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMyPoints(userId);
    }

    public void setTab(int pageIndex){
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments();
        switch (pageIndex) {
            case POINTS_PRIVILEGE:
                if (null == privilegeFragment) {
                    privilegeFragment = new PrivilegeFragment();
                    privilegeFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.container_points, privilegeFragment);
                } else {
                    fragmentTransaction.show(privilegeFragment);
                }
                break;

            case MY_TASK:
                if (null == myTaskFragment) {
                    myTaskFragment = new TaskFragment();
                    myTaskFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.container_points, myTaskFragment);
                } else {
                    fragmentTransaction.show(myTaskFragment);
                }
                break;

            case POINTS_RECORD:
                if (null == recordFragment) {
                    recordFragment = new RecordFragment();
                    recordFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.container_points, recordFragment);
                } else {
                    fragmentTransaction.show(recordFragment);
                }
                break;
        }
        fragmentTransaction.commit();   // 提交
    }

    /**
     * 隐藏Fragment
     *
     */
    private void hideFragments() {
        if (privilegeFragment != null) {
            fragmentTransaction.hide(privilegeFragment);
        }

        if (myTaskFragment != null) {
            fragmentTransaction.hide(myTaskFragment);
        }

        if (recordFragment != null) {
            fragmentTransaction.hide(recordFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                finishActivity(this);
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId){
            case R.id.rb_privilege:
                setTab(POINTS_PRIVILEGE);
                break;

            case R.id.rb_task:
                setTab(MY_TASK);
                break;

            case R.id.rb_record:
                setTab(POINTS_RECORD);
                break;

            default:
                setTab(POINTS_PRIVILEGE);
                break;
        }
    }

    @Override
    public void onPointsChanged() {
        presenter.getMyPoints(userId);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch(dsTag){
            case Constants.DS_TAG_MY_POINTS:
                UserPoints myPoints = (UserPoints) data;
                if(StatusCode.OK.getValue() == myPoints.get_status().getValue()){
                    mPointsView.setText(String.valueOf(myPoints.get_points()));
                }
                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }

}
