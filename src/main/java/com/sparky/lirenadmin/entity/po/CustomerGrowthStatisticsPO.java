package com.sparky.lirenadmin.entity.po;

/**
 * @ClassName CustomerGrowthStatisticsPO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
public class CustomerGrowthStatisticsPO {

    private Integer today;
    private Integer thisMonth;
    private Integer thisSeason;
    private Integer thisYear;

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(Integer thisMonth) {
        this.thisMonth = thisMonth;
    }

    public Integer getThisSeason() {
        return thisSeason;
    }

    public void setThisSeason(Integer thisSeason) {
        this.thisSeason = thisSeason;
    }

    public Integer getThisYear() {
        return thisYear;
    }

    public void setThisYear(Integer thisYear) {
        this.thisYear = thisYear;
    }
}
