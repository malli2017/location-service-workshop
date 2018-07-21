package nl.toefel.location.service.systemtest.dto;

/**
 * Fields are public to avoid getter/setter generation for json (un)marshalling.
 */
public class LocationDTO {
    public String name;
    public Double lat;
    public Double lon;

    public LocationDTO() {
    }

    public LocationDTO(String name, Double lat, Double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
