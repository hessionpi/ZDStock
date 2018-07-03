package com.rjzd.aistock.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.aistock.api.Count;
import com.rjzd.aistock.api.RangeFlag;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.LoginPresenter;
import com.rjzd.aistock.presenter.NotificationPresenter;
import com.rjzd.aistock.ui.fragment.AIFragment;
import com.rjzd.aistock.ui.fragment.MarketsFragment;
import com.rjzd.aistock.ui.fragment.ZiXuanFragment;
import com.rjzd.aistock.ui.views.TabIconView;
import com.rjzd.aistock.utils.CheckVersionUtils;
import com.rjzd.aistock.utils.SPUtils;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.permission.PermissionManager;
import com.rjzd.commonlib.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,IFillDataView{
    public static final String TAG = "MainActivity";

    @Bind(R.id.iv_user)
    ImageView mUser;
    @Bind(R.id.fl_search)
    FrameLayout mSearch;
    @Bind(R.id.rg_range)
    RadioGroup mRangeGroup;

    @Bind(R.id.tab_ai)
    TabIconView aiTabView;
    @Bind(R.id.tab_markets)
    TabIconView marketsTabView;
    @Bind(R.id.tab_zixuan)
    TabIconView zixuanTabView;
    @Bind(R.id.fl_msg)
    FrameLayout mMsgLayout;
    @Bind(R.id.tv_unread)
    TextView mUnread;

    @Bind(R.id.iv_user_remind)
    ImageView mRemind;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    /**
     * Fragment类型
     */
    public static final int AI = 0;
    public static final int MARKETS = 1;
    public static final int ZIXUAN = 2;

    int currentFragmentType;
    Fragment currentFragment;

    private AIFragment aiFragment;
    private MarketsFragment marketsFragment;
    private ZiXuanFragment zixuanFragment;

    private LoginPresenter presenter;
    private NotificationPresenter notificationPresenter;

    /**
     * 第一次按下back键时间
     */
    long firstTimePressBack = 0;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new LoginPresenter();
        notificationPresenter = new NotificationPresenter(this);
        initView();
        // 崩溃、HOME长时间等造成fragment失效问题 解决
        if(null != savedInstanceState){
            int tab = savedInstanceState.getInt("tab");
            setTab(tab);
            LogUtil.e(TAG, "onCreate get the savedInstanceState tab=" + tab);
        }else{
            setTab(ZIXUAN);
        }

        // Android 6.0 以后动态赋予权限   随便给一个requestCode就行，因为当前activity不关心回调函数
        PermissionManager.getIns()
                .requestPermissions(MainActivity.this
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , 0,null);
        PermissionManager.getIns()
                .requestPermissions(MainActivity.this
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , 0,null);
        PermissionManager.getIns()
                .requestPermissions(MainActivity.this
                        , Manifest.permission.READ_PHONE_STATE
                        , 0,null);

        CheckVersionUtils.getInstance().checkUpdate(this,false);
        // 注册广播接收者
        registerBroadcast();
    }

    private void initView() {
        // 内容显示区域
        fragmentManager = getSupportFragmentManager();
        getPushExtraType(getIntent());
        //设置点击监听
        mUser.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mRangeGroup.setOnCheckedChangeListener(this);

        aiTabView.setOnClickListener(this);
        marketsTabView.setOnClickListener(this);
        zixuanTabView.setOnClickListener(this);
        mMsgLayout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userId = UserInfoCenter.getInstance().getUserId();
        if(userId>0){
            // 每日活跃加积分
            presenter.earnPointsBydailyActive(userId);
            // 注册JPush别名.已登录用户用uid作为alias
            JPushInterface.setAlias(this,0,String.valueOf(userId));
            boolean hasSynchronize = (boolean) SPUtils.get("has_synchronize",false);
            if(!hasSynchronize){
                presenter.synchronizePortfolio(userId);
                return ;
            }
            presenter.getPortfolioFromServer(userId);
        }else{
            //注册JPush别名,未登录用户用unregister来作为别名
            JPushInterface.setAlias(this,0,"unregister");
        }

        if(userId>0){
            // 获取未读消息条数
            notificationPresenter.getTotalUnread(userId);
        }

        if(Constants.recommendPlateNew || Constants.recommendStockNew || Constants.recommendWeeklyNew){
            mRemind.setVisibility(View.VISIBLE);
        }else{
            mRemind.setVisibility(View.GONE);
        }
    }

    private void getPushExtraType (Intent intent){
        Bundle bundle = intent.getExtras();
        if(null != bundle){
            String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject extraJson = new JSONObject(jsonExtra);
                String actionType = extraJson.optString("type");
                if(TextUtils.isEmpty(actionType)){
                    return ;
                }

                switch(actionType){
                    case Constants.ACTION_PREDICT:
                        setTab(ZIXUAN);
                        break;

                    case Constants.ACTION_AITRANSFER:
                        String aiId = extraJson.optString("aiId");
                        if(!TextUtils.isEmpty(aiId)){
                            AiAdjustdynamicActivity.startActivity(this, aiId);
                        }
                        break;

                    case Constants.ACTION_ANNOUNCEMENT:
                        MessageNotificationActivity.startActivity(this);
                        break;

                    case Constants.ACTION_DAILYBESTPLATE:
                    case Constants.ACTION_DAILYBESTSTOCK:
                        PersonalCenterActivity.startActivity(this);
                        break;

                    case Constants.ACTION_INVITE:
                        InviteFriendsActivity.startActivity(this);
                        break;

                    case Constants.ACTION_AI_WEEKLY:
                        AIWeeklyActivity.startActivity(this);
                        break;
                }
            } catch (JSONException e) {
                LogUtil.e(e.getMessage());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getPushExtraType(intent);
    }

    /**
     * 把此方法修改为不调用super的onSaveInstanceState方法，以便解决Tab不能切换的Bug。
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // 崩溃、HOME长时间等造成fragment失效问题解决,不能super.onSaveInstanceState(outState);(会造成首页fragment覆盖问题)
        outState.putInt("tab", currentFragmentType);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag){
            case Constants.DS_TAG_NOTIFICATION_UNREAD:
                // 获取未读消息总条数
                Count totalUnread = (Count) data;
                if(StatusCode.OK.getValue() == totalUnread.get_status().getValue()){
                    int total = totalUnread.get_data();
                    if(0 == total){
                        mUnread.setVisibility(View.GONE);
                    }else if(0 < total){
                        mUnread.setVisibility(View.VISIBLE);
                        if(total > 9 ){
                            mUnread.setText(total+"+");
                        }else{
                            mUnread.setText(total+"");
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_user:
                PersonalCenterActivity.startActivity(MainActivity.this);
                break;

            case R.id.fl_search:
                SearchActivity.startActivity(MainActivity.this);
                break;

            case R.id.fl_msg:
                MessageNotificationActivity.startActivity(this);
                break;

            case R.id.tab_zixuan:
                setTab(ZIXUAN);
                mRangeGroup.setVisibility(View.GONE);
                mSearch.setVisibility(View.VISIBLE);
                break;

            case R.id.tab_ai:
                setTab(AI);
                mRangeGroup.setVisibility(View.GONE);
                mSearch.setVisibility(View.VISIBLE);
                break;

            case R.id.tab_markets:
                setTab(MARKETS);
                mRangeGroup.setVisibility(View.VISIBLE);
                mSearch.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //两秒内再次按下则退出
        long delta = System.currentTimeMillis() - firstTimePressBack;
        if (delta > ZdStockApp.QUIT_INTERVAL) {
//            ToastUtils.show(this,R.string.press_again_exit);
            showToast(R.string.press_again_exit);
            firstTimePressBack = System.currentTimeMillis();
        } else {
            finishActivity(this);
        }
    }

    /**
     * 控制Fragment、Tab的显示、跳转。
     */
    public void setTab(int pageIndex){
        currentFragmentType = pageIndex;
        if (currentFragment != null) {
            // onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
            // 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()
            fragmentManager.beginTransaction()
                    .hide(currentFragment)
                    .commitAllowingStateLoss();
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments();
        switch (pageIndex) {
            case AI:
                aiTabView.setIconStatus(TabIconView.CHOSEN);
                if (null == aiFragment) {
                    aiFragment = new AIFragment();
                    fragmentTransaction.add(R.id.flFragmentContainer, aiFragment);
                } else {
                    fragmentTransaction.show(aiFragment);
                }
                currentFragment = aiFragment;
                break;

            case MARKETS:
                marketsTabView.setIconStatus(TabIconView.CHOSEN);
                if (null == marketsFragment) {
                    marketsFragment = new MarketsFragment();
                    fragmentTransaction.add(R.id.flFragmentContainer, marketsFragment);
                } else {
                    fragmentTransaction.show(marketsFragment);
                }
                currentFragment = marketsFragment;
                break;

            case ZIXUAN:
                zixuanTabView.setIconStatus(TabIconView.CHOSEN);
                if (null == zixuanFragment) {
                    zixuanFragment = new ZiXuanFragment();
                    fragmentTransaction.add(R.id.flFragmentContainer, zixuanFragment);
                } else {
                    fragmentTransaction.show(zixuanFragment);
                }
                currentFragment = zixuanFragment;
                break;
        }
        fragmentTransaction.commit();   // 提交
    }

    private void clearChioce() {
        aiTabView.setIconStatus(TabIconView.UNCHOSEN);
        marketsTabView.setIconStatus(TabIconView.UNCHOSEN);
        zixuanTabView.setIconStatus(TabIconView.UNCHOSEN);
    }

    /**
     * 隐藏Fragment
     *
     */
    private void hideFragments() {
        if (aiFragment != null) {
            fragmentTransaction.hide(aiFragment);
        }

        if (marketsFragment != null) {
            fragmentTransaction.hide(marketsFragment);
        }

        if (zixuanFragment != null) {
            fragmentTransaction.hide(zixuanFragment);
        }
    }

    // 顶部涨幅、跌幅切换
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_rise:
                marketsFragment.switchRiseOrFall(Color.parseColor("#fca2a2"),Color.parseColor("#d63535"), RangeFlag.RANGE_RISE);
                break;

            case R.id.rb_fall:
                marketsFragment.switchRiseOrFall(Color.parseColor("#73d9ad"),Color.parseColor("#119d3e"), RangeFlag.RANGE_FALL);
                break;
        }
    }

    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rjzd.aistock.announcement");
        filter.addAction("com.rjzd.aistock.recommendplate");
        filter.addAction("com.rjzd.aistock.recommendstock");
        filter.addAction("com.rjzd.aistock.aiweekly");
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(broadcastReceiver, filter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch(action){
                case "com.rjzd.aistock.announcement":
                    int unRead = intent.getIntExtra("msg_unread",0);
                    if(0 == unRead){
                        mUnread.setVisibility(View.GONE);
                    }else if(0 < unRead){
                        mUnread.setVisibility(View.VISIBLE);
                        if(unRead > 9 ){
                            mUnread.setText(unRead+"+");
                        }else{
                            mUnread.setText(unRead+"");
                        }
                    }
                    break;

                case "com.rjzd.aistock.recommendplate":
                    Constants.recommendPlateNew = true;
                    mRemind.setVisibility(View.VISIBLE);
                    break;

                case "com.rjzd.aistock.recommendstock":
                    Constants.recommendStockNew = true;
                    mRemind.setVisibility(View.VISIBLE);
                    break;

                case "com.rjzd.aistock.aiweekly":
                    mRemind.setVisibility(View.VISIBLE);
                    Constants.recommendWeeklyNew = true;
                    break;
            }
            abortBroadcast();   //接收到广播后中断广播
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        notificationPresenter.onUnsubscribe();
        unregisterReceiver(broadcastReceiver);//取消注册
    }
}
