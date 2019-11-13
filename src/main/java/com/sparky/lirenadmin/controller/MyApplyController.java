package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.SalesPerformanceBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.VacationApplyBO;
import com.sparky.lirenadmin.bo.cond.QueryApplyCond;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.response.ListApplyVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.VacationApply;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyApplyController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/12
 * @Version v0.0.1
 */
@Api(tags = "我的申请")
@Controller
@RequestMapping("/myApply")
public class MyApplyController {

    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private VacationApplyBO vacationApplyBO;
    @Autowired
    private ServiceItemBO serviceItemBO;
    @Autowired
    private SalesPerformanceBO salesPerformanceBO;

    @ApiOperation("我的申请列表")
    @RequestMapping(value = "/listApply", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<ListApplyVO>> listApply(@RequestParam @ApiParam Long empNo,
                                                              @RequestParam(required = false) @ApiParam String applyType,
                                                              @RequestParam(required = false) @ApiParam String status,
                                                              @RequestParam(required = false) @ApiParam Date begin,
                                                              @RequestParam(required = false) @ApiParam Date end,
                                                              @RequestParam @ApiParam Integer currentPage,
                                                              @RequestParam @ApiParam Integer pageSize){
        QueryApplyCond cond = buildQueryApplyCond(empNo, applyType, status, begin, end);
        Integer total = applyBO.countApply(cond);
        if (total < 1){
            return PagingResponseWrapper.success(new ArrayList<>(), 0);
        }
        int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
        cond.setStart(start);
        cond.setLength(pageSize);
        List<Apply> applies = applyBO.pagingQueryApply(cond);
        List<ListApplyVO> applyVOS = applies.stream().map(this::convertToApplyVO).collect(Collectors.toList());
        return PagingResponseWrapper.success(applyVOS, total);
    }

    private ListApplyVO convertToApplyVO(Apply apply) {
        ListApplyVO applyVO = new ListApplyVO();
        applyVO.setApplyType(apply.getOrigin());
        applyVO.setApplyTypeDesc(ApplyTypeEnum.valueOf(apply.getOrigin()).getDesc());
        applyVO.setStatus(apply.getAuditStatus());
        applyVO.setNormal(buildNormalApply(apply));
        applyVO.setSalesPerformance(buildSalesPerformanceApply(apply));
        applyVO.setServiceItem(buildServiceApply(apply));
        applyVO.setVacation(buildVacationApply(apply));
        return applyVO;
    }

    private ListApplyVO.Vacation buildVacationApply(Apply apply) {
        if (!ApplyTypeEnum.VACATION.getCode().equals(apply.getOrigin())){
            return null;
        }
        ListApplyVO.Vacation v = new ListApplyVO.Vacation();
        VacationApply vacationApply = vacationApplyBO.getVacation(apply.getOriginNo());
        if(vacationApply != null){
            v.setBegin(vacationApply.getBeginDate());
            v.setEnd(vacationApply.getEndDate());
            v.setDays(DateUtils.differentDays(vacationApply.getBeginDate(), vacationApply.getEndDate()));
        }
        return v;
    }

    private ListApplyVO.ServiceItem buildServiceApply(Apply apply) {
        if (!ApplyTypeEnum.SERVICE_ITEM.getCode().equals(apply.getOrigin())){
            return null;
        }
        ListApplyVO.ServiceItem item = new ListApplyVO.ServiceItem();
        ServiceItem serviceItem = serviceItemBO.getServiceItem(apply.getOriginNo());
        if (serviceItem != null){
            item.setItemName(serviceItem.getItemName());
            item.setItemDate(serviceItem.getGmtCreate());
            item.setApplyPoint(serviceItem.getRewardPoint());
        }
        return item;
    }

    private ListApplyVO.SalesPerformance buildSalesPerformanceApply(Apply apply) {
        if (!ApplyTypeEnum.SAL_PERF.getCode().equals(apply.getOrigin())){
            return null;
        }
        ListApplyVO.SalesPerformance perf = new ListApplyVO.SalesPerformance();
        SalesPerformance salesPerformance = salesPerformanceBO.getSalesPerf(apply.getOriginNo());
        if (salesPerformance != null){
            perf.setName("做出了 "+ salesPerformance.getAmount()+" 元业绩");
            perf.setDate(salesPerformance.getCompleteTime());
            perf.setApplyPoint(salesPerformance.getRewardPoint());
        }
        return perf;
    }

    private ListApplyVO.Normal buildNormalApply(Apply apply) {
        if (ApplyTypeEnum.VACATION.getCode().equals(apply.getOrigin()) ||
                ApplyTypeEnum.SERVICE_ITEM.getCode().equals(apply.getOrigin()) ||
                ApplyTypeEnum.SAL_PERF.getCode().equals(apply.getOrigin())){
            return null;
        }
        ListApplyVO.Normal normal = new ListApplyVO.Normal();
        normal.setContent(apply.getApplyContent());
        normal.setDate(apply.getGmtCreate());
//        normal.setTitle(apply.app);
        return normal;
    }

    private QueryApplyCond buildQueryApplyCond(Long empNo, String applyType, String status, Date begin, Date end) {
        if (null == ApplyTypeEnum.valueOf(applyType)){
            throw new RuntimeException(String.format("申请类型不存在，可用的申请类型有[%s]", ApplyTypeEnum.listCodes()));
        }
        QueryApplyCond cond = new QueryApplyCond();
        cond.setEmpNo(empNo);
        cond.setApplyType(applyType);
        cond.setStatus(status);
        cond.setBegin(begin);
        cond.setEnd(end);
        return cond;
    }
}
