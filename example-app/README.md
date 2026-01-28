# Example Application

This is an example Spring Boot application demonstrating how to use the Security Platform Starter.

## Features

- Uses Security Platform Starter for vulnerability management
- Demonstrates automatic security configuration
- Shows how parent POM provides centralized dependency management
- Includes REST endpoints to test the application

## Building

```bash
cd example-app
mvn clean package
```

## Running

```bash
mvn spring-boot:run
```

Or run the JAR:

```bash
java -jar target/example-app-1.0.0.jar
```

## Testing Endpoints

Once the application is running, test these endpoints:

### Hello Endpoint
```bash
curl http://localhost:8080/
```

Expected response:
```json
{
  "message": "Hello! This application is secured by Security Platform Starter",
  "status": "All vulnerabilities are managed centrally"
}
```

### Status Endpoint
```bash
curl http://localhost:8080/api/status
```

Expected response:
```json
{
  "application": "Example App",
  "security": "Enabled",
  "platform": "Security Platform Starter v1.0.0"
}
```

### Health Check (Actuator)
```bash
curl http://localhost:8080/actuator/health
```

### Info Endpoint (Actuator)
```bash
curl http://localhost:8080/actuator/info
```

## Security Features in Action

When you run this application, you'll notice:

1. **Security Headers**: All responses include security headers (check with browser dev tools)
2. **Vulnerability Monitoring**: On startup, you'll see security configuration logs
3. **Actuator Security**: Health and info endpoints are public, others require authentication
4. **CSRF Protection**: Enabled for POST, PUT, DELETE requests

## Checking Security Configuration

Look at the startup logs to see the security configuration:

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

## Customizing Security

You can customize security settings in `application.properties`:

```properties
# Disable CSRF for development
security.platform.csrf-enabled=false

# Enable CORS
security.platform.cors-enabled=true

# Disable security headers
security.platform.security-headers-enabled=false
```
