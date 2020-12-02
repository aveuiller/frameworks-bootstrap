package io.github.aveuiller.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/hello")
    public ResponseEntity<Map<String, String>> index() {
        logger.info("Hellogger World2!");
        HashMap<String, String> output = new HashMap<>();
        output.put("Hello", "World");
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
    @RequestMapping("/hellol")
    public ResponseEntity<Map<String, String>> indexWithError() {
        logger.warn("Someone requested an error!");
        Map<String, String> error = new HashMap<>();
        error.put("message", "you asked for it");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
