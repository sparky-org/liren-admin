package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskDtl;

import java.util.List;

/**
 *  任务类，管理员创建/修改/删除/查询任务配置
 *
 * @Description
 * @Author yuankai
 * @Date 2019-11-07
 *
 */
public interface TaskBO {

    void createTask(Task task, List<TaskDtl> dtls);

    void modifyTask(Task task);

    void deleteTask(Long taskNo);

    Task getTask(Long taskNo);

    int countTask(String pointType);

    List<Task> queryTask(String pointType);

}
