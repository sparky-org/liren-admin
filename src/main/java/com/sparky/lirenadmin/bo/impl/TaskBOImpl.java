package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskDtl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskBOImpl implements TaskBO {

    @Override
    public void createTask(Task task, List<TaskDtl> dtls) {

    }

    @Override
    public void modifyTask(Task task) {

    }

    @Override
    public void deleteTask(Long taskNo) {

    }

    @Override
    public Task getTask(Long taskNo) {
        return null;
    }

    @Override
    public int countTask(String pointType) {
        return 0;
    }

    @Override
    public List<Task> queryTask(String pointType) {
        return null;
    }
}
