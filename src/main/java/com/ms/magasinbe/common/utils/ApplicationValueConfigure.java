package com.ms.magasinbe.common.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationValueConfigure {

    @Value(value = "${jwt.expiration}")
    public long JWT_EXPIRATION;

    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${app.name}")
    public String APP_NAME;

    @Value("${app.api.version}")
    public String APP_API_VERSION;
}
