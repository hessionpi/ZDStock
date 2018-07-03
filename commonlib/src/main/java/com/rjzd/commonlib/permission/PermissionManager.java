package com.rjzd.commonlib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Created by Hition on 2016/12/23.
 */

public class PermissionManager {
//    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1000;

    private  static PermissionManager ins;

    private PermissionManager(){}

    public static PermissionManager getIns(){
        if(ins == null){
            ins= new PermissionManager();
        }
        return ins;
    }


    /**
     *
     * @param activity
     * @param requestPermission
     * @param requestCode
     * @param runnable  如果已经获得了想要请求的权限，需要执行的方法用过这个接口传过去，不用的时候可以传null
     */
    public void  requestPermissions(Activity activity, String requestPermission , int requestCode, GrantPermissionRunnable runnable){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                requestPermission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,requestPermission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            if(runnable != null){
                runnable.runTask();
            }
        }
    }

    public String getDeviceid(Context c){
        try{
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }


    public interface GrantPermissionRunnable{
        void runTask();
    }


}
