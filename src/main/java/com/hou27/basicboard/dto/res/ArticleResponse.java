package com.hou27.basicboard.dto.res;

import com.hou27.basicboard.dto.ArticleDto;
import java.time.LocalDateTime;
import java.io.Serializable;

public record ArticleResponse(
    Long id,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String title,
    String content,
    String hashtag,
    String email,
    String name
) implements Serializable {

  public static ArticleResponse of(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt, String title, String content, String hashtag, String email, String name) {
    return new ArticleResponse(id, createdAt, modifiedAt, title, content, hashtag, email, name);
  }

  public static ArticleResponse from(ArticleDto dto) {
    return new ArticleResponse(
        dto.id(),
        dto.createdAt(),
        dto.modifiedAt(),
        dto.title(),
        dto.content(),
        dto.hashtag(),
        dto.accountDto().email(),
        dto.accountDto().name()
    );
  }

}