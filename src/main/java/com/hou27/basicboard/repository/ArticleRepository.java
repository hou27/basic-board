package com.hou27.basicboard.repository;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
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

  @Override
  default void customize(QuerydslBindings bindings, QArticle root) {
    bindings.excludeUnlistedProperties(true); // 미리 정의하지 않은 검색 조건은 무시
    bindings.including(root.title, root.content, root.tag); // 검색 조건으로 사용할 필드를 지정

    // 현재 exact match만 지원하므로, contains로 변경
//    bindings.bind(root.title).first((path, value) -> path.contains(value));
    bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // lambda expression
    bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.tag).first(StringExpression::containsIgnoreCase);
//    bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 날짜 검색

    /**
     * Querydsl 검색 조건 설정
     */
    // like 검색
//    bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like 'value'
    // contains 검색(대소문자 구분 x)
//    bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%value%'

    // difference between contains and containsIgnoreCase
    // contains: 대소문자 구분
    // containsIgnoreCase: 대소문자 무시
  }
}
