package com.hou27.basicboard.service;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.domain.Account;
import com.hou27.basicboard.dto.ArticleCommentDto;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.dto.ArticleWithCommentsDto;
import com.hou27.basicboard.repository.ArticleRepository;
import com.hou27.basicboard.repository.AccountRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final AccountRepository accountRepository;

  @Transactional(readOnly = true) // 조회만 하는 메서드에는 readOnly = true -> memory 사용량 최적화
  public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
    if(searchKeyword == null || searchKeyword.isBlank()) {
      return articleRepository.findAll(pageable).map(ArticleDto::from);
    }

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
      case NAME -> {
        // parse searchKeyword to Long
        return articleRepository.findByAccount_NameContaining(searchKeyword, pageable).map(ArticleDto::from);
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
    Account account = accountRepository./*findById*/getReferenceById(dto.accountDto().userId());
//        .orElseThrow(() -> new EntityNotFoundException("Account Not Found"));
    articleRepository.save(dto.toEntity(account));
  }

  public void updateArticle(ArticleDto dto) {
    try {
      Article article = articleRepository.getReferenceById(dto.id());
      if (dto.title() != null) { article.setTitle(dto.title()); }
      if (dto.content() != null) { article.setContent(dto.content()); }
      article.setHashtag(dto.hashtag());

      // transaction 종료 시 영속성 컨텍스트에 있는 엔티티의 수정 사항을 자동으로 DB에 반영하므로 save() 메서드를 호출할 필요가 없다.
//      articleRepository.save(article);
    } catch (EntityNotFoundException e) {
      log.warn("Article Not Found");
      throw e;
    }
  }

  public void deleteArticle(long articleId) {
    try {
      articleRepository.deleteById(articleId);
    } catch (EntityNotFoundException e) {
      log.warn("Article Not Found");
      throw e;
    }
  }

  @Transactional(readOnly = true)
  public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
    if (hashtag == null || hashtag.isBlank()) {
      return Page.empty(pageable);
    }

    return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
  }

  public List<String> getHashtags() {
    return articleRepository.findAllDistinctHashtags();
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