package OracleTest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "OracleTest") // ✅ يشمل كل الحزم
public class DatabaseTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatabaseTestApplication.class, args);
    }
}