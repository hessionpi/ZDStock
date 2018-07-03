package com.rjzd.aistock.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.DownloadService;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AppUpdate;
import com.rjzd.aistock.api.AppUpdateInfo;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.presenter.AppUpdatePresenter;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.PixelUtil;
import com.rjzd.commonlib.utils.ToastUtils;

import butterknife.ButterKnife;


/**
 * Created by Hition on 2017/5/8.
 */

public class CheckVersionUtils implements IFillDataView{

    private Context mContext;

    private static CheckVersionUtils instance;
    private boolean isNeedShowNewerToast = false;

    private CheckVersionUtils(){ }

    public static CheckVersionUtils getInstance(){
        if(null == instance){
            synchronized (CheckVersionUtils.class){
                if (null == instance){
                    instance = new CheckVersionUtils();
                }
            }
        }
        return instance;
    }

    public void checkUpdate(Context context,boolean isNeedShowNewerToast){
        this.mContext = context;
        this.isNeedShowNewerToast = isNeedShowNewerToast;
        AppUpdatePresenter presenter = new AppUpdatePresenter(this);
        presenter.checkUpdate(mContext.getPackageName(),AppVersionUtil.getVersionCode(context));
    }

    /**
     * 显示更新提示对话框
     *
     * @param updateInfo
     * @return
     */
    private AlertDialog showUpdateDialog(final AppUpdateInfo updateInfo){
        //部分机型 dialog宽度不满出现灰色部分 问题解决
        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_dialog_check_update, null);

        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setContentView(layout);
        dialogWindow.setGravity(Gravity.CENTER);//
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int)(PixelUtil.getScreenWidth(mContext) * 0.85);
        dialogWindow.setAttributes(params);

        TextView mVersion = ButterKnife.findById(layout,R.id.tv_version_name);
        TextView message = ButterKnife.findById(layout,R.id.tv_update_log);
        Button cancel = ButterKnife.findById(layout,R.id.btn_update_ignore);
        Button ok = ButterKnife.findById(layout,R.id.btn_update_now);
        if(null != updateInfo){
            mVersion.setText(updateInfo.get_appVersionName());
            message.setText(updateInfo.get_appUpdateLog());
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, DownloadService.class);
                intent.putExtra("download_version", updateInfo.get_appVersionName());
                intent.putExtra("app_url", updateInfo.get_appUrl());
                mContext.startService(intent);
            }
        });
        return dialog;
    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag){
            case Constants.DS_TAG_CHECK_VERSION:
                AppUpdate updateData = (AppUpdate) data;
                if(StatusCode.OK.getValue() == updateData.get_status().getValue()){
                    AppUpdateInfo updateInfo = updateData.get_data();
                    showUpdateDialog(updateInfo);
                }else if(StatusCode.NEWEST.getValue() == updateData.get_status().getValue()){
                    if(isNeedShowNewerToast){
                        ToastUtils.show(mContext,R.string.is_newer);
                    }
                }
                break;

            default:

                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    @Override
    public void showToast(String msg) {

    }
}
