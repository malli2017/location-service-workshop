package nl.toefel.location.service.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class GenericResponse {

    private HttpStatus status;
    private String message;

    public GenericResponse(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return status;
    }

    public int getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }
}