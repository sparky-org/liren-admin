package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ArticleBO;
import com.sparky.lirenadmin.entity.Article;
import com.sparky.lirenadmin.entity.ArticleExample;
import com.sparky.lirenadmin.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleBOImpl implements ArticleBO {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void createArticle(Article article) {
        if (article.getId() == null) {
            article.setGmtCreate(new Date());
            article.setGmtModify(new Date());
            article.setIsValid(true);
            articleMapper.insertSelective(article);
        }else{
            article.setGmtModify(new Date());
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    @Override
    public Article getArticle(Long articleNo) {
        return articleMapper.selectByPrimaryKey(articleNo);
    }

    @Override
    public List<Article> queryArticleByShop(Long shopNo) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andShopNoEqualTo(shopNo).andIsValidEqualTo(true);
        return articleMapper.selectByExample(example);
    }

    @Override
    public void deleteArticle(Long articleNo, Long empNo) {
        Article article = new Article();
        article.setId(articleNo);
        article.setIsValid(false);
        article.setGmtModify(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
    }
}