package com.hou27.basicboard.domain.constant;

import lombok.Getter;

public enum SearchType {
  TITLE("제목"),
  CONTENT("본문"),
  NAME("유저 이름"),
  HASHTAG("해시태그"),
  TITLE_AND_CONTENT("제목+본문");

  @Getter
  private final String description;

  SearchType(String description) {
    this.description = description;
  }

}