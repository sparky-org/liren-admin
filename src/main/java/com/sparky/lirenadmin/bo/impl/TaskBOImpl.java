package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.TaskDtlBO;
import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskDtl;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
import com.sparky.lirenadmin.mapper.TaskMapper;
import com.sparky.lirenadmin.mapper.ext.TaskMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class TaskBOImpl implements TaskBO {

    @Autowired
    private TaskDtlBO taskDtlBO;

    @Autowired
    private TaskMapperExt taskMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createTask(Task task, List<TaskDtl> dtls) {
        if (task == null){
            throw new RuntimeException("任务为空，不能创建");
        }
        doCreate(task);
        if (!CollectionUtils.isEmpty(dtls)){
            for (TaskDtl dtl : dtls){
                dtl.setTaskNo(task.getId());
                taskDtlBO.createTaskDtl(dtl);
            }
        }
    }

    @Override
    public void modifyTask(Task task) {

    }

    @Override
    public void deleteTask(Long taskNo) {

    }

    @Override
    public Task getTask(Long taskNo) {
        return taskMapper.selectByPrimaryKey(taskNo);
    }

    @Override
    public int countTask(QueryTaskCond cond) {
        return taskMapper.countByQueryCond(cond);
    }

    @Override
    public List<MyTaskPO> queryTask(QueryTaskCond cond) {
        return taskMapper.pagingByQueryCond(cond);
    }

    private void doCreate(Task task) {
        task.setIsValid(true);
        task.setGmtCreate(new Date());
        task.setGmtModify(new Date());
        taskMapper.insertSelective(task);
    }

}
