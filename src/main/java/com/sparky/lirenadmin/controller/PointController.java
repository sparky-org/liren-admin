package com.sparky.lirenadmin.controller;


import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.PointConfigBO;
import com.sparky.lirenadmin.bo.RewardRecordBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.constant.PointTypeEnum;
import com.sparky.lirenadmin.controller.request.CreatePointDTO;
import com.sparky.lirenadmin.controller.request.GetPointTableDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.GetPointTableVO;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.PointConfig;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.PointTablePO;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "积分类型接口")
@Controller
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointBO pointBO;
    @Autowired
    private RewardRecordBO rewardRecordBO;
    @Autowired
    private PointConfigBO pointConfigBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;

    @ApiOperation("创建/修改积分配置")
    @RequestMapping(value = "/createOrModifyPointConfig",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createOrModifyPointConfig(@RequestBody CreatePointDTO createPointDTO){
        if (PointTypeEnum.valueOf(createPointDTO.getPointType()) == null){
            throw new RuntimeException(String.format("未配置积分类型[%s]", createPointDTO.getPointType()));
        }
        PointConfig config = buildPoint(createPointDTO);
        if (createPointDTO.getPointConfigNo() != null){
            config.setId(createPointDTO.getPointConfigNo());
            pointConfigBO.modifyPointConfig(config);
        }else{
            pointConfigBO.createPointConfig(config);
        }
        return BaseResponseWrapper.success(null);
    }

    @ApiOperation("删除积分配置")
    @RequestMapping(value = "/deletePointConfig",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper deletePointConfig(@RequestParam @ApiParam Long configNo,
                                                 @RequestParam @ApiParam Long operator){

        PointConfig config = new PointConfig();
        config.setId(configNo);
        config.setIsValid(false);
        pointConfigBO.modifyPointConfig(config);
        return BaseResponseWrapper.success(null);
    }

    @ApiOperation("获取积分配置")
    @RequestMapping(value = "/getPointConfig",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<PointConfig>> getPointConfig(@RequestParam @ApiParam Long shopNo){
        try {
            List<PointConfig> configs = pointConfigBO.getPointConfig(shopNo);
            return BaseResponseWrapper.success(configs);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("积分榜")
    @RequestMapping(value = "/getPointTable",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<GetPointTableVO>> getPointTable(@RequestBody GetPointTableDTO dto){
        try {

            Integer total = rewardRecordBO.countPointTable(dto.getShopNo(), dto.getInterval(), dto.getBeginDate(), dto.getEndDate());
            if (total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(), total);
            }
            Integer start = PagingUtils.getStartIndex(total,dto.getCurrentPage(), dto.getPageSize());
            List<PointTablePO> configs = rewardRecordBO.getPointTable(dto.getShopNo(), dto.getInterval(), dto.getBeginDate(), dto.getEndDate(),
                    start, dto.getPageSize());
            List<GetPointTableVO> vos = configs.stream().map(this::convertFromPO).collect(Collectors.toList());
            return PagingResponseWrapper.success(vos, total);
        } catch (Exception e) {
            e.printStackTrace();
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    private GetPointTableVO convertFromPO(PointTablePO configs) {
        GetPointTableVO vo = JSONObject.parseObject(JSONObject.toJSONString(configs), GetPointTableVO.class);
        vo.setRank(configs.getEmpRank());
        return vo;
    }

    private PointConfig buildPoint(CreatePointDTO dto){
        PointConfig point = new PointConfig();
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException(String.format("不存在编号为[%d]的员工", dto.getEmpNo()));
        }
        point.setShopNo(employee.getShopNo());
        point.setCreator(dto.getEmpNo());
        point.setPointType(dto.getPointType());
        point.setPointName(dto.getPointName());
        point.setPoint(dto.getValue());
        point.setPointDesc(dto.getContent());
        return point;
    }
}
