package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.TaskDtlBO;
import com.sparky.lirenadmin.entity.TaskDtl;
import com.sparky.lirenadmin.mapper.TaskDtlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
