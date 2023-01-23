package com.hou27.basicboard.domain;

import com.hou27.basicboard.domain.base.AuditingFields;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
    @Index(columnList = "createdAt"),
    @Index(columnList = "email", unique = true)
})
@Entity
public class Account extends AuditingFields {
  @Setter
  @Column(length = 100)
  private String email;

  @Setter
  @Column(length = 100, nullable = false)
  private String name;

  @Setter
  @Column(nullable = false)
  private String password;


  protected Account() {}

  private Account(String email, String name, String password) {
    this.password = password;
    this.email = email;
    this.name = name;
  }

  public static Account of(String email, String name, String password) {
    return new Account(email, name, password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Account userAccount)) return false;
    return this.getId() != null && this.getId().equals(userAccount.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

}
