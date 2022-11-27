package com.hou27.basicboard.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hou27.basicboard.config.JpaConfig;
import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.Comment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("JpaRepository Test")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
  // 생성자 주입 패턴을 통한 필드 생성
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;

  public JpaRepositoryTest(
      @Autowired ArticleRepository articleRepository,
      @Autowired CommentRepository commentRepository
  ) {
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
  }

  @DisplayName("select Test")
  @Test
  void select() {
    // Given

    // When
    List<Article> articles = articleRepository.findAll();
    List<Comment> comments = commentRepository.findAll();

    // Then
    assertThat(articles)
        .isNotNull()
        .hasSize(100);
    assertThat(comments)
        .isNotNull()
        .hasSize(100);
  }

  @DisplayName("insert Test")
  @Test
  void insert() {
    // Given
    long previousCount = articleRepository.count();

    // When
    articleRepository.save(Article.of("new article", "new content", "#spring"));

    // Then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
  }

  @DisplayName("update Test")
  @Test
  void update() {
    // Given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updatedTag = "#springboot";
    article.setTag(updatedTag);

    // When
    // saveAndFlush: DB에 반영(Transactional이기 때문에 그냥 save를 하면 Update 쿼리는 생략되므로 flush를 해줘야함)
    Article savedArticle = articleRepository.saveAndFlush(article);

    // Then
    assertThat(savedArticle).hasFieldOrPropertyWithValue("tag", updatedTag);
  }

  @DisplayName("delete Test")
  @Test
  void delete() {
    // Given
    Article article = articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count();
    long previousArticleCommentCount = commentRepository.count();
    int deletedCommentsSize = article.getComments().size();

    // When
    articleRepository.delete(article);

    // Then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
    assertThat(commentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
  }
}