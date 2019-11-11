package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.TaskRecordBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskRecord;
import com.sparky.lirenadmin.mapper.TaskRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
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
    private PointBO pointBO;

    @Override
    public void createTaskRecord(@NotNull TaskRecord record) {
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
        return pointBO.buildIncreasePointDO(RewardTypeEnum.TASK_RECORD.getCode(), record.getId(),
                record.getEmpNo(), record.getCreator(), record.getRewardPoint(), record.getShopNo());
    }

    private Boolean doRewardTask(Long recordNo){
        TaskRecord record = new TaskRecord();
        record.setId(recordNo);
        record.setIsRewarded(true);
        record.setGmtModify(new Date());
        taskRecordMapper.updateByPrimaryKeySelective(record);
        return true;
    }

    public TaskRecord getTaskRecord(Long recordNo){
        return taskRecordMapper.selectByPrimaryKey(recordNo);
    }

    private Apply buildApply(@NotNull TaskRecord record) {
        Apply apply = applyBO.buildApply(RewardTypeEnum.TASK_RECORD.getCode(), record.getId(), buildApplyContent(record),record.getEmpNo(), record.getCreator(), record.getShopNo());
        return apply;
    }

    private String buildApplyContent(TaskRecord record) {
        Task task = taskBO.getTask(record.getTaskNo());
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("任务名称：%s \n任务内容：%s \n申请奖励：%d 积分", task.getTitle(), task.getContent(), record.getRewardPoint()));
        return builder.toString();
    }

    private void doCreateTaskRecord(TaskRecord record) {
        record.setIsRewarded(false);
        record.setIsValid(true);
        record.setGmtCreate(new Date());
        record.setGmtModify(new Date());
        taskRecordMapper.insertSelective(record);
    }
}
