package com.sparky.lirenadmin.bo.impl;

import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.ApplyBO;
import com.sparky.lirenadmin.bo.VacationApplyBO;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.VacationApply;
import com.sparky.lirenadmin.mapper.ext.VacationApplyMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VacationApplyBOImpl implements VacationApplyBO {

    @Autowired
    private VacationApplyMapperExt vacationApplyMapper;
    @Autowired
    private ApplyBO applyBO;

    @Override
    public void createVacationApply(VacationApply apply) {
        doCreateVacationApply(apply);
        applyBO.createApply(buildApply(apply));
    }

    @Override
    public void afterApproved(Long applyId, String result) {
        VacationApply apply = new VacationApply();
        apply.setId(applyId);
        apply.setAuditStatus(result);
        apply.setGmtModify(new Date());
        vacationApplyMapper.updateByPrimaryKeySelective(apply);
    }

    @Override
    public VacationApply getVacation(Long id) {
        return vacationApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer sumRestEmployeeNum(ShopEmployee employee, Date today) {
        int total;
        if (employee.getIsAdmin()){
            total = vacationApplyMapper.sumRestEmployeeNumByShop(employee.getShopNo(), today);
        }else{
            total = vacationApplyMapper.sumRestEmployeeNumByEmp(employee.getShopNo(), today);
        }
        return total;
    }

    private void doCreateVacationApply(VacationApply apply) {
        apply.setAuditStatus("APPROVAL");
        apply.setIsValid(true);
        apply.setGmtCreate(new Date());
        apply.setGmtModify(new Date());
        vacationApplyMapper.insertSelective(apply);
    }

    private Apply buildApply(VacationApply apply) {
        return applyBO.buildApply(ApplyTypeEnum.VACATION.getCode(), apply.getId(), buildApplyContent(apply),
                apply.getApplyEmpNo(), apply.getAuditEmpNo(), apply.getCreator(),
                apply.getShopNo());
    }

    private String buildApplyContent(VacationApply apply) {
        return JSONObject.toJSONString(apply);
    }
}
