package com.hou27.basicboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@Disabled("Data Rest Test는 사실상 불필요한 테스트이므로, 제외시킴")
@DisplayName("Data Rest Test")
@Transactional
//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {
  private final MockMvc mvc;

  public DataRestTest(@Autowired MockMvc mvc) {
    this.mvc = mvc;
  }

  @DisplayName("[api] GET /api/articles")
  @Test
  void getArticlesTest() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/hal+json"));
//        .andDo(print());
  }

  @DisplayName("[api] GET /articles/{id}")
  @Test
  void getArticleById() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articles/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/hal+json"));
  }

  @DisplayName("[api] GET /api/articles/{id}/comments")
  @Test
  void getCommentsInArticleTest() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articles/1/comments"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/hal+json"));
  }

  @DisplayName("[api] GET /api/comments")
  @Test
  void getCommentsTest() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/comments"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/hal+json"));
  }
}
