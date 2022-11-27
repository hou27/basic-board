package com.hou27.basicboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration // configuration bean
public class JpaConfig {
  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> null; // 추후 인증 기능 구현 후 수정해야함.
  }
}
