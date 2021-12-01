package com.example.test.app.configuration.datasource;

import com.example.test.app.configuration.web.ThreadTenantStorage;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {


    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = ThreadTenantStorage.getTenantId();
        if(t!=null){
            return t;
        } else {
            return TenantConnectionProvider.DEFAULT_TENANT;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
