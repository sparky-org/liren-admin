package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskRecord;
import com.sparky.lirenadmin.mapper.TaskRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TaskRecordBOImpl implements TaskRecordBO {

    @Autowired
    private TaskRecordMapper taskRecordMapper;
    @Autowired
    private ApplyBO applyBO;
    @Autowired
    private TaskBO taskBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private PointBO pointBO;

    @Override
    public void createTaskRecord(TaskRecord record) {
        doCreateTaskRecord(record);
        applyBO.createApply(buildApply(record));
    }

    @Transactional
    @Override
    public void rewardTask(Long recordNo) {
        TaskRecord record = getTaskRecord(recordNo);
        if (record == null){
            throw new RuntimeException(String.format("不存在关联的任务完成记录[%s]", recordNo));
        }
        pointBO.increasePoint(buildIncreasePointDO(record), this::doRewardTask);
    }

    private IncreasePointDO buildIncreasePointDO(TaskRecord record) {
        IncreasePointDO pointDO = new IncreasePointDO();
        pointDO.setEmpNo(record.getEmpNo());
        pointDO.setOperator(record.getEmpNo());
        pointDO.setOrigin("TASK_RECORD");
        pointDO.setOriginNo(record.getId());
        pointDO.setPoint(record.getRewardPoint());
        return pointDO;
    }

    private Boolean doRewardTask(Long recordNo){
        TaskRecord record = new TaskRecord();
        record.setId(recordNo);
        record.setGmtModify(new Date());
        taskRecordMapper.updateByPrimaryKeySelective(record);
        return true;
    }

    private TaskRecord getTaskRecord(Long recordNo){
        return taskRecordMapper.selectByPrimaryKey(recordNo);
    }

    private Apply buildApply(TaskRecord record) {
        Apply apply = new Apply();
        apply.setApplyContent(buildApplyContent(record));
        apply.setApplyEmpNo(record.getEmpNo());
        ShopEmployee employee = shopEmployeeBO.getShopAdmin(record.getEmpNo());
        apply.setAuditEmpNo(employee.getManagerNo());
        apply.setCreator(record.getEmpNo());
        apply.setShopNo(record.getShopNo());
        apply.setOrigin("TASK_RECORD");
        apply.setOriginNo(record.getId());
        return apply;
    }

    private String buildApplyContent(TaskRecord record) {
        Task task = taskBO.getTask(record.getTaskNo());
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("任务名称：%s \n任务内容：%s \n申请奖励：%d 积分", task.getTitle(), task.getContent(), record.getRewardPoint()));
        return builder.toString();
    }

    private void doCreateTaskRecord(TaskRecord record) {
        record.setIsValid(true);
        record.setGmtCreate(new Date());
        record.setGmtModify(new Date());
        taskRecordMapper.insertSelective(record);
    }
}
