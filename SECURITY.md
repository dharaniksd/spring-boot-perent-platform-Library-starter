# Security Summary

## Security Features Implemented

This Spring Boot Security Platform Starter implements the following security features:

### 1. Dependency Management
- **Secure versions** of common dependencies with known vulnerabilities:
  - SnakeYAML 2.2 (mitigates deserialization vulnerabilities)
  - Jackson 2.16.1 (addresses multiple CVEs)
  - Tomcat 10.1.18 (patches container vulnerabilities)
  - Logback 1.4.14 (secure logging)
  - Spring Security 6.2.1 (latest security patches)

### 2. Security Headers
The starter automatically configures the following security headers:
- **Content-Security-Policy**: `default-src 'self'` to prevent XSS attacks
- **X-Frame-Options**: DENY to prevent clickjacking
- **Strict-Transport-Security**: Enforces HTTPS with 1-year max-age and includeSubDomains
- **Referrer-Policy**: STRICT_ORIGIN_WHEN_CROSS_ORIGIN to control referrer information

**Note**: X-XSS-Protection header is NOT included as it's deprecated and modern browsers rely on CSP instead.

### 3. CSRF Protection
- **Enabled by default** for all requests except actuator endpoints
- Can be disabled for REST APIs using token-based authentication via configuration
- When disabled, a warning is logged to alert administrators

### 4. CORS Configuration
- **Disabled by default** for security
- When enabled, uses explicit allowed headers (Content-Type, Authorization, X-Requested-With) instead of wildcards
- Credentials are allowed only with specific headers to prevent security vulnerabilities
- Configured for localhost development only by default

### 5. Actuator Security
- Health and info endpoints are publicly accessible
- All other actuator endpoints require authentication
- When disabled, a warning is logged

### 6. OWASP Dependency Check
- Integrated OWASP dependency check plugin
- Configured to fail builds on CVSS score >= 7
- Supports suppression file for false positives

## Known Security Considerations

### 1. CSRF Can Be Disabled
**Alert**: CodeQL detected that CSRF protection can be disabled.

**Context**: This is intentional and necessary for REST APIs that use token-based authentication (JWT, OAuth2, etc.). CSRF protection is:
- **Enabled by default** (secure by default principle)
- Only disabled when explicitly configured: `security.platform.csrf-enabled=false`
- A warning is logged when disabled to alert administrators

**Recommendation**: Keep CSRF enabled unless you have a valid reason (e.g., stateless REST API with JWT).

### 2. Permissive Default Authorization
When `security.platform.actuator-security-enabled=false`, all requests are permitted by default.

**Mitigation**: 
- Actuator security is **enabled by default**
- A warning is logged when disabled
- Applications should implement their own authorization rules if they disable this feature

## Security Best Practices

1. **Always enable CSRF** for applications with session-based authentication
2. **Use HTTPS** in production to leverage HSTS headers
3. **Regularly update** the starter library to get latest security patches
4. **Run OWASP checks** regularly: `mvn dependency-check:check`
5. **Review security logs** on application startup when `vulnerability-check-on-startup=true`

## Vulnerability Management Process

1. **Discovery**: Monitor security advisories for dependencies
2. **Update**: Update version in parent POM's `<properties>` section
3. **Build**: Run `mvn clean install` to rebuild the starter
4. **Distribute**: All microservices using the starter get the fix on next build
5. **Verify**: Run `mvn dependency-check:check` to confirm fix

## Future Enhancements

Consider adding:
- JWT authentication support
- Rate limiting
- API key validation
- OAuth2 integration
- Metrics for security events
- Automated vulnerability scanning in CI/CD

## Contact

For security issues, please contact the repository maintainers directly rather than opening public issues.
