package io.klr.ci.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:prefix614@gmail.com">KLR</a>
 * @since 1.0.0
 */
@RestController
public class CalculatorController {

    @GetMapping("/add")
    public String add(@RequestParam(value = "x", required = true) final int x,
            @RequestParam(value = "y", required = true) final int y) {
        return String.valueOf(x + y);
    }
}
