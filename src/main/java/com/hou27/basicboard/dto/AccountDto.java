package com.hou27.basicboard.dto;

import com.hou27.basicboard.domain.Account;
import java.time.LocalDateTime;

public record AccountDto(
    Long userId,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String email,
    String name,
    String userPassword
) {

  public static AccountDto of(Long userId, String email, String name, String userPassword) {
    return new AccountDto(userId, null, null, email, name, userPassword);
  }

  public static AccountDto of(Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt, String email, String name, String userPassword) {
    return new AccountDto(userId, createdAt, modifiedAt, email, name, userPassword);
  }

  public static AccountDto from(Account entity) {
    return new AccountDto(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getModifiedAt(),
        entity.getEmail(),
        entity.getName(),
        entity.getPassword()
    );
  }

  public Account toEntity() {
    return Account.of(
        email,
        name,
        userPassword
    );
  }

}
