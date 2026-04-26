# Config Package

## Purpose
Holds application-wide technical configuration that is not tied to a specific domain feature.

## Classes
- `CorsConfig`

## Class Details
- `CorsConfig`: Implements `WebMvcConfigurer` and configures CORS for all paths (`/**`), allowing common HTTP methods and headers. This lets web and mobile clients call the API from different origins.
