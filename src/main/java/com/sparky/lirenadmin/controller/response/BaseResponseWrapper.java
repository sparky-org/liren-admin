package com.sparky.lirenadmin.controller.response;

/**
 * 向前端返回结果包装类
 * @param <T>
 */
public class BaseResponseWrapper<T> {
    private boolean isSuccess;
    private String errMsg;
    private T result;

    public static <T> BaseResponseWrapper<T> success(T result){
        BaseResponseWrapper wrapper = new BaseResponseWrapper();
        wrapper.setSuccess(true);
        wrapper.setResult(result);
        return wrapper;
    }

    public static <T> BaseResponseWrapper<T> fail(T result, String errMsg){
        BaseResponseWrapper wrapper = new BaseResponseWrapper();
        wrapper.setSuccess(false);
        wrapper.setErrMsg(errMsg);
        wrapper.setResult(result);
        return wrapper;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T
    getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
