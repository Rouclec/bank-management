package com.example.test.app.configuration.datasource;



import com.example.test.app.configuration.web.ThreadTenantStorage;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("finding lookup key for " + ThreadTenantStorage.getTenantId());
        return ThreadTenantStorage.getTenantId();
    }
}