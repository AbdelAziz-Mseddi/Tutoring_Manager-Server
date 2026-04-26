# Server Package Documentation

This package contains the complete backend API implementation for Tutoring Manager.

## Package Flow

1. Controllers receive HTTP requests.
2. Services execute business logic.
3. Repositories access the database.
4. Entities map Java objects to tables.
5. DTOs define request/response payloads.
6. Config and exception packages provide cross-cutting behavior.

## Decorator Reference

These annotations appear throughout the server package and are explained once here to avoid repetition:

- `@SpringBootApplication`: marks the main class as a Spring Boot app and enables auto-configuration and component scanning.
- `@OpenAPIDefinition` and `@Info`: add Swagger/OpenAPI metadata for the API documentation.
- `@Configuration`: declares a configuration class managed by Spring.
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
- `controller`: HTTP endpoints.
- `service`: business rules.
- `repository`: database access.
- `entity`: table mappings.
- `dto`: API request/response models.
- `exception`: error formatting and handling.
