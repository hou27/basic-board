package com.hou27.basicboard.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hou27.basicboard.config.SecurityConfig;
import com.hou27.basicboard.domain.constant.SearchType;
import com.hou27.basicboard.dto.AccountDto;
import com.hou27.basicboard.dto.ArticleDto;
import com.hou27.basicboard.service.ArticleService;
import com.hou27.basicboard.service.PaginationService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("View - Article Controller Test")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

  private final MockMvc mvc;

  @MockBean
  private ArticleService articleService;

  @MockBean
  private PaginationService paginationService;

  public ArticleControllerTest(@Autowired MockMvc mvc) {
    this.mvc = mvc;
  }

  @DisplayName("[view] GET 게시글 리스트 - 정상 호출되어야 한다.")
  @Test
  public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
    // given
    given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(
        Page.empty());
    given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(
        List.of(1, 2, 3, 4, 5));

    // when & then
    mvc.perform(get("/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/index"))
        .andExpect(model().attributeExists("articles"))
        .andExpect(model().attributeExists("paginationBarNumbers"))
        .andExpect(model().attributeExists("searchTypes"));
    then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
  }

  @DisplayName("[view] GET 게시글 상세 페이지 - 정상 호출되어야 한다.")
  @Test
  public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
    // Given
    Long articleId = 1L;
    given(articleService.getArticle(articleId)).willReturn(createArticleDto());

    // When & Then
    mvc.perform(get("/articles/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/detail"))
        .andExpect(model().attributeExists("article"))
        .andExpect(model().attributeExists("comments"));
    then(articleService).should().getArticle(articleId);
  }

  @DisplayName("[view] GET 게시글 검색 페이지 - 정상 호출되어야 한다.")
  @Test
  public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView()
      throws Exception {
    // Given
    SearchType searchType = SearchType.TITLE;
    String searchValue = "title";
    given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
    given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

    // When & Then
    mvc.perform(
            get("/articles")
                .queryParam("searchType", searchType.name())
                .queryParam("searchValue", searchValue)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/index"))
        .andExpect(model().attributeExists("articles"))
        .andExpect(model().attributeExists("searchTypes"));
    then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
    then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
  }

  @Disabled("구현 중")
  @DisplayName("[view] GET 게시글 태그 검색 페이지 - 정상 호출되어야 한다.")
  @Test
  public void givenNothing_whenRequestingArticleTagSearchView_thenReturnsArticleTagSearchView()
      throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/articles/search-tag"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(model().attributeExists("articles/search-tag"));
  }

  private ArticleDto createArticleDto() {
    return ArticleDto.of(
        1L,
        LocalDateTime.now(),
        LocalDateTime.now(),
        "title",
        "content",
        createAccountDto(),
        "#java"
    );
  }

  private AccountDto createAccountDto() {
    return AccountDto.of(
        1L,
        "test@mail.com",
        "hou27",
        "password"
    );
  }
}