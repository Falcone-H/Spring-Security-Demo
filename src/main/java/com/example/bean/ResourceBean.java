package com.example.bean;

import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 18:34
 */

public class ResourceBean {
    private Integer status;
    private String msg;
    private Object obj;

    public ResourceBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
