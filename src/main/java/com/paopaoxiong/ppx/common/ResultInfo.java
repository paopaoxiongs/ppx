package com.paopaoxiong.ppx.common;

/**
 * REST请求响应信息类
 */
public class ResultInfo {

    private boolean success;    //是否成功
    private Object data;        //数据
    private long code;        //代码
    private String message;        //信息

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
