package com.yildiz.serhat.favouriterecipesservice;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;


public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int POSTGRESQL_PORT = 5432;


    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer<>("postgres:14.4-alpine")
            .withUsername("sample-user")
            .withPassword("sample-strong-password")
            .withInitScript("schema.sql")
            .withDatabaseName("recipe-db")
            .withExposedPorts(POSTGRESQL_PORT)
            .withEnv("TZ", "UTC")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        // Start containers
        postgresqlContainer.start();
        // Override postgresql configuration
        String databaseHost = "DATABASE_URL=" + postgresqlContainer.getJdbcUrl();

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, databaseHost);
    }
}
