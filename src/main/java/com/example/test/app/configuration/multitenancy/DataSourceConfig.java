package com.example.test.app.configuration.multitenancy;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    public String tenantID = TenantContext.getCurrentTenant();
    @Bean
    public DataSource getDataSource() {
        if(tenantID == null){
            tenantID = "public";
        }
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/" + tenantID + "?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false");
        dataSourceBuilder.username("rouclec");
        dataSourceBuilder.password("@admin123");
        return dataSourceBuilder.build();
    }
}
