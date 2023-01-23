package com.hou27.basicboard.dto;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.Account;
import java.time.LocalDateTime;

public record ArticleDto(
    Long id,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String title,
    String content,
    AccountDto accountDto,
    String hashtag
) {
  public static ArticleDto of(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt, String title, String content, AccountDto accountDto, String hashtag) {
    return new ArticleDto(id, createdAt, modifiedAt, title, content, accountDto, hashtag);
  }

  public static ArticleDto of(String title, String content, AccountDto accountDto, String hashtag) {
    return new ArticleDto(null, null, null, title, content, accountDto, hashtag);
  }

  public static ArticleDto from(Article entity) {
    return new ArticleDto(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getModifiedAt(),
        entity.getTitle(),
        entity.getContent(),
        AccountDto.from(entity.getAccount()),
        entity.getHashtag()
    );
  }

  public Article toEntity(Account account) {
    return Article.of(
        title,
        content,
        account,
        hashtag
    );
  }
}
