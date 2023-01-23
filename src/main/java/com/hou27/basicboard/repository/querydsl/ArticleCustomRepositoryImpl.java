package com.hou27.basicboard.repository.querydsl;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.QArticle;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
public class ArticleCustomRepositoryImpl extends QuerydslRepositorySupport implements ArticleCustomRepository {

  public ArticleCustomRepositoryImpl() {
    super(Article.class);
  }

  @Override
  public List<String> findAllDistinctHashtags() {
    QArticle article = QArticle.article;

    JPQLQuery<String> query = from(article)
        .select(article.hashtag)
        .distinct()
        .where(article.hashtag.isNotNull());

    return query.fetch();
  }

}
