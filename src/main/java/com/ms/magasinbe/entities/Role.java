package com.ms.magasinbe.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
@Entity
@Builder
public class Role extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "role_name", length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole roleType;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private SystemStatus status;


}
