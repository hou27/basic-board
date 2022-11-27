package com.hou27.basicboard.repository;

import com.hou27.basicboard.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
