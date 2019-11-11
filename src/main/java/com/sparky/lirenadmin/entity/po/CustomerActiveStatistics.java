package com.sparky.lirenadmin.entity.po;

/**
 * @ClassName CustomerActiveStatistics
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
public class CustomerActiveStatistics {

    private String yearMonth;

    private Integer count;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
