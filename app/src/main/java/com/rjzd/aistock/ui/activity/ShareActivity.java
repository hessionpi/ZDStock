package com.rjzd.aistock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * Created by Hition on 2017/8/15.
 */

public class ShareActivity extends BaseActivity implements UMShareListener,IFillDataView{

    protected int userId;
    private PointsPresenter pointsPresenter;
    protected boolean isNeedAddPoints = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointsPresenter = new PointsPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = UserInfoCenter.getInstance().getUserId();
    }

    /**
     * 打开分享面板
     */
    protected void openShareBoard(String shareUrl,String shareTitle,String shareDrawable) {
        ShareAction mShareAction = new ShareAction(this);

        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setTitleText("分享到");
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(false);

        // 分享链接
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareTitle);
        web.setDescription(Constants.SHARE_CONTENT);
        UMImage thumb;
        if(TextUtils.isEmpty(shareDrawable)){
            thumb = new UMImage(this, R.drawable.share_logo);
        }else{
            thumb = new UMImage(this,shareDrawable);
        }
        web.setThumb(thumb);

        mShareAction.withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(this)
                .open(config);

        if(0 == userId ){
            ToastUtils.show(this,"登录后分享可以赚取积分解锁特权", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // attention to this below ,must add this
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(SHARE_MEDIA platform) {
        //分享开始的回调
    }

    @Override
    public void onResult(SHARE_MEDIA platform) {
        LogUtil.d("plat", "platform" + platform+"分享成功了");
        // 如果用户已经登录分享出去则进行加积分
        if(!isNeedAddPoints){
            return ;
        }

        if(userId > 0){
            if(NetWorkUtil.isNetworkConnected(this)){
                pointsPresenter.earnPointsByshare(userId);
            }
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        LogUtil.e("share", platform + " 分享失败啦");
        if (t != null) {
            LogUtil.d("throw", "throw:" + t.getMessage());
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        LogUtil.e("share", platform + " 分享取消了");
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch(dsTag){
            case Constants.DS_TAG_EARNPOINTS_BY_SHARE:
                IsSuccess isSuccess = (IsSuccess) data;
                if(StatusCode.OK.getValue() == isSuccess.get_status().getValue()){
                    if(isSuccess.is_data()){
                        ToastUtils.show(this,"分享成功，赚取3积分");
                    }
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
        pointsPresenter.onUnsubscribe();
    }
}
