package com.hou27.basicboard.dto;

import com.hou27.basicboard.domain.Article;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsDto(
    Long id,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    AccountDto accountDto,
    Set<ArticleCommentDto> articleCommentDtos,
    String title,
    String content,
    String hashtag
) {

  public static ArticleWithCommentsDto of(Long id, LocalDateTime createdAt,
      LocalDateTime modifiedAt, AccountDto accountDto, Set<ArticleCommentDto> articleCommentDtos,
      String title, String content, String hashtag) {
    return new ArticleWithCommentsDto(id, createdAt, modifiedAt, accountDto, articleCommentDtos,
        title, content, hashtag);
  }

  public static ArticleWithCommentsDto from(Article entity) {
    return new ArticleWithCommentsDto(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getModifiedAt(),
        AccountDto.from(entity.getAccount()),
        entity.getComments().stream()
            .map(ArticleCommentDto::from)
            .collect(Collectors.toCollection(LinkedHashSet::new)), // LinkedHashSet을 사용하여 순서를 유지
        entity.getTitle(),
        entity.getContent(),
        entity.getHashtag()
    );
  }

}