package com.ms.magasinbe.services.role;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.entities.Role;

import java.util.List;

public interface RoleService {

  List<Role> createRolesDefault();

  Role createRole(UserRole userRole);
}
