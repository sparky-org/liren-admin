package com.sparky.lirenadmin.controller.response;

/**
 * 向前端返回结果包装类
 * @param <T>
 */
public class PagingResponseWrapper<T> extends BaseResponseWrapper{
    private int total;

    public static <T> PagingResponseWrapper<T> success(T result, Integer total){
        PagingResponseWrapper wrapper = new PagingResponseWrapper();
        wrapper.setSuccess(true);
        wrapper.setTotal(total);
        wrapper.setResult(result);
        return wrapper;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
