# Config Package

## Purpose
Holds application-wide technical configuration: CORS policy, Spring Security setup, and JWT token handling.

## Classes
- `CorsConfig`
- `SecurityConfig`
- `JwtUtil`
- `JwtAuthFilter`

## Class Details
- `CorsConfig`: Implements `WebMvcConfigurer` to allow all origins, methods, and headers. Also exposes a `CorsConfigurationSource` bean so Spring Security's filter uses the same CORS rules as MVC.
- `SecurityConfig`: Declares the Spring Security filter chain. Disables CSRF (stateless API), sets session policy to `STATELESS`, permits `/auth/**` and Swagger paths without a token, requires authentication on all other routes, and registers a `BCryptPasswordEncoder` bean.
- `JwtUtil`: Generates signed HS512 JWT tokens (subject = userId) and validates/parses them. Reads the secret and expiration from `application.properties`.
- `JwtAuthFilter`: `OncePerRequestFilter` that extracts the `Authorization: Bearer <token>` header, validates the token with `JwtUtil`, and sets the authenticated `userId` as the principal in the `SecurityContext` for the duration of the request.
