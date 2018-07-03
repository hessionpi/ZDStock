package com.rjzd.aistock.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.Privilege;
import com.rjzd.aistock.api.PrivilegeData;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.api.UserPoints;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.presenter.RecommendPresenter;
import com.rjzd.aistock.ui.views.ToolbarView;
import com.rjzd.aistock.ui.views.pull2refresh.PullToRefreshView;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.PixelUtil;
import com.rjzd.commonlib.utils.ToastUtils;
import net.qiujuer.genius.blur.StackBlur;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 每日今个话每日牛奔父类
 *
 * Created by Hition on 2017/8/10.
 */

public abstract class BlurActivity extends BaseActivity implements IFillDataView,View.OnClickListener{

    @Bind(R.id.toolbar)
    ToolbarView mToolbar;
    @Bind(R.id.refrsh_view)
    PullToRefreshView mRefresh;
    @Bind(R.id.rv_recommend)
    RecyclerView mRecommendView;
    @Bind(R.id.unlock_layout)
    FrameLayout mBlurLayout;
    @Bind(R.id.unlock)
    TextView mUnlock;
    @Bind(R.id.btn_unlock)
    Button mUnlockBtn;

    private PointsPresenter pointsPresenter;
    protected RecommendPresenter presenter;

    protected static final int PAGE_SIZE = 2;
    protected int pageIndext;
    protected boolean hasLoad = false;
    protected int userId;
    protected boolean isUnlock = false;
    private int currentPoints;
    private int costPoints;
    private String privilegeId;
    protected List<String> recommends = new ArrayList<>();
    protected String recommendDialogTitle;
    protected Handler mHandler = new Handler();

    protected ClickEventType clickEvent;
    private enum ClickEventType{
        EVENT_LOGIN,EVENT_EARN_POINTS,EVENT_UNLOCK
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_recommend);
        ButterKnife.bind(this);
        pointsPresenter = new PointsPresenter(this);
        presenter = new RecommendPresenter(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isUnlock){
            return ;
        }

        userId = UserInfoCenter.getInstance().getUserId();
        //  未解锁，先判断解锁状态
        if(userId > 0){
            if(NetWorkUtil.isNetworkConnected(this)){
                pointsPresenter.getMyPoints(userId);
            }else{
                super.showToast(R.string.network_error);
            }
        }else{
            mUnlock.setText("该功能只有登录用户解锁可见!");
            mUnlockBtn.setText(R.string.go_login);
            clickEvent = ClickEventType.EVENT_LOGIN;
            delayGetRecommends();
        }
    }

    protected abstract void initView();

    protected abstract void delayGetRecommends();

    protected void applyBlur(View mBlurLayout) {
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        // 获取当前窗口快照，相当于截屏
        Bitmap bmp1 = view.getDrawingCache();
        int height = getOtherHeight();
        // 除去状态栏和标题栏
        Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height,bmp1.getWidth(), bmp1.getHeight() - height);
        blur(bmp2, mBlurLayout);
    }

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 5.0f;//图片缩放比例；
        float radius = 4.0f;//模糊程度

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        // 调用JNI进行高斯模糊
        overlay = StackBlur.blurNatively(overlay, (int) radius, true);
        view.setBackground(new BitmapDrawable(getResources(), overlay));
        //打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
        LogUtil.e("hition==", "blur time:" + (System.currentTimeMillis() - startMs));
    }

    /**
     * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
     * @return int
     */
    private int getOtherHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
//        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = PixelUtil.dp2px(47.5f)+PixelUtil.dp2px(20);
        return statusBarHeight + titleBarHeight;
    }

    @Override
    public void fillData(Object data, int dsTag) {
        dismissLoading();
        if(mRefresh.isRefreshing()){
            mRefresh.setRefreshing(false);
        }

        switch(dsTag){
            case Constants.DS_TAG_MY_POINTS:
                UserPoints points = (UserPoints) data;
                if(StatusCode.OK.getValue() == points.get_status().getValue()){
                    currentPoints = points.get_points();
                }

                if(NetWorkUtil.isNetworkConnected(this)){
                    pointsPresenter.getPrivilegeStatus(userId,recommends);
                }else{
                    super.showToast(R.string.network_error);
                }
                break;

            case Constants.DS_TAG_PRIVILEGESTATUS:
                PrivilegeData privilegeData = (PrivilegeData) data;
                if(StatusCode.OK.getValue() == privilegeData.get_status().getValue()){
                    List<Privilege> privilegeList = privilegeData.get_data();
                    if(!privilegeList.isEmpty()){
                        isUnlock = 1 == privilegeList.get(0).get_unlock();
                        costPoints = privilegeList.get(0).get_costPoints();
                        privilegeId = privilegeList.get(0).get_privilegeId();
                    }
                }

                LogUtil.e("hition==super",isUnlock+"");
                delayGetRecommends();
                if(!isUnlock){
                    // 积分不足显示
                    if(currentPoints < costPoints){
                        clickEvent = ClickEventType.EVENT_EARN_POINTS;
                        mUnlock.setText("当前积分"+currentPoints+"分，解锁该功能需要"+costPoints+"分，积分不足！");
                        mUnlockBtn.setText(R.string.go_earn_points);
                    }else{  // 积分足显示解锁
                        clickEvent = ClickEventType.EVENT_UNLOCK;
                        mUnlock.setText("当前积分"+currentPoints+"分，可以解锁该功能。");
                        mUnlockBtn.setText(R.string.unlock_now);
                    }
                }
                break;

            case Constants.DS_TAG_UNLOCKPRIVILEGE:
                IsSuccess isSuccess = (IsSuccess) data;
                if(StatusCode.OK.getValue() == isSuccess.get_status().getValue()){
                    if(isSuccess.is_data()){
                        mBlurLayout.setVisibility(View.GONE);
                        isUnlock = true;
                    }
                }else{
                    ToastUtils.show(this,isSuccess.get_msg());
                }
                break;
        }
    }

    @Override
    public void showFailedView() {
        dismissLoading();
        if(mRefresh.isRefreshing()){
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_unlock:
                switch (clickEvent){
                    case EVENT_LOGIN:
                        LoginActivity.startActivity(this);
                        break;

                    case EVENT_EARN_POINTS:
                        MyPointsActivity.startActivity(this, MyPointsActivity.MY_TASK);
                        break;

                    case EVENT_UNLOCK:
                        String unlockMsg = getString(R.string.unlock_msg);
                        DialogManager.showSelectDialogWithTitle2(this, recommendDialogTitle,
                                String.format(unlockMsg, costPoints),
                                R.string.unlock_now,
                                R.string.unlock_not,
                                true, new DialogManager.DialogListener() {
                                    @Override
                                    public void onNegative() {

                                    }

                                    @Override
                                    public void onPositive() {
                                        pointsPresenter.unlockPrivilege(userId,privilegeId);
                                    }
                                });
                        break;
                }
                break;

            case R.id.unlock_layout:
                LogUtil.d("高斯模糊北京已经被点击了");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pointsPresenter.onUnsubscribe();
        presenter.onUnsubscribe();
    }
}
