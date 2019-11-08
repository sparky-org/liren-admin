package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskDtl;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
import com.sparky.lirenadmin.utils.PagingUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TaskBOTest
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.sparky.lirenadmin.mapper")
@Transactional
@Rollback(true)
public class TaskBOTest {
    @Autowired
    private TaskBO taskBO;

    @Test
    public void testCreate(){
        Task task = initTask();
        List<TaskDtl> taskDtls = initTaskDtls();
        taskBO.createTask(task, taskDtls);
    }

    @Test
    public void testPagingQueryCond(){
        QueryTaskCond cond = initQueryCond();
        int total = taskBO.countTask(cond);
        int start = PagingUtils.getStartIndex(total, 1,1);
        cond.setStart(start);
        cond.setLength(1);
        List<MyTaskPO> taskPOS = taskBO.queryTask(cond);
        Assert.assertTrue(taskPOS.size() == 0);
    }

    private QueryTaskCond initQueryCond() {
        QueryTaskCond cond = new QueryTaskCond();
        cond.setEmpNo(2l);
        return cond;
    }

    private List<TaskDtl> initTaskDtls() {
        List<TaskDtl> dtls = new ArrayList<>();
        TaskDtl dtl = new TaskDtl();
        dtl.setCreator(1L);
        dtl.setEmpNo(1L);
        dtl.setShopNo(1L);
        return dtls;
    }

    private Task initTask() {
        Task task = new Task();
        task.setTitle("测试任务");
        task.setContent("测试任务");
        task.setCreator(1L);
        task.setJoinLimit(0);
        task.setPointNo(1l);
        task.setScope("ALL");
        task.setShopNo(1l);
        return task;
    }

}
