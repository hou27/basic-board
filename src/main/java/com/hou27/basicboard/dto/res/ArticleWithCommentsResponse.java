package com.hou27.basicboard.dto.res;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.hou27.basicboard.dto.ArticleWithCommentsDto;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
    Long id,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String title,
    String content,
    String hashtag,
    String email,
    String name,
    Set<CommentResponse> commentsResponse
) implements Serializable {

  public static ArticleWithCommentsResponse of(Long id, LocalDateTime createdAt,
      LocalDateTime modifiedAt, String title, String content, String hashtag, String email,
      String name, Set<CommentResponse> commentResponses) {
    return new ArticleWithCommentsResponse(id, createdAt, modifiedAt, title, content, hashtag, email, name,
        commentResponses);
  }

  public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
    return new ArticleWithCommentsResponse(
        dto.id(),
        dto.createdAt(),
        dto.modifiedAt(),
        dto.title(),
        dto.content(),
        dto.hashtag(),
        dto.accountDto().email(),
        dto.accountDto().name(),
        dto.articleCommentDtos().stream()
            .map(CommentResponse::from)
            .collect(Collectors.toCollection(LinkedHashSet::new))
    );
  }

}
