package com.rjzd.aistock.bean;

import com.rjzd.aistock.api.AIInfo;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Hition on 2017/3/1.
 */

public class LoginBean implements Serializable{


    /**
     * 登录平台
     */
//    public String plaform;
    /**
     * 用户id
     */
    private int userid;
    /**
     *电话号码
     */
    private String cellphone;
    /**
     *昵称
     */
    private String nickname;
    /**
     *性别
     */
    private String gender;
    /**
     * 头像
     */
    private String iconurl;
    /**
     *位置
     */
    private String location;

    private boolean isBindWX;

    private boolean isBindQQ;

    private boolean isBindSina;

    private List<AIInfo> aiList;

    /**
     * 用户等级
     */
    private int level;

    /**
     * 用户积分
     */
    private int points;

    /**
     * 邀请码
     */
    private String inviteCode;


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGender() {
        return gender;
    }

    public String getIconurl() {
        return iconurl;
    }

    public String getLocation() {
        return location;
    }

    public boolean isBindWX() {
        return isBindWX;
    }

    public void setBindWX(boolean bindWX) {
        isBindWX = bindWX;
    }

    public boolean isBindQQ() {
        return isBindQQ;
    }

    public void setBindQQ(boolean bindQQ) {
        isBindQQ = bindQQ;
    }

    public boolean isBindSina() {
        return isBindSina;
    }

    public void setBindSina(boolean bindSina) {
        isBindSina = bindSina;
    }

    public List<AIInfo> getAiList() {
        return aiList;
    }

    public void setAiList(List<AIInfo> aiList) {
        this.aiList = aiList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
