package nl.toefel.location.service;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;

public class Config {

    private static final String APPLICATION_NAME = "location-service";
    private String databaseUrl;
    private String databaseDriverClassName;
    private String databasePassword;
    private String databaseUsername;
    private String commitId;
    private String buildTimestamp;

    public static Config fromEnvironment() {
        Config cfg = new Config();
        cfg.databaseUrl = getEnvironmentValueOrDefault("DB_URL", "jdbc:postgresql://127.0.0.1/locationservice");
        cfg.databaseDriverClassName = getEnvironmentValueOrDefault("DB_DRIVER", "org.postgresql.Driver");
        cfg.databaseUsername = getEnvironmentValueOrDefault("DB_USER", "locationservice");
        cfg.databasePassword = getEnvironmentValueOrDefault("DB_PASSWORD", "locationservice");
        cfg.commitId = getEnvironmentValueOrDefault("COMMIT_ID", "unknown");
        cfg.buildTimestamp = getEnvironmentValueOrDefault("BUILD_TIMESTAMP", ZonedDateTime.ofInstant(EPOCH, UTC).toString());
        return cfg;
    }

    private Config() {
        // use factory method with readable name
    }

    private static String getEnvironmentValueOrDefault(String envName, String defaultValue) {
        String envValue = System.getenv(envName);
        if (envValue == null || envName.isEmpty()) {
            return defaultValue;
        }
        return envValue;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseDriverClassName() {
        return databaseDriverClassName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getBuildTimestamp() {
        return buildTimestamp;
    }

    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    public ZonedDateTime jvmStartupTimeUTC() {
        long applicationStartupTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(applicationStartupTime), UTC);
    }
}
