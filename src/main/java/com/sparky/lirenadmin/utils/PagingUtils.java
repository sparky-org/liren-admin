package com.sparky.lirenadmin.utils;

public class PagingUtils {
    public static Integer getStartIndex(Integer total, Integer currentPage, Integer pageSize){
        int totalPage = total / pageSize;
        Integer mod = total % pageSize;
        totalPage += (mod == 0) ? 0 : 1;
        currentPage = Math.min(currentPage, totalPage);
        currentPage = Math.max(currentPage, 1);
        Integer startIndex = (currentPage  - 1) * pageSize;
        return startIndex;
    }
    
}
