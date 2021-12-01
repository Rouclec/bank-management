package com.example.test.app.service;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TenantService {

    private DataSource dataSource;

    public TenantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initDatabase(String schema) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration")
                .dataSource(dataSource)
                .schemas(schema)
                .load();
        flyway.migrate();
    }
}