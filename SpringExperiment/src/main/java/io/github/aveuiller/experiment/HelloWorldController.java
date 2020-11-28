package io.github.aveuiller.experiment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, String> index() {
        HashMap<String, String> output = new HashMap<>();
        output.put("Hello", "World");
        return output;
    }
}
