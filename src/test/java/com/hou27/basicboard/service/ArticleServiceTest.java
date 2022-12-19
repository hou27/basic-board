package com.hou27.basicboard.service;

import static org.assertj.core.api.Assertions.*;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

  // Mock을 주입받는 대상
  @InjectMocks private ArticleService sut; // system under test
  // 나머지는 Mock으로 생성
  @Mock private ArticleRepository articleRepository;


  // 검색
  @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
  @Test
  void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
    // Given

    // When
    Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

    // Then
    assertThat(articles).isNotNull();
  }


  // 게시글 상세 페이지로 이동
  @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
  @Test
  void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
    // Given

    // When
    ArticleDto articles = sut.searchArticle(1L);

    // Then
    assertThat(articles).isNotNull();
  }
}