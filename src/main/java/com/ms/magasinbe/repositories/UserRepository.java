package com.ms.magasinbe.repositories;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
  User findFirstByEmailAndStatus(String email, SystemStatus systemStatus);

  User findFirstByPhoneNumberAndStatus(String phone, SystemStatus systemStatus);

  User findByIdAndStatus(String id, SystemStatus systemStatus);

  User findByUsernameAndStatus(String username, SystemStatus status);
}
