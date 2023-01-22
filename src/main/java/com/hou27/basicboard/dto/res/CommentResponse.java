package com.hou27.basicboard.dto.res;

import com.hou27.basicboard.dto.ArticleCommentDto;
import java.io.Serializable;
import java.time.LocalDateTime;

public record CommentResponse(
    Long id,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String content,
    String email,
    String name
) implements Serializable {

  public static CommentResponse of(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt, String content, String email, String name) {
    return new CommentResponse(id, createdAt, modifiedAt, content,  email, name);
  }

  public static CommentResponse from(ArticleCommentDto dto) {
    return new CommentResponse(
        dto.id(),
        dto.createdAt(),
        dto.modifiedAt(),
        dto.content(),
        dto.accountDto().email(),
        dto.accountDto().name()
    );
  }

}
