package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.controller.request.ModifyTaskDTO;
import com.sparky.lirenadmin.controller.request.PublishTaskDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName MyTaskController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/11
 * @Version v0.0.1
 */
@Api(tags = "我的任务接口")
@Controller
@RequestMapping("/myTask")
public class MyTaskController {

    private static final Logger logger = LoggerFactory.getLogger(MyTaskController.class);

    @Autowired
    private TaskBO taskBO;
    @Autowired
    private PointBO pointBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private EmployeeBO employeeBO;
    @Autowired
    private TaskRecordBO taskRecordBO;
    @Autowired
    private PointConfigBO pointConfigBO;

    @ApiOperation("我的任务列表")
    @RequestMapping(value = "/queryMyTask",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<MyTaskVO>> queryMyTask(@RequestParam(required = false) @ApiParam("品德积分-CHARACTER/行为积分-ACTION") String pointType,
                                                             @RequestParam(required = false) @ApiParam("任务状态：未完成-UNCOMPLETE/已完成-COMPLETE") String taskStatus,
                                                             @RequestParam @ApiParam Long empNo,
                                                             @RequestParam @ApiParam Integer currentPage,
                                                             @RequestParam @ApiParam Integer pageSize){
        try {
            QueryTaskCond cond = new QueryTaskCond();
            cond.setEmpNo(empNo);
            cond.setPointType(pointType);
            int total = taskBO.countTask(cond);
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(), total);
            }
            int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            cond.setStart(start);
            cond.setLength(pageSize);
            List<MyTaskPO> tasks = taskBO.queryTask(cond);
            if (taskStatus != null){
                tasks = tasks.stream().filter(p -> p.getStatus().equals(taskStatus)).collect(Collectors.toList());
            }
            List<MyTaskVO> myTaskVOS = tasks.stream().map(this::convertToMyTaskVO).collect(Collectors.toList());
            return PagingResponseWrapper.success(myTaskVOS, total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    @RequestMapping(value = "/completeTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper completeTask(@RequestBody Long taskNo,
                                            @RequestBody Long empNo){
        try {
            //TODO 1. 验证此人可以完成该任务
            Task task = taskBO.getTask(taskNo);
            if (null == task){
                throw new RuntimeException("任务不存在");
            }
            //2. 完成逻辑
            taskRecordBO.createTaskRecord(buildTaskRecord(task, empNo));
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    private TaskRecord buildTaskRecord(Task task, Long empNo) {
        TaskRecord record = new TaskRecord();
        record.setIsRewarded(false);
        record.setShopNo(task.getShopNo());
        PointConfig pointConfig = pointConfigBO.getPointConfigByPrimaryKey(task.getPointNo());
        record.setRewardPoint(0);
        if (null != pointConfig){
            record.setRewardPoint(pointConfig.getPoint());
        }
        record.setEmpNo(empNo);
        record.setTaskNo(task.getId());
        record.setCreator(empNo);
        record.setCompleteTime(new Date());
        record.setCompleteCount(1);
        record.setRewardTime(null);
        return record;
    }

    private MyTaskVO convertToMyTaskVO(MyTaskPO myTaskPO) {
        MyTaskVO vo = new MyTaskVO();
        vo.setContent(myTaskPO.getContent());
        Point point = pointBO.getPoint(myTaskPO.getPointNo());
        vo.setRewardPoint(0);
        if(point != null){
            vo.setRewardPoint(point.getPoint());
        }
        vo.setTaskName(myTaskPO.getTitle());
        vo.setTaskNo(myTaskPO.getId());
        return vo;
    }

}
