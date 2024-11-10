package com.huang.utils;

public class JsonResult<T> {
    private int code;//状态码  0  1  -1
    private String msg;//返回的提示信息
    private T data;//返回的数据

//    利用构造函数  设置默认 请求成功
//    如果请求不需要返回值,只需要提示成功或者请求失败
    public JsonResult(){
        this.code=0;
        this.msg="请求成功";
    }

//    如果没有返回,需要自定义状态码和提示信息
    public JsonResult(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

//    有返回值和状态码，默认信息是请求成功
    public JsonResult(T data){
        this.code=0;
        this.msg="请求成功";
        this.data=data;
    }

    public static JsonResult success(Object data) {
        return new JsonResult(data);
    }
    public static JsonResult success(Object data, String msg) {
        return new JsonResult(data,msg);
    }

    public static JsonResult error(String msg) {
        JsonResult rs = new JsonResult();
        rs.setCode(-1);
        rs.setMsg(msg);
        return rs;
    }

    public static JsonResult error(Object data,String msg) {
        JsonResult rs = new JsonResult();
        rs.setCode(-1);
        rs.setData(data);
        rs.setMsg(msg);
        return rs;
    }

//    有返回信息，状态码，提示信息自定义
    public JsonResult(T data, String msg){
        this.code=0;
        this.msg=msg;
        this.data=data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public void setData(T data) {
        this.data = data;
    }
}
