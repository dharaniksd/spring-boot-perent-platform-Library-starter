# Quick Start Guide

This guide shows you how to quickly add the Security Platform Starter to your existing Spring Boot microservices.

## For New Microservices

### Step 1: Use as Parent POM

In your microservice's `pom.xml`:

```xml
<parent>
    <groupId>com.security.platform</groupId>
    <artifactId>security-platform-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>
```

### Step 2: Add Your Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- Security Platform Starter is inherited from parent -->
</dependencies>
```

## For Existing Microservices (with existing parent)

### Step 1: Keep Your Parent, Add BOM

```xml
<parent>
    <groupId>your.existing</groupId>
    <artifactId>your-parent</artifactId>
    <version>1.0.0</version>
</parent>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.security.platform</groupId>
            <artifactId>security-platform-parent</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Step 2: Add Starter Dependency

```xml
<dependencies>
    <dependency>
        <groupId>com.security.platform</groupId>
        <artifactId>security-platform-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Installation

Before using in your microservices, build and install the starter:

```bash
cd security-platform-starter
mvn clean install
```

This installs the starter to your local Maven repository (~/.m2/repository).

## Configuration (Optional)

Add to your `application.properties`:

```properties
# Customize security features (all optional, these are defaults)
security.platform.enabled=true
security.platform.security-headers-enabled=true
security.platform.cors-enabled=false
security.platform.csrf-enabled=true
security.platform.actuator-security-enabled=true
security.platform.vulnerability-check-on-startup=true
```

## For REST APIs with JWT

If you have a REST API using JWT tokens (no sessions):

```properties
# Disable CSRF for stateless JWT APIs
security.platform.csrf-enabled=false
```

## Verify It's Working

Look for these log messages on startup:

```
Security Platform Starter initialized with secure defaults
Configuring security filter chain with secure defaults
Security headers enabled
CSRF protection enabled
Actuator security enabled
```

If you enabled vulnerability check:

```
=================================================
Security Platform Starter - Vulnerability Check
=================================================
Security Platform Enabled: true
Security Headers: true
CORS Enabled: false
CSRF Protection: true
Actuator Security: true
=================================================
```

## Common Scenarios

### Scenario 1: Web Application with Sessions
**Use default settings** - CSRF and security headers are enabled.

### Scenario 2: REST API with JWT
**Disable CSRF**:
```properties
security.platform.csrf-enabled=false
```

### Scenario 3: Public REST API
**Disable CSRF and customize authorization**:
```properties
security.platform.csrf-enabled=false
security.platform.actuator-security-enabled=false
```
Then implement your own authorization in your app.

### Scenario 4: Development Environment
**Enable CORS for frontend development**:
```properties
security.platform.cors-enabled=true
```

## Updating Vulnerability Fixes

When a new vulnerability is discovered:

1. **Maintainer updates** `pom.xml` in security-platform-parent
2. **Rebuild starter**: `mvn clean install`
3. **Rebuild your microservices** - they automatically get the fix
4. **No code changes needed** in your microservices!

## Testing

Test your application's security:

```bash
# Check security headers
curl -I http://localhost:8080/

# Check actuator security
curl http://localhost:8080/actuator/health  # Should work
curl http://localhost:8080/actuator/metrics  # Should require auth

# Run vulnerability scan
mvn dependency-check:check
```

## Troubleshooting

### Problem: Security not applied
**Solution**: Verify the starter is in your dependencies:
```bash
mvn dependency:tree | grep security-platform-starter
```

### Problem: CSRF blocking requests
**Solution**: For REST APIs, disable CSRF:
```properties
security.platform.csrf-enabled=false
```

### Problem: CORS errors in browser
**Solution**: Enable CORS for development:
```properties
security.platform.cors-enabled=true
```

## Next Steps

- Read the full [README.md](README.md) for detailed information
- Check [SECURITY.md](SECURITY.md) for security considerations
- See [example-app](example-app/) for a working example

## Support

For questions or issues, open an issue on GitHub or contact the maintainers.
