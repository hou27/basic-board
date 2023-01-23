package com.hou27.basicboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.Account;
import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.dto.AccountDto;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.dto.ArticleWithCommentsDto;
import com.hou27.basicboard.repository.AccountRepository;
import com.hou27.basicboard.repository.ArticleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DisplayName("Article Service Test")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

  // Mock을 주입받는 대상
  @InjectMocks
  private ArticleService sut; // system under test
  // 나머지는 Mock으로 생성
  @Mock
  private ArticleRepository articleRepository;
  @Mock
  private AccountRepository accountRepository;


  // 검색
  @Nested
  @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
  class searchTest {

    @DisplayName("검색어가 없는 경우엔 모든 게시글을 반환한다.")
    @Test
    void givenNothing_whenSearchingArticles_thenReturnsArticles() {
      // Given
      Pageable pageable = Pageable.ofSize(10);
      Page<Article> expected = Page.empty();
      given(articleRepository.findAll(pageable)).willReturn(expected);

      // When
      Page<ArticleDto> actual = sut.searchArticles(null, null, pageable);

      // Then
      assertThat(actual).isEqualTo(expected);
      then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어가 있는 경우엔 해당 게시글을 반환한다.")
    @Test
    void givenSearchTypeAndKeyword_whenSearchingArticles_thenReturnsArticles() {
      // Given
      SearchType searchType = SearchType.TITLE;
      String searchKeyword = "searchKeyword";
      Pageable pageable = Pageable.ofSize(10);
      Page<Article> expected = Page.empty();
      given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(expected);

      // When
      Page<ArticleDto> actual = sut.searchArticles(searchType, searchKeyword, pageable);

      // Then
//      assertThat(actual).isEmpty();
      assertThat(actual).isEqualTo(expected);
      then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }
  }

  @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
  @Test
  void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage() {
    // Given
    Pageable pageable = Pageable.ofSize(20);

    // When
    Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

    // Then
    assertThat(articles).isEqualTo(Page.empty(pageable));
    then(articleRepository).shouldHaveNoInteractions();
  }

  @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
  @Test
  void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnsArticlesPage() {
    // Given
    String hashtag = "#java";
    Pageable pageable = Pageable.ofSize(20);
    given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty(pageable));

    // When
    Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

    // Then
    assertThat(articles).isEqualTo(Page.empty(pageable));
    then(articleRepository).should().findByHashtag(hashtag, pageable);
  }


  // 게시글 상세 페이지로 이동
  @DisplayName("ID로 게시글을 조회하면, 게시글을 반환한다.")
  @Test
  void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
    // Given
    Long articleId = 1L;
    Article article = createArticle();
    given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

    // When
    ArticleWithCommentsDto articleWithComments = sut.getArticle(articleId);

    // Then
    assertThat(articleWithComments)
        .hasFieldOrPropertyWithValue("title", article.getTitle())
        .hasFieldOrPropertyWithValue("content", article.getContent())
        .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
    then(articleRepository).should().findById(articleId);
  }

  @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
  @Test
  void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
    // Given
    Article article = createArticle();
    ArticleDto articleDto = createArticleDto();
//    given(accountRepository.findById(any())).willReturn(Optional.of(article.getAccount()));
    given(accountRepository.getReferenceById(any())).willReturn(article.getAccount());
    given(articleRepository.save(any(Article.class))).willReturn(article);

    // When
    sut.saveArticle(articleDto);

    // Then
    then(accountRepository).should().getReferenceById(articleDto.accountDto().userId());
    then(articleRepository).should().save(any(Article.class));
  }

  @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
  @Test
  void givenModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
    // Given
    Article article = createArticle();
    ArticleDto articleDto = createArticleDto("updated title", "updated content", "#update");
    given(articleRepository.getReferenceById(articleDto.id())).willReturn(article);

    // When
    sut.updateArticle(articleDto);

    // Then
    assertThat(article)
        .hasFieldOrPropertyWithValue("title", articleDto.title())
        .hasFieldOrPropertyWithValue("content", articleDto.content())
        .hasFieldOrPropertyWithValue("hashtag", articleDto.hashtag());
    then(articleRepository).should().getReferenceById(articleDto.id());
  }

  @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
  @Test
  void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
    // Given
    Long articleId = 1L;
    willDoNothing().given(articleRepository).deleteById(any(Long.class));

    // When
    sut.deleteArticle(articleId);

    // Then
    then(articleRepository).should().deleteById(articleId);
  }

  private AccountDto createAccountDto() {
    return AccountDto.of(
        1L,
        LocalDateTime.now(),
        LocalDateTime.now(),
        "tester@mail.com",
        "hou27",
        "password"
    );
  }

  private Account createAccount() {
    return Account.of(
        "test@email.com",
        "hou27",
        "password"
    );
  }

  private Article createArticle() {
    return Article.of(
        "title",
        "content",
        createAccount(),
        "#java"
    );
  }

  private ArticleDto createArticleDto() {
    return createArticleDto("title", "content", "#java");
  }

  private ArticleDto createArticleDto(String title, String content, String hashtag) {
    return ArticleDto.of(
        1L,
        LocalDateTime.now(),
        LocalDateTime.now(),
        title,
        content,
        createAccountDto(),
        hashtag
    );
  }
}