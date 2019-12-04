package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.PointConfigBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.TaskRecordBO;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.PointConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskRecord;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private ShopEmployeeBO shopEmployeeBO;
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
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                throw new RuntimeException("用户不存在");
            }
            QueryTaskCond cond = new QueryTaskCond();
            cond.setEmpNo(empNo);
            cond.setShopNo(employee.getShopNo());
            cond.setStatus(taskStatus);
            if (pointType != null){
                List<PointConfig> pointConfigs = pointConfigBO.getPointConfig(employee.getShopNo());
                if (pointConfigs != null){
                    List<PointConfig> filter = pointConfigs.stream().filter(e -> pointType.equals(e.getPointType())).collect(Collectors.toList());
                    if (filter != null){
                        List<Long> pointConfigNoList = filter.stream().map(PointConfig::getId).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(pointConfigNoList)){
                            cond.setPointNoList(pointConfigNoList);
                        }
                    }
                }
            }
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
    public BaseResponseWrapper completeTask(@RequestParam @ApiParam Long taskNo,
                                            @RequestParam @ApiParam Long empNo){
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
        record.setRewardPoint(task.getRewardPoint());
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
        vo.setRewardPoint(myTaskPO.getRewardPoint());
        vo.setTaskName(myTaskPO.getTitle());
        vo.setTaskNo(myTaskPO.getId());
        vo.setStatus(myTaskPO.getStatus());
        return vo;
    }

}
