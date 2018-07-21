package nl.toefel.location.service.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.toefel.location.service.access.LocationRepository;
import nl.toefel.location.service.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.lang.String.format;

@ApiModel("Locations")
@RestController
@Transactional
@ApiResponses({
        @ApiResponse(code = 400, message = "Client error", response = GenericResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = GenericResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = GenericResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = GenericResponse.class),
        @ApiResponse(code = 500, message = "Internal server error", response = GenericResponse.class)
})
public class LocationController {

    private static final Logger LOG = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationRepository locationRepository;

    @ApiOperation(
            value = "Finds a location by name",
            notes = "Finds a location by it's name.",
            response = Location.class)
    @GetMapping("/v1/locations/{name}")
    public Optional<Location> findByName(@PathVariable String name) {
        return locationRepository.findByName(name);
    }

    @ApiOperation(
            value = "Deletes a location by it's name",
            notes = "Deletes a location by it's name, returns the location if was actually deleted, 404 otherwise.",
            response = Location.class
    )
    @DeleteMapping("/v1/locations/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        Optional<Location> optionalLocation = locationRepository.findByName(name);
        optionalLocation.ifPresent(location -> locationRepository.delete(location));
        if (optionalLocation.isPresent()) {
            return ResponseEntity.ok(optionalLocation);
        } else {
            GenericResponse notFound = new GenericResponse(HttpStatus.NOT_FOUND, format("no location found with name '%s'", name));
            return ResponseEntity.status(404).body(notFound);
        }
    }

    @ApiOperation(
            value = "Creates a new location",
            notes = "Creates a new location. The name of the location is it's functional key and must be unqiue. Returns the saved location",
            response = Location.class)
    @PostMapping("/v1/locations")
    public ResponseEntity<?> create(@RequestBody Location location) {
        try {
            Location newLocation = locationRepository.save(location);
            return ResponseEntity.ok(newLocation);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("DataIntegrityViolationException occurred while saving new location", e);
            GenericResponse error = new GenericResponse(HttpStatus.BAD_REQUEST, format("location with name '%s' already exists", location.getName()));
            return new ResponseEntity<>(error, new HttpHeaders(), error.getHttpStatus());
        }
    }

    /**
     * Handle all exceptions by returning a uniform response to the client. This simplifies the clients because they
     * can depend on the GenericResponse type for all non 200 responses.
     *
     * @param ex
     * @param request
     * @return a {@link GenericResponse} describing the error
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAll(Exception ex, WebRequest request) {
        GenericResponse error = new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getHttpStatus());
    }
}