package com.hou27.basicboard.repository;

import com.hou27.basicboard.domain.Comment;
import com.hou27.basicboard.domain.QComment;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommentRepository
    extends
    JpaRepository<Comment, Long>,
    QuerydslPredicateExecutor<Comment>,
    QuerydslBinderCustomizer<QComment>
{
  @Override
  default void customize(QuerydslBindings bindings, QComment root) {
    bindings.excludeUnlistedProperties(true); // 미리 정의하지 않은 검색 조건은 무시
    bindings.including(root.content); // 검색 조건으로 사용할 필드를 지정

    bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
  }
}