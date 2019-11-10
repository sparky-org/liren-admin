package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.TodayBusinessVO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

@Api(tags = "主页接口")
@Controller
@RequestMapping("/main")
public class MainPageController {

    @Autowired
    private AppointmentBO appointmentBO;
    @Autowired
    private SalesPerformanceBO salesPerformanceBO;
    @Autowired
    private ServiceItemRecordBO serviceItemRecordBO;
    @Autowired
    private VacationApplyBO vacationApplyBO;
    @Autowired
    private RewardRecordBO rewardRecordBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    //TODO 需要测试下多线程情况下是共用5线程还是独占5线程
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @RequestMapping(value = "/findTodayBusiness", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("今日动态和排名")
    public BaseResponseWrapper<TodayBusinessVO> findTodayBusiness(@RequestParam @ApiParam Long empNo){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (null == employee){
                throw new RuntimeException(String.format("员工[%d]不存在。", empNo));
            }
            Date today = new Date();
            //获取预约客户数
            Future<Integer> appointment = executor.submit(() -> sumAppointmentCustomer(employee,today));
            Future<Integer> salePerformance = executor.submit(() -> sumSalePerformanceNum(employee,today));
            Future<Integer> serviceItemRecord = executor.submit(() -> sumServiceItemRecordNum(employee,today));
            Future<Integer> restEmployee = executor.submit(() -> sumRestEmployeeNum(employee,today));
            Future<List<PointRankPO>> rankPOList = executor.submit(() -> findPointRank(empNo,today));

            TodayBusinessVO businessVO = new TodayBusinessVO();
            Integer appointmentNum =  catchExceptionAndReturn(appointment);
            businessVO.setAppointmentCustomerNum(appointmentNum == null ? 0 : appointmentNum);
            Integer salePerformanceNum =  catchExceptionAndReturn(salePerformance);
            businessVO.setSalesPerformanceNum(salePerformanceNum == null ? 0 : salePerformanceNum);
            Integer serviceItemRecordNum =  catchExceptionAndReturn(serviceItemRecord);
            businessVO.setServiceItemRecordNum(serviceItemRecordNum == null ? 0 : serviceItemRecordNum);
            Integer restEmployeeNum =  catchExceptionAndReturn(restEmployee);
            businessVO.setRestEmployeeNum(restEmployeeNum == null ? 0 : restEmployeeNum);

            businessVO.setYourRank(1);
            businessVO.setObtainPoint(0);
            businessVO.setChampionDate(today);
            businessVO.setChampionName(employee.getName());

            List<PointRankPO> rankPOS = catchExceptionAndReturn(rankPOList);
            if (!CollectionUtils.isEmpty(rankPOS)){
                Iterator<PointRankPO> iterator = rankPOS.iterator();
                while (iterator.hasNext()){
                    PointRankPO rankPO = iterator.next();
                    if (rankPO.getRank() == 1){
                        //第一名
                        businessVO.setChampionDate(today);
                        ShopEmployee champion = shopEmployeeBO.getEmployee(rankPO.getEmpNo());
                        businessVO.setChampionName(champion.getName());
                    }
                    if (rankPO.getEmpNo() == empNo){
                        //本人
                        businessVO.setYourRank(rankPO.getRank());
                        businessVO.setObtainPoint(rankPO.getPoint());
                    }
                }
            }
            return BaseResponseWrapper.success(businessVO);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private List<PointRankPO> findPointRank(Long empNo, Date today){
        return rewardRecordBO.findPointRank(empNo, today);
    }

    private <T> T catchExceptionAndReturn(Future<T> future){
        try {
            return future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer sumAppointmentCustomer(ShopEmployee employee, Date today){
        return appointmentBO.countAppointCustomer(employee, today);
    }
    private Integer sumSalePerformanceNum(ShopEmployee employee, Date today){
        return salesPerformanceBO.sumSalePerformanceNum(employee, today);
    }
    private Integer sumServiceItemRecordNum(ShopEmployee employee, Date today){
        return serviceItemRecordBO.sumServiceItemRecordNum(employee, today);
    }
    private Integer sumRestEmployeeNum(ShopEmployee employee, Date today){
        return vacationApplyBO.sumRestEmployeeNum(employee, today);
    }
}
