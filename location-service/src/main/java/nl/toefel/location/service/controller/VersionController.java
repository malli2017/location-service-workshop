package nl.toefel.location.service.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import nl.toefel.location.service.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@ApiModel("Version")
@RestController
public class VersionController {

    @ApiOperation(
            notes = "Retrieves application version information",
            value = "Retrieves application version information")
    @GetMapping("/version")
    public VersionDto createCustomer() {
        return new VersionDto(Config.fromEnvironment());
    }


    /**
     * All fields are public so that no getters/setters need to be generated for JSON unmarshalling.
     * Promote to separate class if it get's used elsewhere.
     */
    public static class VersionDto {
        public String application;
        public String commitId;
        public String buildTimestamp;
        public String startTimestamp;
        public String currentTime;
        public String databaseUrl;

        public VersionDto(Config cfg) {
            application    = cfg.getApplicationName();
            commitId       = cfg.getCommitId();
            buildTimestamp = cfg.getBuildTimestamp();
            startTimestamp = cfg.jvmStartupTimeUTC().toString();
            currentTime    = ZonedDateTime.now(UTC).toString();
            databaseUrl    = cfg.getDatabaseUrl();
        }
    }
}