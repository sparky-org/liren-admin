package com.sparky.lirenadmin.controller;

import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateFormatUtils;
import com.sparky.lirenadmin.bo.ArticleBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.controller.request.PublishCompanySystemDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.ListCompanySystemVO;
import com.sparky.lirenadmin.controller.response.ViewCompanySystemVO;
import com.sparky.lirenadmin.entity.Article;
import com.sparky.lirenadmin.entity.ShopEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员可以发布公司制度
 * 普通员工只可以查看公司制度
 *
 * @ClassName NoticeController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */

@Api(tags = "公司制度接口")
@Controller
@RequestMapping("/companySystem")
public class CompanySystemController {

    @Autowired
    private ArticleBO articleBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;


    @ApiOperation("查询公司制度")
    @RequestMapping(value = "/listSystem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<ListCompanySystemVO>> listSystem(@RequestParam @ApiParam Long shopNo) {
        try {
            List<Article> articles = articleBO.queryArticleByShop(shopNo);
            if (CollectionUtils.isEmpty(articles)){
                BaseResponseWrapper.success(new ArrayList<>());
            }
            List<ListCompanySystemVO> listCompanySystemVOS = articles.stream().map(this::convertToListVO).collect(Collectors.toList());
            return BaseResponseWrapper.success(listCompanySystemVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("发布/修改公司制度")
    @RequestMapping(value = "/publishSystem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper publishNotice(@RequestBody PublishCompanySystemDTO dto) {
        try {
            articleBO.createArticle(buildArticle(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }



    @ApiOperation("查看公司制度")
    @RequestMapping(value = "/viewSystem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<ViewCompanySystemVO> viewNotice(@RequestParam @ApiParam Long articleNo,
                                                               @RequestParam @ApiParam Long empNo) {
        try {
            Article config = articleBO.getArticle(articleNo);
            return BaseResponseWrapper.success(convertToDto(config, empNo));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("删除公司制度")
    @RequestMapping(value = "/deleteSystem", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper deleteSystem(@RequestParam @ApiParam Long articleNo,
                                            @RequestParam @ApiParam Long empNo) {
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (!employee.getIsAdmin()){
                return BaseResponseWrapper.fail(null, "无权删除");
            }
            articleBO.deleteArticle(articleNo, empNo);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private ListCompanySystemVO convertToListVO(Article article){
        ListCompanySystemVO vo = new ListCompanySystemVO();
        vo.setCompanySystemNo(article.getId());
        vo.setTitle(article.getTitle());
        vo.setCreateDate(DateFormatUtils.format(article.getGmtCreate(), "yyyy-MM-dd"));
        return vo;
    }

    private ViewCompanySystemVO convertToDto(Article article, Long empNo) {
        ViewCompanySystemVO dto = new ViewCompanySystemVO();
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        dto.setCanEdit(false);
        if (employee != null) {
            dto.setCanEdit(employee.getIsAdmin());
        }
        if (article != null) {
            dto.setCompanySystemNo(article.getId());
            dto.setTitle(article.getTitle());
            dto.setContent(article.getContent());
        }
        return dto;
    }

    private Article buildArticle(PublishCompanySystemDTO dto) {
        Article ar = new Article();
        ar.setTitle(dto.getTitle());
        ar.setContent(dto.getContent());
        if (dto.getArticleNo() != null) {
            ar.setId(dto.getArticleNo());
        } else {
            ar.setCreator(dto.getEmpNo());
        }
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null || !employee.getIsAdmin()) {
            throw new RuntimeException(String.format("员工[%d]非管理员不能发布公司制度", dto.getEmpNo()));
        }
        ar.setShopNo(employee.getShopNo());
        return ar;
    }
}
