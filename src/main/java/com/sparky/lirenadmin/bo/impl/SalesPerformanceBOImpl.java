package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.mapper.ext.SalesPerformanceMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SalesPerformanceBOImpl implements SalesPerformanceBO {

    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private PointBO pointBO;
    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private CustomerTraceBO customerTraceBO;

    @Autowired
    private SalesPerformanceMapperExt salesPerformanceMapper;

    @Override
    public void createSalePerformance(SalesPerformance salesPerformance) {
        doCreateSales(salesPerformance);
        applyBO.createApply(buildApply(salesPerformance));
        customerTraceBO.createCustomerTrace(buildTrace(salesPerformance));
    }

    @Transactional
    @Override
    public void reward(Long salesNo) {
        SalesPerformance sales = getSalePerformance(salesNo);
        if (null == sales){
            throw new RuntimeException("增加业绩完成积分失败，业绩不存在");
        }
        pointBO.increasePoint(buildIncreasePointDO(sales), this::doReward);
    }

    @Override
    public Integer sumSalePerformanceNum(ShopEmployee employee, Date today) {
        int total;
        if (employee.getIsAdmin()){
            total = salesPerformanceMapper.sumSalePerformanceNumByShop(employee.getShopNo(), today);
        }else{
            total = salesPerformanceMapper.sumSalePerformanceNumByEmp(employee.getShopNo(), today);
        }
        return total;
    }

    @Override
    public SalesPerformance getSalesPerf(Long id) {
        return salesPerformanceMapper.selectByPrimaryKey(id);
    }

    private CustomerTrace buildTrace(SalesPerformance record) {
        CustomerInfo customer = customerBO.getCustomerByPhone(record.getCustomerPhone());
        return customerTraceBO.buildCustomerTrace(customer.getId(), record.getCompleteTime(),
                RewardTypeEnum.SAL_PERF.getCode(), record.getId(), record.getShopNo(), record.getEmpNo());
    }


    private IncreasePointDO buildIncreasePointDO(SalesPerformance sales) {
        return pointBO.buildIncreasePointDO(RewardTypeEnum.SAL_PERF.getCode(), sales.getId(),
                sales.getEmpNo(), sales.getCreator(), sales.getRewardPoint(), sales.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        SalesPerformance salesPerformance = new SalesPerformance();
        salesPerformance.setId(salesId);
        salesPerformance.setGmtModify(new Date());
        salesPerformance.setIsRewarded(true);
        salesPerformance.setRewardTime(new Date());
        salesPerformanceMapper.updateByPrimaryKeySelective(salesPerformance);
        return true;
    }

    private SalesPerformance getSalePerformance(Long salesNo) {
        return salesPerformanceMapper.selectByPrimaryKey(salesNo);
    }

    private Apply buildApply(SalesPerformance salesPerformance) {
        return applyBO.buildApply("SALES_PERF", salesPerformance.getId(),
                buildApplyContent(salesPerformance), salesPerformance.getEmpNo(),
                salesPerformance.getCreator(), salesPerformance.getShopNo());
    }

    private String buildApplyContent(SalesPerformance salesPerformance) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("业绩金额：%s \n客户手机号：%s \n申请奖励：%d 积分", salesPerformance.getAmount().toString(), salesPerformance.getCustomerPhone(), salesPerformance.getRewardPoint()));
        return builder.toString();
    }

    private void doCreateSales(SalesPerformance salesPerformance) {
        salesPerformance.setIsRewarded(false);
        salesPerformance.setIsValid(true);
        salesPerformance.setGmtModify(new Date());
        salesPerformance.setGmtCreate(new Date());
        salesPerformanceMapper.insertSelective(salesPerformance);
    }
}
