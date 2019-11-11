package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyTaskVO;
import com.sparky.lirenadmin.entity.Point;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    private PointBO pointBO;

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
