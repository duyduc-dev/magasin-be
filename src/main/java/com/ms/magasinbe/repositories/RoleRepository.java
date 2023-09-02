package com.ms.magasinbe.repositories;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
  Role findByRoleTypeAndStatus(UserRole userRole, SystemStatus systemStatus);
}
