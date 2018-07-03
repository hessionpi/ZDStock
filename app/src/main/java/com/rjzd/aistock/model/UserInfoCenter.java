package com.rjzd.aistock.model;

import android.text.TextUtils;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.SerializeTools;


/**
 * 用于存放个人信息
 *
 * @author phs at 2017-03-01
 */
public final class UserInfoCenter {

    private static UserInfoCenter instance;
    private LoginBean loginBean;

    private UserInfoCenter(){
        loadLoginModel();
    }

    public static UserInfoCenter getInstance() {
        if(null == instance){
            synchronized (UserInfoCenter.class){
                if (null == instance){
                    instance = new UserInfoCenter();
                }
            }
        }
        return instance;
    }


    /**
     * 快捷方法获取用户id
     * @return userid
     */
    public int getUserId() {
        int id = 0;
        if(loginBean!=null) {
            id = loginBean.getUserid();
        }
        return id;
    }

    /**
     * 获取登陆成功后的用户信息,用于判断登录状态
     *
     * @return null 未登录, else 已登录
     */
    public LoginBean getLoginModel() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginModel) {
        this.loginBean = loginModel;
        if(loginModel!=null) {
            saveLoginModel(loginModel);
        }
    }

    /**
     * 登录成功后调用，将用户信息序列化到本地
     *
     * @param model 登录模型
     */
    private void saveLoginModel(LoginBean model){
        String fileName = getFileDir();
        if(!TextUtils.isEmpty(fileName)) {
            SerializeTools.serialization(fileName, model);
        }else{
            LogUtil.d("userinfo", "登录初始化数据失败");
        }
    }

    /**
     * 加载 文件下的LoginModel
     */
    private void loadLoginModel() {
        String fileName = getFileDir();
        if(!TextUtils.isEmpty(fileName)) {
            try {
                Object obj = SerializeTools.deserialization(fileName);
                if (obj instanceof LoginBean) {
                    loginBean = (LoginBean) obj;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 退出登录时清空用户信息
     */
    public void clearUserInfo(){
        String fileName = getFileDir();
        SerializeTools.deletePath(fileName);
    }

    /**
     * 恢复重置
     */
    public void reset() {
//        PushManager.getInstance().unBindAlias(XywyApp.getAppContext(), getUserId(),true);
        loginBean = null;
    }

    private String getFileDir() {
        return ZdStockApp.getAppContext().getFilesDir().toString()+ Constants.LoginModelFileName;
    }

}
