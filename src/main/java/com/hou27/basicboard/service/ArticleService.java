package com.hou27.basicboard.service;

import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.dto.ArticleCommentDto;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.dto.ArticleWithCommentsDto;
import com.hou27.basicboard.repository.ArticleRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
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
  public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
    if(searchKeyword == null || searchKeyword.isBlank()) {
      return articleRepository.findAll(pageable).map(ArticleDto::from);
    }

//    switch (searchType) {
//      case TITLE:
//        return articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
//      case CONTENT:
//        return articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
//      case TITLE_AND_CONTENT:
//        return articleRepository.findByTitleContainingOrContentContaining(searchKeyword, searchKeyword, pageable).map(ArticleDto::from);
//      default:
//        return articleRepository.findAll(pageable).map(ArticleDto::from);
//    }

    switch (searchType) {
      case TITLE -> {
        return articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
      }
      case CONTENT -> {
        return articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
      }
      case TITLE_AND_CONTENT -> {
        return articleRepository.findByTitleContainingOrContentContaining(searchKeyword, searchKeyword, pageable).map(ArticleDto::from);
      }
      case ID -> {
        // parse searchKeyword to Long
        return articleRepository.findByAccount_IdContaining(Long.parseLong(searchKeyword), pageable).map(ArticleDto::from);
      }
      case NAME -> {
        return articleRepository.findByNameContaining(searchKeyword, pageable).map(ArticleDto::from);
      }
      case HASHTAG -> {
        return articleRepository.findByHashtag(searchKeyword, pageable).map(ArticleDto::from);
      }
      default -> {
        return articleRepository.findAll(pageable).map(ArticleDto::from);
      }
    }
  }

  @Transactional(readOnly = true)
  public ArticleWithCommentsDto getArticle(Long articleId) {
    return articleRepository.findById(articleId)
        .map(ArticleWithCommentsDto::from)
        .orElseThrow(() -> new EntityNotFoundException("Article Not Found"));
  }

  public void saveArticle(ArticleDto dto) {
  }

  public void updateArticle(long articleId, ArticleDto dto) {
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