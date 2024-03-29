package com.hou27.basicboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hou27.basicboard.config.JpaConfig;
import com.hou27.basicboard.domain.Account;
import com.hou27.basicboard.domain.Article;
import com.hou27.basicboard.domain.Comment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("JpaRepository Test")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
  // 생성자 주입 패턴을 통한 필드 생성
  private final ArticleRepository articleRepository;
  private final ArticleCommentRepository commentRepository;
  private final AccountRepository accountRepository;

  public JpaRepositoryTest(
      @Autowired ArticleRepository articleRepository,
      @Autowired ArticleCommentRepository commentRepository,
      @Autowired AccountRepository accountRepository
  ) {
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
    this.accountRepository = accountRepository;
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
        .hasSize(200);
  }

  @DisplayName("insert Test")
  @Test
  void insert() {
    // Given
    long previousCount = articleRepository.count();
    Account account = accountRepository.save(Account.of("test@gmail.com", "hou27", "pw"));
    Article article = Article.of("title", "new article", account, "#spring");

    // When
    articleRepository.save(Article.of("new article", "new content", account, "#spring"));

    // Then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
  }

  @DisplayName("update Test")
  @Test
  void update() {
    // Given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updatedTag = "#springboot";
    article.setHashtag(updatedTag);

    // When
    // saveAndFlush: DB에 반영(Transactional이기 때문에 그냥 save를 하면 Update 쿼리는 생략되므로 flush를 해줘야함)
    Article savedArticle = articleRepository.saveAndFlush(article);

    // Then
    assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedTag);
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