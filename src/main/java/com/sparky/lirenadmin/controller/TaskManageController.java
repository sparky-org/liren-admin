package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.controller.request.ModifyTaskDTO;
import com.sparky.lirenadmin.controller.request.PublishTaskDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.controller.response.ViewTaskVO;
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
import org.springframework.util.Assert;
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
@Api(tags = "任务管理接口")
@Controller
@RequestMapping("/manage/task")
public class TaskManageController {

    private static final Logger logger = LoggerFactory.getLogger(TaskManageController.class);

    @Autowired
    private TaskBO taskBO;
    @Autowired
    private PointBO pointBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private EmployeeBO employeeBO;
    @Autowired
    private PointConfigBO pointConfigBO;
    @Autowired
    private TaskDtlBO taskDtlBO;

    @ApiOperation("任务列表")
    @RequestMapping(value = "/queryTask",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<MyTaskVO>> queryTask(@RequestParam(required = false) @ApiParam("开始日期:yyyy-MM-dd") String beginDate,
                                                           @RequestParam(required = false) @ApiParam("结束日期:yyyy-MM-dd") String endDate,
                                                           @RequestParam(required = false) @ApiParam("可见范围：不限/jobNo") Long jobNo,
                                                           @RequestParam @ApiParam Long empNo,
                                                           @RequestParam @ApiParam Integer currentPage,
                                                           @RequestParam @ApiParam Integer pageSize){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                throw new RuntimeException("员工不存在");
            }
            int total = taskBO.countManageTask(convertDate(beginDate, true), convertDate(endDate, false), jobNo, employee.getShopNo());
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(), total);
            }
            int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            List<Task> tasks = taskBO.queryManageTask(convertDate(beginDate, true), convertDate(endDate, false), jobNo, employee.getShopNo(), start, pageSize);
            List<MyTaskVO> myTaskVOS = tasks.stream().map(this::convertToMyTaskVO).collect(Collectors.toList());
            return PagingResponseWrapper.success(myTaskVOS, total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    @ApiOperation("发布任务")
    @RequestMapping(value = "/publishTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishTask(@RequestBody PublishTaskDTO dto){
        try {
            validateParams(dto);
            ShopEmployee shopEmployee = shopEmployeeBO.getEmployee(dto.getEmpNo());
            checkPrivilege(shopEmployee);
            taskBO.createTask(buildTask(dto, shopEmployee), assembleTaskDtl(dto, shopEmployee));
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    @ApiOperation("修改任务")
    @RequestMapping(value = "/modifyTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper modifyTask(@RequestBody ModifyTaskDTO dto){
        try {
            validateParams(dto);
            ShopEmployee shopEmployee = shopEmployeeBO.getEmployee(dto.getEmpNo());
            checkPrivilege(shopEmployee);
            Task task = buildTask(dto, shopEmployee);
            task.setId(dto.getTaskNo());
            taskBO.modifyTask(task, assembleTaskDtl(dto, shopEmployee));
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    @RequestMapping(value = "/deleteTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper deleteTask(@RequestParam @ApiParam Long taskNo,
                                          @RequestParam @ApiParam Long empNo){
        try {
            ShopEmployee shopEmployee = shopEmployeeBO.getEmployee(empNo);
            checkPrivilege(shopEmployee);
            Task task = new Task();
            task.setId(taskNo);
            task.setIsValid(false);
            taskBO.modifyTask(task, null);
            return BaseResponseWrapper.success(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    @RequestMapping(value = "/viewTask",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewTaskVO> viewTask(@RequestParam @ApiParam Long taskNo,
                                                    @RequestParam @ApiParam Long empNo){
        try {
            Task task = taskBO.getTask(taskNo);
            if (null == task){
                return BaseResponseWrapper.success(null);
            }
            ViewTaskVO viewTaskVO = new ViewTaskVO();
            viewTaskVO.setContent(task.getContent());
            if ("ALL".equals(task.getScope())){
                viewTaskVO.setSelectAll(true);
            }else{
                viewTaskVO.setSelectAll(false);
            }
            if (!viewTaskVO.getSelectAll()){
                List<TaskDtl> dtls = taskDtlBO.queryTaskDtlByTask(task.getId());
                if (!CollectionUtils.isEmpty(dtls)){
                    List<Long> empNos = dtls.stream().map(TaskDtl::getEmpNo).collect(Collectors.toList());
                    viewTaskVO.setEmpNoList(empNos);
                }
            }
            viewTaskVO.setPointNo(task.getPointNo());
            PointConfig pointConfig = pointConfigBO.getPointConfigByPrimaryKey(task.getPointNo());
            if (null != pointConfig) {
                viewTaskVO.setPointType(pointConfig.getPointType());
            }
            viewTaskVO.setRewardPoint(task.getRewardPoint());
            viewTaskVO.setTaskNo(task.getId());
            viewTaskVO.setTitle(task.getTitle());
            return BaseResponseWrapper.success(viewTaskVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, null);
        }
    }

    private void validateParams(PublishTaskDTO dto) {
        Assert.notNull(dto.getRewardPoint(), "奖励积分不能为空值");
        Assert.isTrue(Math.abs(dto.getRewardPoint()) < 1000, "任务奖励积分值不能大于1000");
    }

    private void checkPrivilege(ShopEmployee employee){
        if (employee == null){
            throw new RuntimeException("发布人不存在");
        }
        if (!employee.getIsAdmin()){
            throw new RuntimeException("发布人必须是管理员");
        }
    }

    private Date convertDate(String date, Boolean isDayBegin){
        if (date == null){
            return null;
        }
        Date d = DateUtils.getDateTime(date);
        return isDayBegin ? DateUtils.getDayBegining(d) : DateUtils.getDayEnding(d);
    }

    private List<TaskDtl> assembleTaskDtl(PublishTaskDTO dto,ShopEmployee shopEmployee) {
        if (dto.getSelectAll()){
            return null;
        }
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
        if (!shopEmployee.getIsAdmin()){
            throw new RuntimeException("发布人必须是管理员");
        }
        Task task = new Task();
        task.setContent(dto.getTaskDesc());
        task.setCreator(dto.getEmpNo());
        task.setPointNo(dto.getPointConfigNo());
        task.setJoinLimit(0);
        if (dto.getSelectAll() != null && dto.getSelectAll()){
            task.setScope("ALL");
        }else{
            task.setScope("SPECIAL");
        }
        task.setShopNo(shopEmployee.getShopNo());
        task.setTitle(dto.getTaskTitle());
        task.setRewardPoint(dto.getRewardPoint());
        return task;
    }

    private MyTaskVO convertToMyTaskVO(Task myTaskPO) {
        MyTaskVO vo = new MyTaskVO();
        String content = myTaskPO.getContent();
        if (content != null){
            vo.setContent(content.replaceAll("\n", "<br>"));
        }
        vo.setRewardPoint(myTaskPO.getRewardPoint());
        Point point = pointBO.getPoint(myTaskPO.getPointNo());
        vo.setTaskName(myTaskPO.getTitle());
        if(point != null){
            vo.setRewardPoint(point.getPoint());
        }
        vo.setTaskNo(myTaskPO.getId());
        return vo;
    }
}
