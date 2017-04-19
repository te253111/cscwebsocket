package com.example.zlink.cscwebsocket.model;

/**
 * Created by Zlink on 19/4/2560.
 */

public class objectAdapter {
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String className;
    private Object object;
    private String type;
}
