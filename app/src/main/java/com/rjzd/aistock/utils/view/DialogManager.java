package com.rjzd.aistock.utils.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.CloudFeature;
import com.rjzd.commonlib.utils.PixelUtil;
import butterknife.ButterKnife;
import static com.rjzd.aistock.R.id.img_close;

/**
 * AlertDialog和ProgressDialog 进行封装
 * Created by Hition on 2016/12/28.
 */

public class DialogManager {

    private DialogManager() {
    }

    /**
     * Show dialog with single button.
     *
     * @param context   context
     * @param title_rid 标题资源id
     * @param msg_rid   内容资源id
     * @param ok_rid    确定按钮id
     * @param click     按钮监听
     */
    public static AlertDialog showSingleButton(Context context, int title_rid,
                                               int msg_rid, int ok_rid, DialogInterface.OnClickListener click) {
        return showSingleButton(context, title_rid, context.getString(msg_rid), ok_rid, false,
                click);
    }

    /**
     * 自定义布局的 AlertDialog
     *
     * @param context    context
     * @param msg_rid    内容资源id
     * @param okResId    确定按钮id
     * @param cancelable 点击对话框以外的地方是否可以取消
     * @param listener   按钮监听
     * @return
     */
    public static AlertDialog showSingleButton(Context context,
                                               int msg_rid, int okResId, boolean cancelable, final DialogListener listener) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        dialog.setContentView(R.layout.layout_alert_singlebutton_notitle);

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView message = (TextView) dialog.findViewById(R.id.message);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        message.setText(msg_rid);
        ok.setText(okResId);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositive();
            }
        });
        return dialog;
    }

    /**
     * Show dialog with single button.
     *
     * @param context   context
     * @param title_rid 标题资源id
     * @param msg       提示内容资源id
     * @param ok_rid    确定资源id
     * @param click     按钮监听
     */
    public static AlertDialog showSingleButton(Context context, int title_rid,
                                               String msg, int ok_rid, DialogInterface.OnClickListener click) {
        return showSingleButton(context, title_rid, msg, ok_rid, false, click);
    }

    public static AlertDialog showSingleButton(Context context,
                                               int msg_rid, boolean cancelable, DialogInterface.OnClickListener click) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg_rid);
        builder.setPositiveButton(R.string.sure, click);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    /**
     * Show dialog with single button.
     *
     * @param context    context
     * @param title_rid  标题资源id
     * @param msg        提示内容
     * @param ok_rid     确认按钮资源id
     * @param cancelable 点击对话框以外区域是否可以取消
     * @param click      按钮点击事件
     */
    public static AlertDialog showSingleButton(Context context, int title_rid,
                                               String msg, int ok_rid, boolean cancelable, DialogInterface.OnClickListener click) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title_rid);
        builder.setMessage(msg);
        builder.setPositiveButton(ok_rid, click);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return dialog;
    }

    /**
     * 无按钮dialog
     * @param context                           上下文
     * @param title                             标题
     * @param msg                               内容
     * @param cancelable                        是否可以被取消
     * @return Dialog
     */
    public static Dialog showNoButtonDialog(Context context, String title,String msg,boolean cancelable,int style){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_alert_nobutton, null);

        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int) (PixelUtil.getScreenWidth(context) * 0.9);
        dialogWindow.setAttributes(params);

        ImageView mClose = ButterKnife.findById(layout,R.id.img_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView mTitle = ButterKnife.findById(layout,R.id.tv_title);
        mTitle.setText(title);
        TextView mLogic = ButterKnife.findById(layout,R.id.tv_logic);
        TextView mLogicMsg = ButterKnife.findById(layout,R.id.tv_logic_message);
        TextView mResult = ButterKnife.findById(layout,R.id.tv_result);
        TextView mResultMsg = ButterKnife.findById(layout,R.id.tv_result_message);

        switch(style){
            case Constants.DS_TAG_PREDCTION_RADAR://多因子预测
                mLogicMsg.setText(R.string.analysis_logic_muti_factor_msg);
                break;

            case Constants.DS_TAG_PREDCTION_PERIODICITY:// 周期性分析
                mLogic.setVisibility(View.GONE);
                mLogicMsg.setVisibility(View.GONE);
                mResult.setVisibility(View.GONE);
                break;

            case Constants.DS_TAG_PREDCTION_RELATED_STOCKS:// 关联股
                mLogicMsg.setText(R.string.analysis_logic_relevance_msg);
                break;
        }
        mResultMsg.setText(msg);
        return dialog;
    }


    /**
     * Show dialog with two buttons.
     *
     * @param context     context
     * @param title_rid   标题资源id
     * @param msg_rid     提示内容资源id
     * @param okResId     按钮资源id
     * @param cancelResId 取消按钮资源id
     * @param click       确定按钮事件监听
     */
    public static AlertDialog showSelectDialog(Context context, int title_rid,
                                               String msg_rid, int okResId, int cancelResId, boolean cancelable,
                                               DialogInterface.OnClickListener click) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title_rid != -1) {
            builder.setTitle(title_rid);
        }
        builder.setMessage(msg_rid);
        builder.setPositiveButton(okResId, click);
        builder.setNegativeButton(cancelResId, click);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        return dialog;
    }

    /**
     * Show dialog with two buttons.
     *
     * @param context     context
     * @param title_rid   标题资源id
     * @param msg_rid     提示内容id
     * @param okResId     确认按钮资源id
     * @param cancelResId 取消按钮资源id
     * @param click       确认按钮事件监听
     */
    public static AlertDialog showSelectDialog(Context context, int title_rid,
                                               int msg_rid, int okResId, int cancelResId, boolean cancelable,
                                               DialogInterface.OnClickListener click) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title_rid != -1) {
            builder.setTitle(title_rid);
        }
        builder.setMessage(msg_rid);
        builder.setPositiveButton(okResId, click);
        builder.setNegativeButton(cancelResId, click);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        return dialog;
    }

    /**
     * 自定义布局的 AlertDialog,带有标题、内容、一个按钮
     *
     * @param context    context
     * @param titleRid   标题资源id
     * @param msgRid     提示内容id
     * @param okText     确认按钮内容
     * @param cancelable 点击对话框以外区域是否可取消
     * @return
     */
    public static AlertDialog showSingleButtonWithTitle(Context context, int titleRid, int msgRid, int okText,int warningTxt,boolean cancelable) {
        //部分机型 dialog宽度不满出现灰色部分 问题解决
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_alert_singlebutton_withtitle, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setContentView(layout);
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView title = (TextView) dialogWindow.findViewById(R.id.tv_title);
        TextView message = (TextView) dialogWindow.findViewById(R.id.tv_message);
        TextView mWarning = (TextView) dialogWindow.findViewById(R.id.tv_warning);
        Button mGotIt = (Button) dialogWindow.findViewById(R.id.btn_got_it);

        title.setText(titleRid);
        message.setText(msgRid);
        if(warningTxt == 0){
            mWarning.setVisibility(View.GONE);
        }else{
            mWarning.setVisibility(View.VISIBLE);
            mWarning.setText(warningTxt);
        }

        mGotIt.setText(okText);
        mGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 自定义布局的 AlertDialog,带有标题、内容、两个按钮
     *
     * @param context    context
     * @param titleRid   标题资源id
     * @param msgRid     提示内容资源id
     * @param okRid      确认按钮内容资源id
     * @param cancelRid  取消按钮内容资源id
     * @param cancelable 点击对话框以外区域是否可取消
     * @param listener   确定事件监听
     * @return
     */
    public static AlertDialog showSelectDialogWithTitle(Context context, int titleRid, int msgRid, int okRid,
                                                        int cancelRid, boolean cancelable, final DialogListener listener) {
        return showSelectDialogWithTitle(context, titleRid, context.getString(msgRid), okRid, cancelRid, cancelable, listener);
    }

    /**
     * 自定义布局的 AlertDialog,带有标题、内容、两个按钮
     *
     * @param context
     * @param titleRid
     * @param msg
     * @param okRid
     * @param cancelRid
     * @param cancelable
     * @param listener
     * @return
     */
    public static AlertDialog showSelectDialogWithTitle(Context context, int titleRid, String msg, int okRid,
                                                        int cancelRid, boolean cancelable, final DialogListener listener) {
        //部分机型 dialog宽度不满出现灰色部分 问题解决
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_alert_select_withtitle, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setContentView(layout);
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView title = (TextView) dialogWindow.findViewById(R.id.tv_title);
        TextView message = (TextView) dialogWindow.findViewById(R.id.message);
        TextView cancel = (TextView) dialogWindow.findViewById(R.id.cancel);
        TextView ok = (TextView) dialogWindow.findViewById(R.id.ok);

        title.setText(titleRid);
        title.setTextColor(Color.parseColor("#333333"));
        message.setText(msg);
        cancel.setText(cancelRid);
        ok.setText(okRid);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onNegative();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositive();
            }
        });

        return dialog;
    }

    /**
     * 自定义布局的 AlertDialog,带有标题、内容、两个按钮
     *
     * @param context
     * @param titleStr
     * @param msg
     * @param okRid
     * @param cancelRid
     * @param cancelable
     * @param listener
     * @return
     */
    public static AlertDialog showSelectDialogWithTitle2(Context context, String titleStr, String msg, int okRid,
                                                        int cancelRid, boolean cancelable, final DialogListener listener) {
        //部分机型 dialog宽度不满出现灰色部分 问题解决
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_dialog_select_withtitle, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setContentView(layout);
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView title = (TextView) dialogWindow.findViewById(R.id.tv_title);
        TextView message = (TextView) dialogWindow.findViewById(R.id.tv_message);
        TextView cancel = (TextView) dialogWindow.findViewById(R.id.cancel);
        TextView ok = (TextView) dialogWindow.findViewById(R.id.ok);

        title.setText(titleStr);
        message.setText(msg);
        cancel.setText(cancelRid);
        ok.setText(okRid);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onNegative();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositive();
            }
        });

        return dialog;
    }





    /**
     * 自定义布局的 AlertDialog
     *
     * @param context
     * @param msg_rid
     * @param okText
     * @param cancelText
     * @param cancelable
     * @param listener
     * @return
     */
    public static AlertDialog showSelectDialog(Context context,
                                               int msg_rid, String okText, String cancelText, boolean cancelable, final DialogListener listener) {

        //部分机型 dialog宽度不满出现灰色部分 问题解决
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_alert_select_notitle, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setContentView(layout);
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView title = (TextView) dialogWindow.findViewById(R.id.title);
        TextView cancel = (TextView) dialogWindow.findViewById(R.id.cancel);
        TextView ok = (TextView) dialogWindow.findViewById(R.id.ok);

        title.setText(msg_rid);
        cancel.setText(cancelText);
        ok.setText(okText);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onNegative();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositive();
            }
        });

        return dialog;
    }

    /**
     * 自定义布局的 AlertDialog
     *
     * @param context
     * @param msg_rid
     * @param okResId
     * @param cancelResId
     * @param cancelable
     * @param listener
     * @return
     */
    public static AlertDialog showSelectDialog(Context context,
                                               int msg_rid, int okResId, int cancelResId, boolean cancelable, final DialogListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_alert_select_notitle, null);

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        dialog.setContentView(layout);

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setGravity(Gravity.CENTER);//

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);

        title.setText(msg_rid);
        cancel.setText(cancelResId);
        ok.setText(okResId);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onNegative();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositive();
            }
        });
        return dialog;
    }

    public interface DialogListener extends DialogPositiveListener {
        void onNegative();//消极
    }

    public interface DialogPositiveListener {
        void onPositive();//积极
    }

    /**
     * Show progress dialog.
     *
     * @param context The context
     * @param msg_rid The id of message
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context, int msg_rid) {
        return showProgressDialog(context, 0, msg_rid);
    }

    /**
     * Show progress dialog.
     *
     * @param context        The context
     * @param msg_rid        The id of message
     * @param isCancelable   Is cancelable
     * @param cancelListener The listener for cancel event
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context,
                                                    int msg_rid, boolean isCancelable,
                                                    DialogInterface.OnCancelListener cancelListener) {
        return showProgressDialog(context, 0, msg_rid, isCancelable,
                cancelListener);
    }

    /**
     * Show progress dialog.
     *
     * @param context   The context
     * @param title_rid The id of title.
     * @param msg_rid   The id of message.
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context,
                                                    int title_rid, int msg_rid) {
        return showProgressDialog(context, title_rid,
                context.getString(msg_rid));
    }

    /**
     * Show progress dialog.
     *
     * @param context        The context
     * @param title_rid      The id of title
     * @param msg_rid        The id of message
     * @param isCancelable   Is cancelable
     * @param cancelListener The listener for cancel event
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context,
                                                    int title_rid, int msg_rid, boolean isCancelable,
                                                    DialogInterface.OnCancelListener cancelListener) {
        return showProgressDialog(context, title_rid,
                context.getString(msg_rid), isCancelable, cancelListener);
    }

    /**
     * Show progress dialog.
     *
     * @param context   The context
     * @param title_rid The id of title
     * @param msg       The message text
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context,
                                                    int title_rid, String msg) {
        return showProgressDialog(context, title_rid, msg, false, null);
    }

    /**
     * Show progress dialog.
     *
     * @param context        The context
     * @param title_rid      The id of title
     * @param msg            The message text
     * @param isCancelable   Is cancelable
     * @param cancelListener The listener for cancel event
     * @return The progress dialog
     */
    public static ProgressDialog showProgressDialog(Context context,
                                                    int title_rid, String msg, boolean isCancelable,
                                                    DialogInterface.OnCancelListener cancelListener) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        if (title_rid != 0) {
            progressDialog.setTitle(title_rid);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setOnCancelListener(cancelListener);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        return progressDialog;
    }

    /**
     * 显示云图dialog
     */
    public static Dialog showIchimokuChartDialog(Context context, boolean cancelable,String msg, CloudFeature feature) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ichimokuchart_layout);

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int)(PixelUtil.getScreenWidth(context) * 0.9);
        dialogWindow.setAttributes(params);

        ImageView mClose = ButterKnife.findById(dialog,R.id.img_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView mMessage = ButterKnife.findById(dialog,R.id.tv_message);
        mMessage.setText(msg);
        RecyclerView rv_raise = ButterKnife.findById(dialog, R.id.rv_raise);
        RecyclerView rv_fall = ButterKnife.findById(dialog, R.id.rv_fall);
        RecyclerView rv_flat = ButterKnife.findById(dialog, R.id.rv_flat);
        dialog.setCancelable(cancelable);
        StockPredictionUtil.showIchimokuExplain(context, feature, rv_raise, rv_fall, rv_flat);
        dialog.show();
        return dialog;
    }

    public static Dialog showAiStrategyDialog(Context context, String message, boolean cancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_alert_nobutton_withtitle, null);

        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);//
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int) (PixelUtil.getScreenWidth(context) * 0.79);
        params.height = (int) (PixelUtil.getScreenHeight(context) * 0.32);
        dialogWindow.setAttributes(params);

        TextView tvmessage = ButterKnife.findById(layout, R.id.tv_message);
        ImageView mClose = ButterKnife.findById(layout, img_close);
        tvmessage.setText(message);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

}
