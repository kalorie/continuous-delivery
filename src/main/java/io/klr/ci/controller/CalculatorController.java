package io.klr.ci.controller;

import static java.lang.String.format;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.klr.ci.service.CalculatorService;
import io.klr.ci.service.TimingService;

/**
 * @author <a href="mailto:prefix614@gmail.com">KLR</a>
 * @since 1.0.0
 */
@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private TimingService timingService;

    @GetMapping("/add")
    public String add(@RequestParam(value = "x", required = true) final int x,
            @RequestParam(value = "y", required = true) final int y, final HttpServletRequest request) {
        final int r = x + y;
        final String ip = request.getRemoteAddr();
        calculatorService.save(x, y, r, ip);
        return String.valueOf(r);
    }

    @GetMapping("/timed/add")
    public String timedAdd(@RequestParam(value = "x", required = true) final int x,
            @RequestParam(value = "y", required = true) final int y, final HttpServletRequest request) {
        final int r = x + y;
        final String ip = request.getRemoteAddr();
        calculatorService.save(x, y, r, ip);
        final String timestamp = timingService.invoke();
        return format("%s@%s", String.valueOf(r), timestamp);
    }
}
