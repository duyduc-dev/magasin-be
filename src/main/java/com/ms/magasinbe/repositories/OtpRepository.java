package com.ms.magasinbe.repositories;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.entities.Otp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OtpRepository extends JpaRepository<Otp, String>, JpaSpecificationExecutor<Otp> {
  Otp findByKeyAndOtpAndStatus(String key, String otp, SystemStatus status);

  Otp findByKeyAndStatus(String key, SystemStatus status);

  @Query(value = """
     select p
     from Otp p
     where p.status = :#{#status} and p.key = :key
     order by p.createdDate desc
     limit 1
  """)
  Otp findKeyAndStatusLatest(
          @Param("key") String key,
          @Param("status") final SystemStatus systemStatus
  );

  @Query(value = """
     select p
     from Otp p
     where p.status = :#{#status} and p.key = :key and p.otp = :otp
     order by p.createdDate desc
     limit 1
  """)
  Otp findKeyAndOtpAndStatusLatest(
          @Param("key") String key,
          @Param("otp") String otp,
          @Param("status") final SystemStatus systemStatus
  );
}
