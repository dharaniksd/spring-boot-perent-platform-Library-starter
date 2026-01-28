# Spring Boot Security Platform Starter

A centralized Spring Boot starter library for managing security vulnerabilities across multiple microservice applications.

## Quick Links

- **[Quick Start Guide](QUICK_START.md)** - Get started in 5 minutes
- **[Security Documentation](SECURITY.md)** - Security features and best practices
- **[Example Application](example-app/)** - Working example

## Overview

This starter library provides a single place to manage and fix security vulnerabilities that affect all your Spring Boot microservices. By using this starter, you can:

- Fix vulnerabilities in one place and apply them to all applications
- Enforce secure dependency versions across all microservices
- Apply security best practices automatically
- Configure security headers, CORS, CSRF protection
- Monitor security configuration on startup

## Features

- **Centralized Dependency Management**: All secure dependency versions are managed in the parent POM
- **Auto-Configuration**: Automatic security configuration with sensible defaults
- **Security Headers**: Automatic configuration of security headers (HSTS, X-Frame-Options, CSP, etc.)
- **OWASP Dependency Check**: Built-in OWASP dependency check plugin configuration
- **Customizable**: All security features can be enabled/disabled via properties
- **Actuator Security**: Secure actuator endpoints by default
- **Vulnerability Monitoring**: Optional startup logging of security configuration

## Usage

### 1. Add the Starter to Your Microservice

Add the following parent to your microservice's `pom.xml`:

```xml
<parent>
    <groupId>com.security.platform</groupId>
    <artifactId>security-platform-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>
```

Or if you already have a parent, add the starter as a dependency:

```xml
<dependency>
    <groupId>com.security.platform</groupId>
    <artifactId>security-platform-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. Build and Install the Starter Locally

First, build and install the starter library to your local Maven repository:

```bash
mvn clean install
```

### 3. Configure Your Application (Optional)

Add these properties to your `application.properties` or `application.yml` to customize the security configuration:

```properties
# Enable/disable the security platform starter (default: true)
security.platform.enabled=true

# Enable security headers (default: true)
security.platform.security-headers-enabled=true

# Enable CORS (default: false)
security.platform.cors-enabled=false

# Enable CSRF protection (default: true)
security.platform.csrf-enabled=true

# Enable actuator security (default: true)
security.platform.actuator-security-enabled=true

# Enable vulnerability check logging on startup (default: false)
security.platform.vulnerability-check-on-startup=true
```

## Vulnerability Management

### How It Works

1. **Dependency Version Management**: The parent POM declares secure versions of common dependencies that have known vulnerabilities (e.g., SnakeYAML, Jackson, Tomcat, Logback)

2. **OWASP Dependency Check**: The parent POM includes the OWASP dependency check plugin that scans for known vulnerabilities

3. **Auto-Configuration**: The starter automatically configures Spring Security with best practices

### Running Vulnerability Checks

To check for vulnerabilities in your application:

```bash
mvn dependency-check:check
```

This will generate a report in `target/dependency-check-report.html`

### Fixing Vulnerabilities

When a new vulnerability is discovered:

1. Update the version in the parent POM's `<properties>` section
2. Rebuild and reinstall the starter: `mvn clean install`
3. Update your microservices to use the new starter version
4. The fix is now applied to all microservices

## Project Structure

```
.
├── pom.xml                          # Parent POM with dependency management
├── owasp-suppressions.xml           # OWASP suppressions for false positives
├── security-platform-starter/       # Starter module
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/security/platform/starter/
│       │   ├── SecurityPlatformAutoConfiguration.java
│       │   ├── SecurityPlatformProperties.java
│       │   └── VulnerabilityMonitor.java
│       └── resources/
│           ├── META-INF/spring.factories
│           └── application.properties
└── README.md
```

## Security Features

### 1. Security Headers

When enabled, the following security headers are automatically configured:

- **Content-Security-Policy**: Prevents XSS attacks
- **X-Frame-Options**: Prevents clickjacking
- **X-XSS-Protection**: Enables browser XSS protection
- **Strict-Transport-Security**: Enforces HTTPS
- **Referrer-Policy**: Controls referrer information

### 2. CORS Configuration

When enabled, CORS is configured with secure defaults:
- Allowed origins: `http://localhost:*`
- Allowed methods: GET, POST, PUT, DELETE, OPTIONS
- Credentials: Enabled
- Max age: 3600 seconds

### 3. CSRF Protection

CSRF protection is enabled by default with exceptions for actuator endpoints.

### 4. Actuator Security

Actuator endpoints are secured by default:
- `/actuator/health` and `/actuator/info` are publicly accessible
- All other actuator endpoints require authentication

## Managing Secure Dependency Versions

The parent POM manages the following dependency versions:

- **Spring Boot**: 3.2.2
- **Spring Security**: 6.2.1
- **SnakeYAML**: 2.2
- **Jackson**: 2.16.1
- **Tomcat**: 10.1.18
- **Logback**: 1.4.14

To update a dependency version:

1. Edit the `<properties>` section in `pom.xml`
2. Run `mvn clean install`
3. Update your microservices to use the new version

## Example: Adding to a Microservice

Here's a complete example of adding this starter to an existing Spring Boot microservice:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.security.platform</groupId>
        <artifactId>security-platform-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>my-microservice</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Security Platform Starter is inherited from parent -->
    </dependencies>
</project>
```

## Benefits

1. **Single Point of Management**: Fix vulnerabilities once, apply to all microservices
2. **Consistency**: All microservices use the same secure dependency versions
3. **Easy Updates**: Update versions in one place and rebuild
4. **Best Practices**: Security best practices are enforced automatically
5. **Monitoring**: Optional vulnerability monitoring on startup
6. **Flexibility**: All features can be customized or disabled

## Contributing

To contribute to this starter library:

1. Make changes to the starter code
2. Test your changes locally with `mvn clean install`
3. Update the version in `pom.xml`
4. Commit and push your changes

## License

[Add your license here]

## Support

For issues or questions, please [open an issue](https://github.com/dharaniksd/spring-boot-perent-platform-Library-starter/issues) on GitHub.