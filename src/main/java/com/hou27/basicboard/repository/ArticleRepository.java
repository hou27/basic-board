package com.hou27.basicboard.repository;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.QArticle;
import com.jayway.jsonpath.JsonPath;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository
    extends
    JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>, // Entity의 필드를 이용해 검색 조건을 만들 수 있도록 해줌(기본 검색)
    QuerydslBinderCustomizer<QArticle>  // Querydsl을 이용해 동적 검색을 할 수 있도록 해줌
{
  Page<Article> findByTitleContaining(String title, Pageable pageable);
  Page<Article> findByContentContaining(String content, Pageable pageable);
  Page<Article> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
  Page<Article> findByAccount_NameContaining(String searchKeyword, Pageable pageable);
  Page<Article> findByHashtag(String hashtag, Pageable pageable);

  @Override
  default void customize(QuerydslBindings bindings, QArticle root) {
    bindings.excludeUnlistedProperties(true); // 미리 정의하지 않은 검색 조건은 무시
    bindings.including(root.title, root.content, root.hashtag); // 검색 조건으로 사용할 필드를 지정

    // 현재 exact match만 지원하므로, contains로 변경
    bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // lambda expression
    bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
    // contains title and content
    bindings.bind(root.title, root.content).first((title, content) -> root.title.containsIgnoreCase(title).and(root.content.containsIgnoreCase(content)));

  }
}
