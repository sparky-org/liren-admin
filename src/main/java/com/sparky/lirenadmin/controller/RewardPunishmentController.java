package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.sparky.lirenadmin.bo.RewardPunishmentBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.SubmitAwardDeductionDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.controller.response.QueryAwardDeductionVO;
import com.sparky.lirenadmin.entity.RewardPunishment;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  积分奖扣，由管理直接对某个员工进行积分奖扣，不需要审核
 *
 * @ClassName RewardConfigController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "积分奖扣接口")
@Controller
@RequestMapping("/award/deduction")
public class RewardPunishmentController {

    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private RewardPunishmentBO rewardPunishmentBO;

    @ApiOperation("积分奖扣")
    @RequestMapping(value = "/submitAwardDeduction",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper submitAwardDeduction(@RequestBody SubmitAwardDeductionDTO dto){
        try {
            if (CollectionUtils.isEmpty(dto.getEmpNoList())){
                throw new RuntimeException("奖扣员工不存在");
            }
            ShopEmployee submitter = shopEmployeeBO.getEmployee(dto.getSubmitter());
            if(submitter == null){
                throw new RuntimeException(String.format("奖扣执行人[%d]不存在", dto.getSubmitter()));
            }
            for (Long empNo : dto.getEmpNoList()) {
                rewardPunishmentBO.createRewardPunishment(buildRewardPunishment(dto, empNo, submitter.getShopNo()));
            }
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("积分奖扣明细")
    @RequestMapping(value = "/queryAwardDeduction",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<QueryAwardDeductionVO>> queryAwardDeduction(@RequestParam @ApiParam Long empNo,
                                                                                  @RequestParam(required = false) @ApiParam Long contentLike,
                                                                                  @RequestParam @ApiParam Integer currentPage,
                                                                                  @RequestParam @ApiParam Integer pageSize){
        int total = rewardPunishmentBO.countRewardPunishment(empNo, contentLike);
        if (total < 1){
            return PagingResponseWrapper.success(new ArrayList<>(), total);
        }
        int start = PagingUtils.getStartIndex(total, currentPage, pageSize);
        List<RewardPunishment> rps = rewardPunishmentBO.queryRewardPunishment(empNo, contentLike, start, pageSize);
        List<Long> empNoList = rps.stream().map(RewardPunishment::getEmpNo).collect(Collectors.toList());
        List<ShopEmployee> shopEmployees = shopEmployeeBO.listEmploy(empNoList);
        if (CollectionUtils.isEmpty(shopEmployees)){
            //可能所有员工均离职了
            shopEmployees = new ArrayList<>();
        }
        Map<Long, String> empNoName = shopEmployees.stream().collect(Collectors.toMap(ShopEmployee::getId, ShopEmployee::getName));
        List<QueryAwardDeductionVO> vos = rps.stream().map(e -> convertFromRP(e, empNoName)).collect(Collectors.toList());
        return PagingResponseWrapper.success(vos, total);
    }

    private QueryAwardDeductionVO convertFromRP(RewardPunishment e, Map<Long, String> empNoName) {
        QueryAwardDeductionVO vo = new QueryAwardDeductionVO();
        vo.setContent(e.getContent());
        vo.setDate(DateUtils.formatDate(e.getExecTime(), "yyyy-MM-dd"));
        String name = empNoName.get(e.getEmpNo());
        if (name != null){
            vo.setEmpName(name);
        }else{
            vo.setEmpName(e.getEmpNo().toString());
        }
        vo.setReward(e.getPoint() > 0);
        int absPoint = Math.abs(e.getPoint());
        vo.setRewardDesc(e.getPoint() > 0 ? "奖励"+ absPoint +"积分" : "扣除" + absPoint + "积分");
        return vo;
    }

    private RewardPunishment buildRewardPunishment(SubmitAwardDeductionDTO dto, Long empNo, Long shopNo) {
        RewardPunishment punishment = new RewardPunishment();
        punishment.setIsRewarded(false);
        punishment.setRewardTime(null);
        punishment.setContent(dto.getContent());
        punishment.setCreator(dto.getSubmitter());
        punishment.setEmpNo(empNo);
        punishment.setExecTime(new Date());
        if (CollectionUtils.isEmpty(dto.getPicList())){
            punishment.setPicList(JSONArray.toJSONString(dto.getPicList()));
        }
        punishment.setPoint(dto.getPoint());
        punishment.setShopNo(shopNo);
        punishment.setTitle(dto.getTitle());
        return punishment;
    }

}
