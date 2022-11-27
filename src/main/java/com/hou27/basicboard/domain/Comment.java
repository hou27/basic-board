package com.hou27.basicboard.domain;

import com.hou27.basicboard.domain.base.AuditingFields;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(indexes = {
    @Index(columnList = "createdAt"),
    @Index(columnList = "content")
})
@Entity
public class Comment extends AuditingFields {
  @Setter
  @Column(nullable = false, length = 500)
  private String content;

  @Setter
  @ManyToOne(optional = false) // no cascade
  private Article article;

  /**
   * Constructor
   */
//  protected Comment() {
//  }

  private Comment(String content, Article article) {
    this.content = content;
    this.article = article;
  }

  public static Comment of(String content, Article article) {
    return new Comment(content, article);
  }

  /**
   * equals, hashCode
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Comment comment)) {
      return false;
    }

    return this.getId() != null && this.getId().equals(comment.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }
}
