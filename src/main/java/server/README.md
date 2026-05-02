# Server Package Documentation

This package contains the complete backend API implementation for Tutoring Manager.

## Package Flow

1. `JwtAuthFilter` validates the Bearer token and sets the authenticated `userId` in the security context.
2. Controllers receive HTTP requests and read `userId` from the security context.
3. Services execute business logic and enforce ownership.
4. Repositories access the database.
5. Entities map Java objects to tables.
6. DTOs define request/response payloads.
7. Config and exception packages provide cross-cutting behavior.

## Decorator Reference

These annotations appear throughout the server package and are explained once here to avoid repetition:

- `@SpringBootApplication`: marks the main class as a Spring Boot app and enables auto-configuration and component scanning.
- `@OpenAPIDefinition` and `@Info`: add Swagger/OpenAPI metadata for the API documentation.
- `@Configuration`: declares a configuration class managed by Spring.
- `@EnableWebSecurity`: activates Spring Security configuration.
- `@Bean`: declares a Spring-managed bean from a method return value.
- `@Value`: injects a value from `application.properties`.
- `@Component`: marks a general-purpose Spring-managed component (used for filters and utilities).
- `@RestController`: combines `@Controller` and `@ResponseBody` for JSON API endpoints.
- `@RequestMapping`: sets a base URL path for all methods in a controller.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`: map methods to HTTP verbs.
- `@RequestParam`: reads query parameters from the URL.
- `@PathVariable`: reads values from the URL path.
- `@RequestBody`: converts JSON request bodies into Java objects.
- `@Valid`: triggers Bean Validation on incoming DTOs.
- `@Service`: marks a business-layer Spring bean.
- `@Repository`: marks a persistence-layer Spring bean.
- `@Autowired`: injects a Spring-managed dependency.
- `@Entity`: marks a JPA entity.
- `@Table`: sets the exact database table name.
- `@Id`: marks the primary key field.
- `@GeneratedValue`: tells JPA how the primary key is generated.
- `@Column`: configures the database column mapping and constraints.
- `@ManyToOne`: defines a many-to-one JPA relationship.
- `@JoinColumn`: maps a relationship to a foreign key column.
- `@CreationTimestamp`: auto-fills a timestamp when the row is inserted.
- `@PrePersist`: runs just before the entity is first saved.
- `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`: Lombok annotations that generate boilerplate code.
- `@NotBlank`, `@NotNull`, `@Email`, `@DecimalMin`, `@Min`: Bean Validation annotations used to enforce input rules.
- `@RestControllerAdvice`: applies exception handling globally to all controllers.
- `@ExceptionHandler`: maps a specific exception type to an HTTP response.
- `@Override`: standard Java annotation confirming a method overrides a parent/interface method.

## Package Summary

- `Main`: application entry point.
- `config/CorsConfig`: global CORS setup.
- `config/SecurityConfig`: Spring Security filter chain — token authentication, route permissions, password encoder.
- `config/JwtUtil`: JWT token generation and validation.
- `config/JwtAuthFilter`: per-request filter that reads and validates the Bearer token.
- `controller/AuthController`: `/auth/register` and `/auth/login` endpoints.
- `controller/*Controller`: HTTP endpoints for domain resources.
- `service/AuthService`: registration and login logic.
- `service/*Service`: business rules and ownership enforcement.
- `repository`: database access.
- `entity`: table mappings (including `User`).
- `dto`: API request/response models (including auth DTOs).
- `exception`: error formatting and handling (`UnauthorizedException`, `ForbiddenException`, `ConflictException`).
