package com.hou27.basicboard.domain;

import com.hou27.basicboard.domain.base.AuditingFields;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@ToString
@Table(indexes = {
    @Index(columnList = "createdAt"),
    @Index(columnList = "title")
})
@Entity
public class Article extends AuditingFields {
  @Setter
  @Column(nullable = false)
  private String title;

  @Setter
  @Column(nullable = false, length = 10000)
  private String content;

  @Setter
  private String tag;

  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  @Exclude // do not include this field in the generated toString.
  private final Set<Comment> comments = new LinkedHashSet<>();

  /**
   * Constructor
   */
  protected Article() {
  }

  private Article(String title, String content, String tag) {
    this.title = title;
    this.content = content;
    this.tag = tag;
  }

  public static Article of(String title, String content, String tag) {
    return new Article(title, content, tag);
  }

  /**
   * equals, hashCode
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    // pattern variable로 코드 개선
//    if (!(o instanceof Article)) {
//      return false;
//    }
//    Article article = (Article) o;
    if (!(o instanceof Article article)) {
      return false;
    }
    
    return this.getId() != null && this.getId().equals(article.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }
}
