package com.security.platform.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the Security Platform Starter.
 * Allows applications to customize security settings.
 */
@ConfigurationProperties(prefix = "security.platform")
public class SecurityPlatformProperties {

    /**
     * Enable or disable the security platform starter
     */
    private boolean enabled = true;

    /**
     * Enable security headers
     */
    private boolean securityHeadersEnabled = true;

    /**
     * Enable CORS configuration
     */
    private boolean corsEnabled = false;

    /**
     * Enable CSRF protection
     */
    private boolean csrfEnabled = true;

    /**
     * Enable actuator security
     */
    private boolean actuatorSecurityEnabled = true;

    /**
     * Vulnerability check on startup
     */
    private boolean vulnerabilityCheckOnStartup = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSecurityHeadersEnabled() {
        return securityHeadersEnabled;
    }

    public void setSecurityHeadersEnabled(boolean securityHeadersEnabled) {
        this.securityHeadersEnabled = securityHeadersEnabled;
    }

    public boolean isCorsEnabled() {
        return corsEnabled;
    }

    public void setCorsEnabled(boolean corsEnabled) {
        this.corsEnabled = corsEnabled;
    }

    public boolean isCsrfEnabled() {
        return csrfEnabled;
    }

    public void setCsrfEnabled(boolean csrfEnabled) {
        this.csrfEnabled = csrfEnabled;
    }

    public boolean isActuatorSecurityEnabled() {
        return actuatorSecurityEnabled;
    }

    public void setActuatorSecurityEnabled(boolean actuatorSecurityEnabled) {
        this.actuatorSecurityEnabled = actuatorSecurityEnabled;
    }

    public boolean isVulnerabilityCheckOnStartup() {
        return vulnerabilityCheckOnStartup;
    }

    public void setVulnerabilityCheckOnStartup(boolean vulnerabilityCheckOnStartup) {
        this.vulnerabilityCheckOnStartup = vulnerabilityCheckOnStartup;
    }
}
