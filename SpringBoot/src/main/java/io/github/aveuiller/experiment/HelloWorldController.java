package io.github.aveuiller.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/hello",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloWorldController {
    Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> index() {
        logger.info("Hellogger World2!");
        HashMap<String, String> output = new HashMap<>();
        output.put("Hello", "World");
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @RequestMapping("")
    public ResponseEntity<Map<String, String>> indexWithError() throws ApiException {
        logger.warn("Someone requested an error!");
        throw new ApiException(HttpStatus.BAD_REQUEST, "you asked for it");
    }
}
