package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ApplyDtlBO;
import com.sparky.lirenadmin.entity.ApplyDtl;
import com.sparky.lirenadmin.entity.ApplyDtlExample;
import com.sparky.lirenadmin.mapper.ApplyDtlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ApplyDtlBOImpl
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
@Service
public class ApplyDtlBOImpl implements ApplyDtlBO {

    @Autowired
    private ApplyDtlMapper applyDtlMapper;

    @Override
    public List<ApplyDtl> listApplyDtl(Long applyNo) {
        ApplyDtlExample example = new ApplyDtlExample();
        example.createCriteria().andIsValidEqualTo(true).andApplyNoEqualTo(applyNo);
        return applyDtlMapper.selectByExample(example);
    }
}
