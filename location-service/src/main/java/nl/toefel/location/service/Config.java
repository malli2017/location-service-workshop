package nl.toefel.location.service;

public class Config {

    private String databaseUrl;
    private String databaseDriverClassName;
    private String databasePassword;
    private String databaseUsername;

    public static Config fromEnvironment() {
        Config cfg = new Config();
        cfg.databaseUrl = getEnvironmentValueOrDefault("DB_URL", "jdbc:postgresql://127.0.0.1/locationservice");
        cfg.databaseDriverClassName = getEnvironmentValueOrDefault("DB_DRIVER", "org.postgresql.Driver");
        cfg.databaseUsername = getEnvironmentValueOrDefault("DB_USER", "locationservice");
        cfg.databasePassword = getEnvironmentValueOrDefault("DB_PASSWORD", "locationservice");
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
}
