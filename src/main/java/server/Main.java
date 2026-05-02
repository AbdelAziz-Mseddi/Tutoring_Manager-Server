package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Tutoring Manager API",
                version = "1.1.1",
                description = "REST API for the Tutoring Manager application - manage users, students, classes, enrollments, sessions, and payments"
        )
)
// Application entry point for the tutoring manager REST API.
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}