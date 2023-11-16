package com.yanggc.pojo.base;

import java.io.Serializable;

public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;


    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }


    /**
     * 几种构造方法
     */
    private R() {
    }

    private R(String msg, T data) {
        this.msg = msg;
        if (data != null) {
            this.data = data;
        }
    }

    private R(RCodeEnum rCodeEnum, String msg, T data) {
        this.msg = msg;
        if (data != null) {
            this.data = data;
        }
    }

    /**
     * 失败 自定义
     *
     * @param code
     * @param msg
     * @return
     */
    public static R failure(Integer code, String msg) {
        R result = new R();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

//    /**
//     * 失败 传入枚举
//     *
//     * @param rCodeEnum
//     * @return
//     */
//    public static R failure(RCodeEnum rCodeEnum) {
//        R result = new R();
//        result.setResultCode(rCodeEnum);
//        return result;
//    }

//    public R setResultCode(RCodeEnum code) {
//        this.code = code.code();
//        this.msg = code.msg();
//        return this;
//    }

    //静态方法要使用泛型参数的话，要声明其为泛型方法
    public static <T> R<T> success() {
        return success("操作成功");
    }

    public static <T> R<T> success(String msg) {
        return success(msg, (T) null);
    }

    public static <T> R<T> success(T data) {
        return success("操作成功", data);
    }


    public static <T> R<T> success(String msg, T data) {
        return new R<T>(msg, data);
    }


//    public static <T> R<T> error() {
//        return error("操作失败");
//    }

//    public static <T> R<T> error(String msg) {
//        return error(msg, (T) null);
//    }

//    public static <T> R<T> error(String msg, T data) {
//        return new R<T>(R.Type.ERROR, msg, data);
//    }


}
