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
@ToString(callSuper = true)
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

  @Setter
  @ManyToOne(optional = false)
  private Account account;

  /**
   * Constructor
   */
//  protected Comment() {
//  }

  private Comment(String content, Article article, Account account) {
    this.content = content;
    this.article = article;
    this.account = account;
  }

  public static Comment of(Article article, Account account, String content) {
    return new Comment(content, article, account);
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
