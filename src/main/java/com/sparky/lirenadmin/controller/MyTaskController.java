package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.controller.request.PublishTaskDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.entity.Point;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskDtl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ApiOperation("我的任务列表")
    @RequestMapping(value = "/queryMyTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<MyTaskVO>> queryMyTask(@RequestParam(required = false) @ApiParam("奖励编号，通过/point/getPoint获取工作积分/品德积分的id") Long pointNo,
                                                           @RequestParam(required = false) @ApiParam("任务状态：未完成-UNCOMPLETE/已完成-COMPLETE") String taskStatus,
                                                           @RequestParam @ApiParam Long empNo,
                                                           @RequestParam @ApiParam Integer currentPage,
                                                           @RequestParam @ApiParam Integer pageSize){
        QueryTaskCond cond = new QueryTaskCond();
        cond.setEmpNo(empNo);
        int total = taskBO.countTask(cond);
        if (total < 1){
            return BaseResponseWrapper.success(new ArrayList<>());
        }
        int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
        cond.setStart(start);
        cond.setLength(pageSize);
        List<MyTaskPO> tasks = taskBO.queryTask(cond);
        if (taskStatus != null){
            tasks = tasks.stream().filter(p -> p.getStatus().equals(taskStatus)).collect(Collectors.toList());
        }
        List<MyTaskVO> myTaskVOS = tasks.stream().map(this::convertToMyTaskVO).collect(Collectors.toList());
        return BaseResponseWrapper.success(myTaskVOS);
    }

    @ApiOperation("发布任务")
    @RequestMapping(value = "/publishTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishTask(@RequestBody PublishTaskDTO dto){
        try {
            ShopEmployee shopEmployee = shopEmployeeBO.getEmployee(dto.getEmpNo());
            if (shopEmployee == null){
                throw new RuntimeException("发布人不存在");
            }
            taskBO.createTask(buildTask(dto, shopEmployee), assembleTaskDtl(dto, shopEmployee));
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    private List<TaskDtl> assembleTaskDtl(PublishTaskDTO dto,ShopEmployee shopEmployee) {
        Set<Long> employeeList = new HashSet<>();
        if (CollectionUtils.isEmpty(dto.getJobList()) && CollectionUtils.isEmpty(dto.getEmpList())){
            List<ShopEmployee> shopEmployees = employeeBO.getEmployeeByShopNo(shopEmployee.getShopNo());
            List<Long> list = shopEmployees.stream().map(ShopEmployee::getId).collect(Collectors.toList());
            employeeList.addAll(list);
        }else{
            if (!CollectionUtils.isEmpty(dto.getJobList())){
                List<ShopEmployee> shopEmployees = shopEmployeeBO.listEmployByJobList(dto.getJobList());
                List<Long> list = shopEmployees.stream().map(ShopEmployee::getId).collect(Collectors.toList());
                employeeList.addAll(list);
            }
            if (!CollectionUtils.isEmpty(dto.getEmpList())){
                employeeList.addAll(dto.getEmpList());
            }
        }

        if (CollectionUtils.isEmpty(employeeList)){
            return null;
        }
        List<TaskDtl> taskDtls = employeeList.stream().map(l -> {
            TaskDtl dtl = new TaskDtl();
            dtl.setEmpNo(l);
            dtl.setShopNo(shopEmployee.getShopNo());
            dtl.setCreator(dto.getEmpNo());
            return  dtl;
        }).collect(Collectors.toList());
        return taskDtls;
    }

    private Task buildTask(PublishTaskDTO dto,ShopEmployee shopEmployee) {
        Task task = new Task();
        task.setContent(dto.getTaskDesc());
        task.setCreator(dto.getEmpNo());
        task.setPointNo(dto.getPointConfigNo());
        task.setJoinLimit(0);
        task.setScope("");
        if (!shopEmployee.getIsAdmin()){
            throw new RuntimeException("发布人必须是管理员");
        }
        task.setShopNo(shopEmployee.getShopNo());
        task.setTitle(dto.getTaskTitle());
        return task;
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
