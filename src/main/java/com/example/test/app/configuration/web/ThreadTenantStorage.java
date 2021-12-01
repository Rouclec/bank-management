package com.example.test.app.configuration.web;

import com.example.test.app.configuration.datasource.TenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadTenantStorage {

    TenantConnectionProvider tenantConnectionProvider;

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId);
        System.out.println("Storing Thread tenant Id for " + tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }
}
