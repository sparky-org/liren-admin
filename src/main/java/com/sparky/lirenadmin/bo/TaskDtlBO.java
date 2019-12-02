package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.TaskDtl;

import java.util.List;

public interface TaskDtlBO {
    void createTaskDtl(TaskDtl dtl);

    void deleteDtlIfExist(Long taskId);

    List<TaskDtl> queryTaskDtlByTask(Long taskId);
}
