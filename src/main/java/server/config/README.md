# Config Package

## Purpose
Holds application-wide technical configuration that is not tied to a specific domain feature.

## Classes
- `CorsConfig`

## Class Details
- `CorsConfig`: Uses `@Configuration` to register a Spring configuration class and implements `WebMvcConfigurer` to configure CORS for all paths (`/**`), allowing common HTTP methods and headers. This lets web and mobile clients call the API from different origins.
