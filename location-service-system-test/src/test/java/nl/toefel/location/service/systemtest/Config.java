package nl.toefel.location.service.systemtest;

public class Config {

    /** an address should have the format host:port*/
    private String locationServiceAddress;

    public static Config fromEnvironment() {
        Config cfg = new Config();
        cfg.locationServiceAddress = getEnvironmentValueOrDefault("LOCATION_SERVICE_ADDRESS", "localhost:8080");
        return cfg;
    }

    private Config() {
        // use factory method with readable name
    }

    private static String getEnvironmentValueOrDefault(String envName, String defaultValue) {
        String envValue = System.getenv(envName);
        if (envValue == null || envValue.isEmpty()) {
            return defaultValue;
        }
        return envValue;
    }

    public String getLocationServiceAddress() {
        return locationServiceAddress;
    }
}
