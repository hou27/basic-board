package com.hou27.basicboard.dto;

public record CommentDto(
    Long id,
    Long articleId,
    UserAccountDto userAccountDto,
    String content,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime modifiedAt,
    String modifiedBy
) {
  public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
    return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
  }

  public static ArticleCommentDto from(ArticleComment entity) {
    return new ArticleCommentDto(
        entity.getId(),
        entity.getArticle().getId(),
        UserAccountDto.from(entity.getUserAccount()),
        entity.getContent(),
        entity.getCreatedAt(),
        entity.getCreatedBy(),
        entity.getModifiedAt(),
        entity.getModifiedBy()
    );
  }

  public ArticleComment toEntity(Article entity) {
    return ArticleComment.of(
        entity,
        userAccountDto.toEntity(),
        content
    );
  }

}