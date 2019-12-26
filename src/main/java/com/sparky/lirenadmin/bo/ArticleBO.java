package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.Article;

import java.util.List;

public interface ArticleBO {
    void createArticle(Article article);

    Article getArticle(Long articleNo);

    List<Article> queryArticleByShop(Long shopNo);

    void deleteArticle(Long articleNo, Long empNo);
}
