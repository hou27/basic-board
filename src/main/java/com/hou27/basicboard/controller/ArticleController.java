package com.hou27.basicboard.controller;

import com.hou27.basicboard.domain.type.SearchType;
import com.hou27.basicboard.dto.AccountDto;
import com.hou27.basicboard.dto.res.ArticleResponse;
import com.hou27.basicboard.dto.res.ArticleWithCommentsResponse;
import com.hou27.basicboard.service.ArticleService;
import com.hou27.basicboard.service.PaginationService;
import com.hou27.basicboard.dto.req.ArticleRequest;
import com.hou27.basicboard.domain.constant.FormStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

  private final ArticleService articleService;
  private final PaginationService paginationService;

  @GetMapping
  public String articles(
      @RequestParam(required = false) SearchType searchType,
      @RequestParam(required = false) String searchValue,
      @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
      Model map
  ) {
    Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
    List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

    map.addAttribute("articles", articles);
    map.addAttribute("paginationBarNumbers", barNumbers);
    map.addAttribute("searchTypes", SearchType.values());

    return "articles/index";
  }

  @GetMapping("/{articleId}")
  public String articleDetail(
      @PathVariable Long articleId,
      Model map
  ) {
    ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(
        articleService.getArticleWithComments(articleId));
    map.addAttribute("article", article);
    map.addAttribute("comments", article.commentsResponse());

    return "articles/detail";
  }

  @GetMapping("/search-by-hashtag")
  public String searchByHashtag(
      @RequestParam(required = false) String searchValue,
      @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
      Model map
  ) {
    Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
    List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
    List<String> hashtags = articleService.getHashtags();

    map.addAttribute("articles", articles);
    map.addAttribute("hashtags", hashtags);
    map.addAttribute("paginationBarNumbers", barNumbers);
    map.addAttribute("searchType", SearchType.HASHTAG);

    return "articles/search-by-hashtag";
  }

  @GetMapping("/form")
  public String articleForm(Model map) {
    map.addAttribute("formStatus", FormStatus.CREATE);

    return "articles/form";
  }

  @PostMapping ("/form")
  public String postNewArticle(ArticleRequest articleRequest) {
    // TODO: 인증 정보를 넣어줘야 한다.
    articleService.saveArticle(articleRequest.toDto(AccountDto.of(
        1L, "uno@mail.com", "hou27", "passw0rd"
    )));

    return "redirect:/articles";
  }

  @GetMapping("/{articleId}/form")
  public String updateArticleForm(@PathVariable Long articleId, Model map) {
    ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

    map.addAttribute("article", article);
    map.addAttribute("formStatus", FormStatus.UPDATE);

    return "articles/form";
  }

  @PostMapping("/{articleId}/form")
  public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
    // TODO: 인증 정보를 넣어줘야 한다.
    articleService.updateArticle(articleRequest.toDto(AccountDto.of(
        1L, "uno@mail.com", "hou27", "passw0rd"
    )));

    return "redirect:/articles/" + articleId;
  }

  @PostMapping ("/{articleId}/delete")
  public String deleteArticle(@PathVariable Long articleId) {
    // TODO: 인증 정보를 넣어줘야 한다.
    articleService.deleteArticle(articleId);

    return "redirect:/articles";
  }
}
