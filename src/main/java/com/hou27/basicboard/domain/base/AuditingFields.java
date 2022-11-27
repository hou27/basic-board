package com.hou27.basicboard.domain.base;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @ToString
/**
 * JPA Entity 클래스들이 CoreEntity 추상 클래스를 상속할 경우
 * createdAt, modifiedAt을 컬럼으로 인식하도록 해줌
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditingFields {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // LocalDateTime을 JSON으로 변환할 때 ISO 8601 포맷으로 변환
  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime modifiedAt;
}
