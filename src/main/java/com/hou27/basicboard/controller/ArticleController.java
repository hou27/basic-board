package com.hou27.basicboard.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/articles")
@Controller
public class ArticleController {
  @GetMapping
  public String articles(Model map) {
    map.addAttribute("articles", List.of());

    return "articles/index";
  }

  @GetMapping("/{articleId}")
  public String articleDetail(@PathVariable Long articleId, Model map) {
    map.addAttribute("article", "dummy"); // TODO: 추후 실제 데이터를 넣어줘야 함
    map.addAttribute("comments", List.of());

    return "articles/detail";
  }
}
