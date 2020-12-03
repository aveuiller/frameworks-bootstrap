package io.github.aveuiller.experiment;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiException extends Exception {
    private final HttpStatus status;
    private final String details;

    public ApiException(HttpStatus status, String details) {
        this.status = status;
        this.details = details;
    }

    public Map<String, String> getJsonMessage() {
        Map<String, String> message = new HashMap<>();
        message.put("reason", status.getReasonPhrase());
        message.put("code", String.valueOf(status.value()));
        message.put("message", details);
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
