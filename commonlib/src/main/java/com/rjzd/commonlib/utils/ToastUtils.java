package com.rjzd.commonlib.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Hition on 2016/12/8.
 */

public class ToastUtils {
    static Toast toast;

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated ...");
    }

    public static void show(Context paramContext, int paramInt) {
        show(paramContext, paramContext.getResources().getText(paramInt), 0);
    }

    public static void show(Context paramContext, int paramInt1, int paramInt2) {
        show(paramContext, paramContext.getResources().getText(paramInt1), paramInt2);
    }

    public static void show(Context paramContext, CharSequence paramCharSequence) {
        show(paramContext, paramCharSequence, 0);
    }

    public static void show(Context paramContext, CharSequence paramCharSequence, int paramInt) {
        if (toast != null) {
            toast.setText(paramCharSequence);
            toast.setDuration(paramInt);
            toast.show();
            return;
        }
        toast = Toast.makeText(paramContext, paramCharSequence, paramInt);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void toastCancle() {
        if (toast != null){
            toast.cancel();
        }
    }
}
