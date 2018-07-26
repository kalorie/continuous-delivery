package io.klr.ci.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.klr.ci.service.CalculatorService;

/**
 * @author <a href="mailto:prefix614@gmail.com">KLR</a>
 * @since 1.0.0
 */
@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/add")
    public String add(@RequestParam(value = "x", required = true) final int x,
            @RequestParam(value = "y", required = true) final int y, final HttpServletRequest request) {
        final int r = x + y;
        final String ip = request.getRemoteAddr();
        calculatorService.save(x, y, r, ip);
        return String.valueOf(r);
    }
}
