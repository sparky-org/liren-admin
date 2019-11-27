package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.DailyRecordBO;
import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.constant.AutoRewardConfigEnum;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.CreateDailyRecordDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListDailyRecordVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopJob;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  员工日志
 *
 * @ClassName NoticeController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "员工日志接口")
@Controller
@RequestMapping("/daily/record")
public class DailyRecordController {

    @Autowired
    private DailyRecordBO dailyRecordBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private ShopJobBO shopJobBO;
    @Autowired
    private ShopConfigBO shopConfigBO;

    @ApiOperation("添加日志")
    @RequestMapping(value = "/createRecord",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createRecord(@RequestBody CreateDailyRecordDTO dto){
        try {
            dailyRecordBO.createDailyRecord(buildRecord(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("我发出的日志")
    @RequestMapping(value = "/listMyDailyRecord",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<ListDailyRecordVO>> listMyDailyRecord(@RequestParam @ApiParam Long empNo,
                                                                            @RequestParam @ApiParam Integer currentPage,
                                                                            @RequestParam @ApiParam Integer pageSize){
        try {
            Integer total = dailyRecordBO.countDailyRecord(empNo, null);
            if (total == 0){
                return PagingResponseWrapper.success(new ArrayList<>(), total);
            }
            Integer startIndex = PagingUtils.getStartIndex(total, currentPage, pageSize);
            List<DailyRecord> records = dailyRecordBO.queryDailyRecord(empNo, null, startIndex, pageSize);
            List<ListDailyRecordVO> vos = records.stream().map(this::convertToVO).collect(Collectors.toList());
            return PagingResponseWrapper.success(vos, total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    @ApiOperation("我收到的日志")
    @RequestMapping(value = "/listReportToMeRecord",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<ListDailyRecordVO>> listReportToMeRecord(@RequestParam @ApiParam Long empNo,
                                                                            @RequestParam @ApiParam Integer currentPage,
                                                                            @RequestParam @ApiParam Integer pageSize){
        try {
            Integer total = dailyRecordBO.countDailyRecord(null, empNo);
            if (total == 0){
                return PagingResponseWrapper.success(new ArrayList<>(), total);
            }
            Integer startIndex = PagingUtils.getStartIndex(total, currentPage, pageSize);
            List<DailyRecord> records = dailyRecordBO.queryDailyRecord(null,empNo,  startIndex, pageSize);
            List<ListDailyRecordVO> vos = records.stream().map(this::convertToVO).collect(Collectors.toList());
            return PagingResponseWrapper.success(vos, total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    private DailyRecord buildRecord(CreateDailyRecordDTO dto) {
        DailyRecord record = new DailyRecord();
        record.setCreator(dto.getEmpNo());
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException(String.format("员工[%d]不存在。", dto.getEmpNo()));
        }
        record.setShopNo(employee.getShopNo());
        record.setRecordDate(dto.getDate());
        record.setToday(dto.getTodayConlude());
        record.setTomorrow(dto.getTomorrowPlan());
        record.setManagerNo(dto.getAuditor());
        return record;
    }

    private ListDailyRecordVO convertToVO(DailyRecord record){
        ListDailyRecordVO vo = new ListDailyRecordVO();
        ShopEmployee reporter = shopEmployeeBO.getEmployee(record.getCreator());
        if (reporter == null){
            return null;
        }
        vo.setEmpNo(record.getCreator());
        vo.setEmpName(reporter.getName());
        vo.setIcon(reporter.getAvatar());
        ShopJob shopJob = shopJobBO.getShopJob(reporter.getJobNo());
        if (shopJob != null) {
            vo.setJobName(shopJob.getName());
        }
        ShopEmployee auditor = shopEmployeeBO.getEmployee(record.getManagerNo());
        if (auditor != null){
            vo.setAuditorName(auditor.getName());
        }else{
            vo.setAuditorName(record.getManagerNo().toString());
        }
        vo.setRewardPoint(getRewardPoint(record.getShopNo()));
        vo.setDate(record.getRecordDate());
        vo.setTodayConlude(record.getToday());
        vo.setTomorrowPlan(record.getTomorrow());
        return vo;
    }

    private Integer getRewardPoint(Long shopNo){
        ShopConfig config = shopConfigBO.getShopConfig(shopNo, ShopConfigTypeEnum.REWARD_CONFIG.getCode());
        if (config != null){
            JSONArray array = JSONArray.parseArray(config.getContent());
            Iterator it = array.iterator();
            while (it.hasNext()){
                JSONObject o = (JSONObject) it.next();
                Integer p = o.getInteger(AutoRewardConfigEnum.DAILY_RECORD.getCode());
                return p;
            }
        }
        return 0;
    }

}
