package com.rjzd.aistock.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.rjzd.aistock.R;
import com.rjzd.aistock.utils.StatisticalTools;
import com.rjzd.commonlib.utils.ToastUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 * Created by Hition on 2016/12/21.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private static Stack<Activity> activityStack = new Stack<>();
    //loading用
    public Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStack.add(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatisticalTools.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatisticalTools.onPause(this);
    }

    /**
     * 显示loading对话框
     */
    private void showDialog() {
        loadingDialog = new Dialog(this, R.style.loading_dialog);
        LayoutInflater inflater2 = LayoutInflater.from(this);
        View v = inflater2.inflate(R.layout.dialog_common_layout, null);// 得到加载view
        loadingDialog.setCancelable(false);//true 点击空白处或返回键消失   false 不消失
        loadingDialog.setContentView(v);// 设置布局
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    loadingDialog.dismiss();
                }
                return false;
            }
        });
        loadingDialog.show();
    }

    /**
     * 显示dialog加载进度
     */
    public void showLoadingView() {
        showDialog();
    }

    public void dismissLoading(){
        if(null != loadingDialog && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    public void showToast(int msgResId) {
        showToast(getString(msgResId));
    }

    public void showToast(String msg) {
        ToastUtils.show(this,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
        finishActivity(this);
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 清除掉中间的Activity 到MainActivity
     */
    public void finishToMainActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity act = activityStack.get(i);
                if(!(act instanceof MainActivity)) {
                    activityStack.get(i).finish();
                }
            }
        }
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor(int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = new View(window.getContext());
            int statusBarHeight = getStatusBarHeight(window.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            params.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(params);
            statusBarView.setBackgroundColor(ContextCompat.getColor(this,colorResId));
            decorViewGroup.addView(statusBarView);
        }
    }

    /**
     * 获取状态栏高度
     * @param context  上下文
     * @return height
     */
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 需要MIUIV6以上
     * @param activity Activity
     * @param darkmode 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    protected static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    protected static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

}
