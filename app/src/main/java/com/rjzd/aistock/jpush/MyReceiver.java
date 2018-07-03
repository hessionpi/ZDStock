package com.rjzd.aistock.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.Count;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.NotificationPresenter;
import com.rjzd.aistock.ui.activity.MainActivity;
import com.rjzd.aistock.ui.activity.SplashActivity;
import com.rjzd.aistock.view.IFillDataView;
import com.rjzd.commonlib.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver implements IFillDataView {

	private static final String TAG = "JPush";
	private Context mContext;
	private int unRegisterUnread = 0;
	private NotificationPresenter notificationPresenter;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.mContext = context;
		String action = intent.getAction();
        Bundle bundle = intent.getExtras();

		if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			// 通知栏点击推送消息事件
			Intent mainIntent = new Intent(context, MainActivity.class);
			mainIntent.putExtras(bundle);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(mainIntent);
		}else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)){
			LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的通知");

			String jsonExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			JSONObject extraJson = null;
			try {
				extraJson = new JSONObject(jsonExtra);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if(null != extraJson){
				String actionType = extraJson.optString("type");
				int userId = UserInfoCenter.getInstance().getUserId();
				switch(actionType){
					case Constants.ACTION_AI_WEEKLY:
						sendBroadcast("com.rjzd.aistock.aiweekly",1);
						break;

					case Constants.ACTION_DAILYBESTPLATE:
						sendBroadcast("com.rjzd.aistock.recommendplate",1);
						break;

					case Constants.ACTION_DAILYBESTSTOCK:
						sendBroadcast("com.rjzd.aistock.recommendstock",1);
						break;

					case Constants.ACTION_ANNOUNCEMENT:
						if(userId>0){
							// 获取未读消息条数
							notificationPresenter = new NotificationPresenter(this);
							notificationPresenter.getTotalUnread(userId);
						}else{
							unRegisterUnread ++;
							sendBroadcast("com.rjzd.aistock.announcement",unRegisterUnread);
						}
						break;
				}
			}
		}
	}

	@Override
	public void fillData(Object data, int dsTag) {
		switch (dsTag){
			case Constants.DS_TAG_NOTIFICATION_UNREAD:
				// 获取未读消息总条数
				Count totalUnread = (Count) data;
				if(StatusCode.OK.getValue() == totalUnread.get_status().getValue()){
					int total = totalUnread.get_data();
					sendBroadcast("com.rjzd.aistock.announcement",total);
					notificationPresenter.onUnsubscribe();
				}
				break;
		}
	}

	@Override
	public void showFailedView() {
		if(null!= notificationPresenter){
			notificationPresenter.onUnsubscribe();
		}
	}

	@Override
	public void showToast(String msg) {

	}

	private void sendBroadcast(String action,int unread){
		Intent intent = new Intent(action);
		intent.putExtra("msg_unread",unread);
		mContext.sendBroadcast(intent);
	}
}
