package com.hou27.basicboard.dto;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.Comment;
import java.time.LocalDateTime;

public record ArticleCommentDto(
    Long id,
    Long articleId,
    AccountDto userAccountDto,
    String content,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {
  public static ArticleCommentDto of(Long id, Long articleId, AccountDto userAccountDto, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, modifiedAt);
  }

  public static ArticleCommentDto from(Comment entity) {
    return new ArticleCommentDto(
        entity.getId(),
        entity.getArticle().getId(),
        AccountDto.from(entity.getAccount()),
        entity.getContent(),
        entity.getCreatedAt(),
        entity.getModifiedAt()
    );
  }

  public Comment toEntity(Article entity) {
    return Comment.of(
        entity,
        userAccountDto.toEntity(),
        content
    );
  }

}
