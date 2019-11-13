package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.QueryApplyCond;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.controller.request.NewNormalApplyDTO;
import com.sparky.lirenadmin.controller.request.NewVacationApplyDTO;
import com.sparky.lirenadmin.controller.response.*;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private static final Logger logger = LoggerFactory.getLogger(MyApplyController.class);

    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private ApplyDtlBO applyDtlBO;
    @Autowired
    private VacationApplyBO vacationApplyBO;
    @Autowired
    private ServiceItemRecordBO serviceItemBO;
    @Autowired
    private SalesPerformanceBO salesPerformanceBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private ShopJobBO shopJobBO;

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
        try {
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
        } catch (Exception e) {
            logger.error("申请列表查询异常", e.getMessage());
            return (PagingResponseWrapper)PagingResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("请假申请详情")
    @RequestMapping(value = "/vacationApplyDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<VacationApplyDetailVO> vacationApplyDetail(@RequestParam @ApiParam Long vacationApplyNo){
        try {
            Apply apply = applyBO.getApply(vacationApplyNo);
            if(apply == null){
                return BaseResponseWrapper.success(new VacationApplyDetailVO());
            }
            if (!ApplyTypeEnum.VACATION.getCode().equals(apply.getOrigin())){
                return BaseResponseWrapper.success(new VacationApplyDetailVO());
            }
            VacationApply vacationApply = vacationApplyBO.getVacation(apply.getOriginNo());
            if(null == vacationApply){
                return BaseResponseWrapper.success(new VacationApplyDetailVO());
            }
            VacationApplyDetailVO detailVO = buildVacationDetail(apply, vacationApply);
            return BaseResponseWrapper.success(detailVO);
        } catch (Exception e) {
            logger.error("请假详情查询异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("普通申请详情")
    @RequestMapping(value = "/normalApplyDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<NormalApplyDetailVO> normalApplyDetail(@RequestParam @ApiParam Long applyNo){
        try {
            Apply apply = applyBO.getApply(applyNo);
            if(apply == null){
                return BaseResponseWrapper.success(new NormalApplyDetailVO());
            }
            if (ApplyTypeEnum.VACATION.getCode().equals(apply.getOrigin())){
                return BaseResponseWrapper.success(new NormalApplyDetailVO());
            }
            NormalApplyDetailVO detailVO = buildNormalApplyDetailVO(apply);
            return BaseResponseWrapper.success(detailVO);
        } catch (Exception e) {
            logger.error("普通申请详情查询异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("撤回申请")
    @RequestMapping(value = "/revertApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper revertApply(@RequestParam @ApiParam Long applyNo,
                                           @RequestParam @ApiParam Long empNo){
        try {
            Apply apply = applyBO.getApply(applyNo);
            if(apply == null){
                throw new RuntimeException(String.format("申请[%d]不存在", applyNo));
            }
            if (apply.getApplyEmpNo() != empNo){
                throw new RuntimeException(String.format("操作者[%d]只能撤回自己的申请", applyNo));
            }
            if (canRevert(apply.getCreator(), apply.getAuditStatus())){
                throw new RuntimeException(String.format("当前申请[%d]状态是[%s]不能被撤回", applyNo, ApplyStatusEnum.valueOf(apply.getAuditStatus()).getDesc()));
            }
            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            logger.error("撤回申请异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("新建请假申请")
    @RequestMapping(value = "/newVacationApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper newVacationApply(@RequestBody NewVacationApplyDTO newVacationApplyDTO){
        try {
//            Apply apply = applyBO.getApply(applyNo);
//            if(apply == null){
//                throw new RuntimeException(String.format("申请[%d]不存在", applyNo));
//            }
//            if (apply.getApplyEmpNo() != empNo){
//                throw new RuntimeException(String.format("操作者[%d]只能撤回自己的申请", applyNo));
//            }
//            if (canRevert(apply.getCreator(), apply.getAuditStatus())){
//                throw new RuntimeException(String.format("当前申请[%d]状态是[%s]不能被撤回", applyNo, ApplyStatusEnum.valueOf(apply.getAuditStatus()).getDesc()));
//            }
//            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            logger.error("新建申请异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("新建普通申请")
    @RequestMapping(value = "/newNormalApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper newNormalApply(@RequestBody NewNormalApplyDTO newNormalApplyDTO){
        try {
//            Apply apply = applyBO.getApply(applyNo);
//            if(apply == null){
//                throw new RuntimeException(String.format("申请[%d]不存在", applyNo));
//            }
//            if (apply.getApplyEmpNo() != empNo){
//                throw new RuntimeException(String.format("操作者[%d]只能撤回自己的申请", applyNo));
//            }
//            if (canRevert(apply.getCreator(), apply.getAuditStatus())){
//                throw new RuntimeException(String.format("当前申请[%d]状态是[%s]不能被撤回", applyNo, ApplyStatusEnum.valueOf(apply.getAuditStatus()).getDesc()));
//            }
//            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            logger.error("新建申请异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("待我审批")
    @RequestMapping(value = "/myApprovalPending", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper myApprovalPending(@RequestParam @ApiParam Long auditEmpNo){
        try {
//            Apply apply = applyBO.getApply(applyNo);
//            if(apply == null){
//                throw new RuntimeException(String.format("申请[%d]不存在", applyNo));
//            }
//            if (apply.getApplyEmpNo() != empNo){
//                throw new RuntimeException(String.format("操作者[%d]只能撤回自己的申请", applyNo));
//            }
//            if (canRevert(apply.getCreator(), apply.getAuditStatus())){
//                throw new RuntimeException(String.format("当前申请[%d]状态是[%s]不能被撤回", applyNo, ApplyStatusEnum.valueOf(apply.getAuditStatus()).getDesc()));
//            }
//            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            logger.error("查询异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("审批")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper approve(@RequestParam @ApiParam Long applyNo,
                                       @RequestParam @ApiParam Long auditEmpNo){
        try {
//            Apply apply = applyBO.getApply(applyNo);
//            if(apply == null){
//                throw new RuntimeException(String.format("申请[%d]不存在", applyNo));
//            }
//            if (apply.getApplyEmpNo() != empNo){
//                throw new RuntimeException(String.format("操作者[%d]只能撤回自己的申请", applyNo));
//            }
//            if (canRevert(apply.getCreator(), apply.getAuditStatus())){
//                throw new RuntimeException(String.format("当前申请[%d]状态是[%s]不能被撤回", applyNo, ApplyStatusEnum.valueOf(apply.getAuditStatus()).getDesc()));
//            }
//            applyBO.revert(apply);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            logger.error("审批异常", e.getMessage());
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private NormalApplyDetailVO buildNormalApplyDetailVO(Apply apply) {
        NormalApplyDetailVO vo = new NormalApplyDetailVO();
        ShopEmployee auditEmp = shopEmployeeBO.getEmployee(apply.getAuditEmpNo());
        if (auditEmp != null){
            vo.setAuditEmp(auditEmp.getName());
        }
        vo.setContent(apply.getApplyContent());
        vo.setCcList(JSONArray.toJSONString(getCcEmpNames(apply.getId())));
        vo.setPicList(apply.getPicList());
        vo.setStatus(apply.getAuditStatus());
        if (apply.getAuditTime() != null) {
            vo.setPassedTime(org.apache.hc.client5.http.utils.DateUtils.formatDate(apply.getAuditTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        vo.setCanRevert(canRevert(apply.getCreator(), apply.getAuditStatus()));
        return vo;
    }

    private VacationApplyDetailVO buildVacationDetail(Apply apply, VacationApply vacationApply) {
        VacationApplyDetailVO vo = new VacationApplyDetailVO();
        vo.setStatus(apply.getAuditStatus());
        ShopEmployee auditEmp = shopEmployeeBO.getEmployee(apply.getAuditEmpNo());
        vo.setAuditEmp(auditEmp.getName());

        vo.setBeinDate(vacationApply.getBeginDate());
        vo.setEndDate(vacationApply.getEndDate());
        vo.setDays(DateUtils.differentDays(vacationApply.getBeginDate(), vacationApply.getEndDate()));
        vo.setReason(vacationApply.getReason());

        ShopEmployee applyEmp = shopEmployeeBO.getEmployee(apply.getApplyEmpNo());
        ShopJob shopJob = shopJobBO.getShopJob(applyEmp.getJobNo());
        vo.setApplyEmpJob(shopJob.getName());
        vo.setApplyEmpName(applyEmp.getName());
        vo.setCcList(JSONArray.toJSONString(getCcEmpNames(apply.getId())));
        vo.setCanRevert(canRevert(vacationApply.getCreator(), apply.getAuditStatus()));
        return vo;
    }

    private List<String> getCcEmpNames(Long applyNo){
        List<ApplyDtl> dtls =applyDtlBO.listApplyDtl(applyNo);
        if (!CollectionUtils.isEmpty(dtls)){
            List<Long> ccEmpNoList = dtls.stream().map(ApplyDtl::getCcNo).collect(Collectors.toList());
            List<ShopEmployee> ccEmpList = shopEmployeeBO.listEmploy(ccEmpNoList);
            if (!CollectionUtils.isEmpty(ccEmpList)){
                List<String> ccEmpNameList = ccEmpList.stream().map(ShopEmployee::getName).collect(Collectors.toList());
                return ccEmpNameList;
            }
        }
        return null;
    }

    private Boolean canRevert(Long creator, String status){
        //1. 自提交，且状态待审批 APPROVAL_PENDING 才会允许撤回
        return 0 != creator && ApplyStatusEnum.NEW.getCode().equals(status);
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
        ServiceItemRecord serviceItem = serviceItemBO.getServiceItemRecord(apply.getOriginNo());
        if (serviceItem != null){
            item.setItemName(serviceItem.getTitle());
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
            perf.setName(salesPerformance.getTitle());
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
