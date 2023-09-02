package com.ms.magasinbe.controllers;

import com.ms.magasinbe.common.exceptions.ApplicationException;
import com.ms.magasinbe.common.utils.ApplicationValueConfigure;
import com.ms.magasinbe.common.utils.ResponseUtil;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBaseController {
  @Autowired
  ResponseUtil responseUtil;
  @Autowired
  ApplicationValueConfigure applicationValueConfigure;

  public void validatePaging(int pageNumber, int pageSize) {
    if (pageNumber < 1 || pageSize < 1) {
      throw new ApplicationException(RestAPIStatus.BAD_PARAMS, "Invalid paging request");
    }
  }
}
