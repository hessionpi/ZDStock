package com.rjzd.aistock.bean;

import java.io.Serializable;

/**
 * Created by Hition on 2017/3/13.
 */

public class DynamicBean implements Serializable {

    private String aiIconUrl;

    private String aiName;

    private String actionName;

    private String actionTime;

    public DynamicBean(String aiIconUrl, String aiName, String actionName, String actionTime) {
        this.aiIconUrl = aiIconUrl;
        this.aiName = aiName;
        this.actionName = actionName;
        this.actionTime = actionTime;
    }

    public String getAiIconUrl() {
        return aiIconUrl;
    }

    public void setAiIconUrl(String aiIconUrl) {
        this.aiIconUrl = aiIconUrl;
    }

    public String getAiName() {
        return aiName;
    }

    public void setAiName(String aiName) {
        this.aiName = aiName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
}
