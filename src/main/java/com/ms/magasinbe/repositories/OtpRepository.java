package com.ms.magasinbe.repositories;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OtpRepository extends JpaRepository<Otp, String>, JpaSpecificationExecutor<Otp> {
  Otp findByKeyAndOtpAndStatus(String key, String otp, SystemStatus status);

  Otp findByKeyAndStatus(String key, SystemStatus status);
}
