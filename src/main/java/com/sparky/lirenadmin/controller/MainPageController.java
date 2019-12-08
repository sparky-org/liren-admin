package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.annotations.NeedLogin;
import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.controller.response.PointTraceVO;
import com.sparky.lirenadmin.controller.response.TodayBusinessVO;
import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
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

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @NeedLogin
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
            Future<BigDecimal> salePerformance = executor.submit(() -> sumSalePerformanceNum(employee,today));
            Future<Integer> serviceItemRecord = executor.submit(() -> sumServiceItemRecordNum(employee,today));
            Future<Integer> restEmployee = executor.submit(() -> sumRestEmployeeNum(employee,today));
            Future<List<PointRankPO>> rankPOList = executor.submit(() -> findPointRank(employee.getShopNo(), empNo,today));

            TodayBusinessVO businessVO = new TodayBusinessVO();
            Integer appointmentNum =  catchExceptionAndReturn(appointment);
            businessVO.setAppointmentCustomerNum(appointmentNum == null ? 0 : appointmentNum);
            BigDecimal salePerformanceNum =  catchExceptionAndReturn(salePerformance);
            businessVO.setSalesPerformanceNum(salePerformanceNum == null ? BigDecimal.ZERO : salePerformanceNum);
            Integer serviceItemRecordNum =  catchExceptionAndReturn(serviceItemRecord);
            businessVO.setServiceItemRecordNum(serviceItemRecordNum == null ? 0 : serviceItemRecordNum);
            Integer restEmployeeNum =  catchExceptionAndReturn(restEmployee);
            businessVO.setRestEmployeeNum(restEmployeeNum == null ? 0 : restEmployeeNum);

            Integer totalEmploy = shopEmployeeBO.countTotalEmployee(employee.getShopNo());
            businessVO.setYourRank(totalEmploy);
            businessVO.setObtainPoint(0);
            businessVO.setChampionDate(today);
            businessVO.setChampionName(employee.getName());

            //自己排名：自己得分
            //冠军姓名
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
                    if (rankPO.getEmpNo() == empNo.longValue()){
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

    @RequestMapping(value = "/pagingQueryPointTrace",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("积分动态")
    public PagingResponseWrapper<List<PointTraceVO>> pagingQueryPointTrace(@RequestParam @ApiParam Long shopNo,
                                                                           @RequestParam @ApiParam Integer currentPage,
                                                                           @RequestParam @ApiParam Integer pageSize){
        try {
            int total = rewardRecordBO.countRewardRecord(shopNo.toString());
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList(), total);
            }
            int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            List<RewardRecord> records = rewardRecordBO.pagingQueryRewardRecord(shopNo.toString(), start, pageSize);
            List<PointTraceVO> result = new ArrayList<>();
            for (RewardRecord record : records){
                PointTraceVO vo = new PointTraceVO();
                ShopEmployee employee = shopEmployeeBO.getEmployee(record.getEmpNo());
                vo.setEmpName(employee.getName());
                vo.setEmpIcon(employee.getAvatar());
                vo.setPoint(record.getPoint());
                vo.setRewardTime(DateUtils.formatDate(record.getRewardTime(), "yyyy-mm-dd HH:mm:ss"));
                vo.setTitle(RewardTypeEnum.valueOf(record.getOrigin()).getDesc());
                result.add(vo);
            }
            return PagingResponseWrapper.success(result, total);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    private List<PointRankPO> findPointRank(Long shopNo, Long empNo, Date today){
        return rewardRecordBO.findPointRank(shopNo, empNo, today);
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
    private BigDecimal sumSalePerformanceNum(ShopEmployee employee, Date today){
        return salesPerformanceBO.sumSalePerformanceNum(employee, today);
    }
    private Integer sumServiceItemRecordNum(ShopEmployee employee, Date today){
        return serviceItemRecordBO.sumServiceItemRecordNum(employee, today);
    }
    private Integer sumRestEmployeeNum(ShopEmployee employee, Date today){
        return vacationApplyBO.sumRestEmployeeNum(employee, today);
    }
}
