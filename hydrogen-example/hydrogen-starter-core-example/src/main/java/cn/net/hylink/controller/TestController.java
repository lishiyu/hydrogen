package cn.net.hylink.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("")
    public String test() {

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("测试");
            throw new RuntimeException("测试");
        }

        return "Hello World";
    }

}
