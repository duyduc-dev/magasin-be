package com.ms.magasinbe.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.magasinbe.common.enums.GenderType;
import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.TypeVerifyAccount;
import com.ms.magasinbe.common.enums.UserRole;
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
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "password_hash")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @Column(name = "password_salt", length = 36)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordSalt;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private SystemStatus status;

    @Column(name = "gender", length = 50)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "role_id", length = 36)
    private String roleId;

    @Column(name = "otp_code", length = 10)
    private String otpCode;

    @Column(name = "otp_expiry_date")
    private long otpExpiryDate;

    @Column (name = "date_of_birth")
    private Long dateOfBirth;

    @Column(name ="middle_name", length = 50)
    private String middleName;

    @Column(name = "street_address", columnDefinition = "TEXT", length = 2000)
    private String streetAddress;

    @Column(name = "user_role", length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "active_code", length = 50)
    private String activeCode;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "is_2_Fa", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean is2Fa;

}
