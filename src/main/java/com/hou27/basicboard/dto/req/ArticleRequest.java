package com.hou27.basicboard.dto.req;

import com.hou27.basicboard.dto.AccountDto;
import com.hou27.basicboard.dto.ArticleDto;

public record ArticleRequest(
    String title,
    String content,
    String hashtag
) {

  public static ArticleRequest of(String title, String content, String hashtag) {
    return new ArticleRequest(title, content, hashtag);
  }

  public ArticleDto toDto(AccountDto accountDto) {
    return ArticleDto.of(
        title,
        content,
        accountDto,
        hashtag
    );
  }

}
