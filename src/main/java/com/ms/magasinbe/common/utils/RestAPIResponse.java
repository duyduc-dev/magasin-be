package com.ms.magasinbe.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestAPIResponse<T extends Object>  implements Serializable {
    private int status;
    private String message;
    private T data;
    private String description;

    public RestAPIResponse(RestAPIStatus restApiStatus, T data) {

        if (restApiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = restApiStatus.getCode();
        this.message = restApiStatus.getDescription();
        this.data = data;
        this.description = "";
    }

    public RestAPIResponse(RestAPIStatus restApiStatus, T data, String description) {

        if (restApiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = restApiStatus.getCode();
        this.message = restApiStatus.getDescription();
        this.data = data;
        this.description = description;
    }
}

