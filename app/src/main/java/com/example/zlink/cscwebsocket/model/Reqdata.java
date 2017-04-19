package com.example.zlink.cscwebsocket.model;

/**
 * Created by Zlink on 19/4/2560.
 */



public class Reqdata {
    private String requestId;
    private String cmdCode;
    private String cmdMode;
    private String deviceNo;
    private String lang;
    private Reqobject parameter;
    private String versionNo;
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(String cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getCmdMode() {
        return cmdMode;
    }

    public void setCmdMode(String cmdMode) {
        this.cmdMode = cmdMode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Reqobject getParameter() {
        return parameter;
    }

    public void setParameter(Reqobject parameter) {
        this.parameter = parameter;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}

