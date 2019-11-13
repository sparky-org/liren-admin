package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.ApplyDtlBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.cond.QueryApplyCond;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.constant.ApplyStatusEnum;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.ApplyDtl;
import com.sparky.lirenadmin.entity.ApplyExample;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.invoker.NotifyInvoker;
import com.sparky.lirenadmin.mapper.ext.ApplyMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private ApplyDtlBO applyDtlBO;

    @Autowired
    private ApplyMapperExt applyMapper;

    @Override
    public void createApply(Apply apply, List<ApplyDtl> dtls) {
        apply.setGmtModify(new Date());
        apply.setGmtCreate(new Date());
        apply.setIsValid(true);
        apply.setAuditStatus(ApplyStatusEnum.NEW.getCode());
        applyMapper.insertSelective(apply);
        if (!CollectionUtils.isEmpty(dtls)){
            for (ApplyDtl dtl : dtls){
                dtl.setApplyNo(apply.getId());
                dtl.setApplyType(apply.getOrigin());
                dtl.setCreator(apply.getCreator());
                dtl.setShopNo(apply.getShopNo());
                applyDtlBO.createApplyDtl(dtl);
            }
        }
    }

    @Override
    public void approve(Apply apply) {
        if (apply.getId() == null){
            throw new RuntimeException("审核失败，无审核对象");
        }
        apply.setAuditStatus(ApplyStatusEnum.PASSED.getCode());
        apply.setGmtModify(new Date());
        applyMapper.updateByPrimaryKeySelective(apply);
        NotifyInvoker.invoke(ApplyApprovedHandler.class, "afterApplyApproved", new Object[]{apply.getOrigin(),apply.getOriginNo()});
    }

    @Override
    public void refuse(Apply apply) {
        if (apply.getId() == null){
            throw new RuntimeException("审核失败，无审核对象");
        }
        apply.setAuditStatus(ApplyStatusEnum.REFUSED.getCode());
        apply.setGmtModify(new Date());
        applyMapper.updateByPrimaryKeySelective(apply);
    }

    @Override
    public List<Apply> queryApprovalPendingTasks(Long id) {
        ApplyExample example = new ApplyExample();
        example.createCriteria().andAuditEmpNoEqualTo(id).andIsValidEqualTo(true);
        return applyMapper.selectByExample(example);
    }

    @Override
    public Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long approvalEmp, Long creator, Long shopNo) {
        Apply apply = new Apply();
        apply.setApplyContent(content);
        apply.setApplyEmpNo(applyEmp);
        apply.setAuditEmpNo(approvalEmp);
        apply.setCreator(creator);
        apply.setShopNo(shopNo);
        apply.setOrigin(origin);
        apply.setOriginNo(originId);
        return apply;
    }

    @Override
    public Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long creator, Long shopNo) {
        ShopEmployee admin = shopEmployeeBO.getShopAdmin(applyEmp);
        if (admin == null){
            throw new RuntimeException("没有管理员，不符合业务规则");
        }
        return buildApply(origin, originId, content, applyEmp, admin.getId(), creator, shopNo);
    }

    @Override
    public Apply queryApplyByOrigin(String origin, Long originNo) {
        ApplyExample example = new ApplyExample();
        example.createCriteria().andOriginEqualTo(origin).andOriginNoEqualTo(originNo).andIsValidEqualTo(true);
        example.setOrderByClause("gmt_modify desc");
        List<Apply> applies = applyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(applies)){
            return null;
        }
        return applies.iterator().next();
    }

    @Override
    public Integer countApply(QueryApplyCond cond) {
        ApplyExample example = new ApplyExample();
        ApplyExample.Criteria criteria = example.createCriteria().andIsValidEqualTo(true);
        if (cond.getEmpNo() != null) {
            criteria.andApplyEmpNoEqualTo(cond.getEmpNo());
        }
        if (cond.getAuditEmpNo() != null) {
            criteria.andAuditEmpNoEqualTo(cond.getAuditEmpNo());
        }
        if (cond.getApplyType() != null){
            criteria.andOriginEqualTo(cond.getApplyType());
        }
        if(cond.getStatus() != null){
            criteria.andAuditStatusEqualTo(cond.getStatus());
        }
        if (cond.getStart() != null && cond.getEnd() != null){
            criteria.andGmtCreateBetween(cond.getBegin(), cond.getEnd());
        }
        Long count = applyMapper.countByExample(example);
        return count == null ? 0 : count.intValue();
    }

    @Override
    public List<Apply> pagingQueryApply(QueryApplyCond cond) {
        return applyMapper.pagingQueryApply(cond);
    }

    @Override
    public Apply getApply(Long id) {
        return applyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void revert(Apply apply) {
        if (apply.getId() == null){
            throw new RuntimeException("待撤回申请的id不存在");
        }
        apply.setAuditStatus(ApplyStatusEnum.REVERTED.getCode());
        apply.setGmtModify(new Date());
        applyMapper.updateByPrimaryKeySelective(apply);
    }
}
