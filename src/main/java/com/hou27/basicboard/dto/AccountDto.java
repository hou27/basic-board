package com.hou27.basicboard.dto;

import com.hou27.basicboard.domain.Account;
import java.time.LocalDateTime;

public record AccountDto(
    Long userId,
    String userPassword,
    String email,
    String name,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

  public static AccountDto of(Long userId, String userPassword, String email, String name) {
    return new AccountDto(userId, userPassword, email, name, null, null);
  }

  public static AccountDto of(Long userId, String userPassword, String email, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    return new AccountDto(userId, userPassword, email, name, createdAt, modifiedAt);
  }

  public static AccountDto from(Account entity) {
    return new AccountDto(
        entity.getId(),
        entity.getPassword(),
        entity.getEmail(),
        entity.getName(),
        entity.getCreatedAt(),
        entity.getModifiedAt()
    );
  }

  public Account toEntity() {
    return Account.of(
        userPassword,
        email,
        name
    );
  }

}
