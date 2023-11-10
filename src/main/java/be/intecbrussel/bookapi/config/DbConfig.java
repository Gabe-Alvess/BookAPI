package be.intecbrussel.bookapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbConfig {

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    public DbConfig() {
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
