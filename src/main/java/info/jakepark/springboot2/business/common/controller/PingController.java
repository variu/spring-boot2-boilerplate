package info.jakepark.springboot2.business.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PingController {

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("test")
    public String test() {
        return "This is log test!!";
    }

}
