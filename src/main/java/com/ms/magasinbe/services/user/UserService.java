package com.ms.magasinbe.services.user;

import com.ms.magasinbe.common.enums.UserRole;
import com.ms.magasinbe.controllers.modals.request.SignupRequest;
import com.ms.magasinbe.entities.User;

public interface UserService {

  User createUser(SignupRequest signupRequest, UserRole userRole);

  User getUserById(String id);

}
