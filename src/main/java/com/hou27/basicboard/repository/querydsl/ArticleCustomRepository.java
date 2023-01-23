package com.hou27.basicboard.repository.querydsl;

import java.util.List;

public interface ArticleCustomRepository {
  List<String> findAllDistinctHashtags();
}
