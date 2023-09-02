package com.ms.magasinbe.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.magasinbe.common.enums.SystemStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Otp")
public class Otp extends BaseEntity implements Serializable {
  @Id
  @Column(name = "id", nullable = false, updatable = false, length = 36)
  private String id;

  @Column(name = "otp_key", length = 50)
  private String key;

  @Column(name = "otp", length = 50)
  private String otp;

  @Column(name = "expired_date")
  private long expiredDate;

  @Column(name = "status", length = 50)
  @Enumerated(EnumType.STRING)
  private SystemStatus status;
}
