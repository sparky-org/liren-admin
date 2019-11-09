package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.mapper.SalesPerformanceMapper;
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
    private SalesPerformanceMapper salesPerformanceMapper;

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

    private CustomerTrace buildTrace(SalesPerformance record) {
        CustomerInfo customer = customerBO.getCustomerByPhone(record.getCustomerPhone());
        return customerTraceBO.buildCustomerTrace(customer.getId(), record.getCompleteTime(),
                "SALES_PERF", record.getId(), record.getShopNo(), record.getEmpNo());
    }


    private IncreasePointDO buildIncreasePointDO(SalesPerformance sales) {
        return pointBO.buildIncreasePointDO("SALES_PERF", sales.getId(),
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
