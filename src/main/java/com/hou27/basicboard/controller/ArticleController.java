package com.hou27.basicboard.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/articles")
@Controller
public class ArticleController {
  @GetMapping
  public String articles(Model map) {
    map.addAttribute("articles", List.of());

    return "articles/index";
  }
}
