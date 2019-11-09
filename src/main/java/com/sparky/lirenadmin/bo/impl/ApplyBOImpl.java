package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.ApplyExample;
import com.sparky.lirenadmin.invoker.NotifyInvoker;
import com.sparky.lirenadmin.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 申请类
 *
 * @ClassName ApplyBOImpl
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
@Service
public class ApplyBOImpl implements ApplyBO {

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public void createApply(Apply apply) {
        apply.setGmtModify(new Date());
        apply.setGmtCreate(new Date());
        apply.setIsValid(true);
        apply.setAuditStatus("NEW");
        applyMapper.insertSelective(apply);
    }

    @Override
    public void approve(Apply apply) {
        if (apply.getId() == null){
            throw new RuntimeException("审核失败，无审核对象");
        }
        apply.setAuditStatus("APPROVED");
        apply.setGmtModify(new Date());
        applyMapper.updateByPrimaryKeySelective(apply);
        NotifyInvoker.invoke(ApplyApprovedHandler.class, "afterApplyApproved", apply.getOriginNo());
    }

    @Override
    public List<Apply> queryApprovalPendingTasks(Long id) {
        ApplyExample example = new ApplyExample();
        example.createCriteria().andAuditEmpNoEqualTo(id).andIsValidEqualTo(true);
        return applyMapper.selectByExample(example);
    }
}
