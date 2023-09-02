package com.ms.magasinbe.controllers;

import com.ms.magasinbe.common.utils.RestAPIResponse;
import com.ms.magasinbe.configs.security.AuthorizeValidator;
import com.ms.magasinbe.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.ROLE_API)
public class RoleController extends AbstractBaseController {

  @Autowired
  RoleService roleService;

//  @AuthorizeValidator
  @PostMapping(ApiPath.ADD)
  public ResponseEntity<RestAPIResponse<Object>> createRoleDefault() {
    roleService.createRolesDefault();
    return responseUtil.successResponse("ok");
  }

}
