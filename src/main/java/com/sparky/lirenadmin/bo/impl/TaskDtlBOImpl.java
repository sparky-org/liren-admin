package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.TaskDtlBO;
import com.sparky.lirenadmin.entity.TaskDtl;
import com.sparky.lirenadmin.entity.TaskDtlExample;
import com.sparky.lirenadmin.mapper.TaskDtlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TaskDtlBOImpl
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
@Service
public class TaskDtlBOImpl implements TaskDtlBO {

    @Autowired
    private TaskDtlMapper taskDtlMapper;

    @Override
    public void createTaskDtl(TaskDtl dtl) {
        dtl.setIsValid(true);
        dtl.setGmtCreate(new Date());
        dtl.setGmtModify(new Date());
        taskDtlMapper.insert(dtl);
    }

    @Override
    public void deleteDtlIfExist(Long taskId) {
        List<TaskDtl> dtlList = getTaskDtlByTask(taskId);
        if (CollectionUtils.isEmpty(dtlList)){
            return;
        }
        for (TaskDtl dtl : dtlList) {
            doDelete(dtl.getId());
        }
    }

    @Override
    public List<TaskDtl> queryTaskDtlByTask(Long taskId) {
        TaskDtlExample example = new TaskDtlExample();
        example.createCriteria().andIsValidEqualTo(true).andTaskNoEqualTo(taskId);
        return taskDtlMapper.selectByExample(example);
    }

    private void doDelete(Long id) {
        TaskDtl dtl = new TaskDtl();
        dtl.setIsValid(false);
        dtl.setId(id);
        dtl.setGmtModify(new Date());
        taskDtlMapper.updateByPrimaryKeySelective(dtl);
    }

    private List<TaskDtl> getTaskDtlByTask(Long taskId) {
        TaskDtlExample example = new TaskDtlExample();
        example.createCriteria().andIsValidEqualTo(true).andTaskNoEqualTo(taskId);
        return taskDtlMapper.selectByExample(example);
    }
}
