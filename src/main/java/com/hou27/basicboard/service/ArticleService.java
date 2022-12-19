package com.hou27.basicboard.service;

import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.dto.ArticleCommentDto;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.dto.ArticleUpdateDto;
import com.hou27.basicboard.repository.ArticleRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
  private final ArticleRepository articleRepository;

  @Transactional(readOnly = true) // 조회만 하는 메서드에는 readOnly = true -> memory 사용량 최적화
  public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
    return Page.empty();
  }

  @Transactional(readOnly = true)
  public ArticleDto searchArticle(long l) {
    return null;
  }

  public void saveArticle(ArticleDto dto) {
  }

  public void updateArticle(long articleId, ArticleUpdateDto dto) {
  }

  public void deleteArticle(long articleId) {
  }
}

@RequiredArgsConstructor
@Transactional
@Service
class ArticleCommentService {

  @Transactional(readOnly = true)
  public List<ArticleCommentDto> searchArticleComments(Long articleId) {
    return null;
  }

  public void saveArticleComment(ArticleCommentDto dto) {
  }

  public void updateArticleComment(ArticleCommentDto dto) {
  }

  public void deleteArticleComment(Long articleCommentId) {
  }
}