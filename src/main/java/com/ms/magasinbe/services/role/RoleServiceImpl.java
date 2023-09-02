package com.ms.magasinbe.services.role;

import com.ms.magasinbe.common.enums.SystemStatus;
import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.common.utils.UniqueID;
import com.ms.magasinbe.entities.Role;
import com.ms.magasinbe.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleRepository roleRepository;

  /**
   *
   */
  @Override
  public List<Role> createRolesDefault() {
    List<Role> roles = new ArrayList<>();
    roles.add(createRole(UserRole.ADMIN));
    roles.add(createRole(UserRole.CUSTOMER));
    roles.add(createRole(UserRole.SELLER));
    this.roleRepository.saveAll(roles);
    return roles;
  }

  /**
   * @param userRole
   * @return
   */
  @Override
  public Role createRole(UserRole userRole) {
    return Role.builder()
            .id(UniqueID.getUUID())
            .roleType(userRole)
            .name(userRole.name())
            .status(SystemStatus.ACTIVE)
            .build();
  }
}
