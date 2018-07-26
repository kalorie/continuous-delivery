package io.klr.ci.service;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.klr.ci.util.Constant;

/**
 * @author <a href="mailto:prefix614@gmail.com">KLR</a>
 * @since 1.0.0
 */
@Service
public class CalculatorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(final int x, final int y, final int r, final String ip) {
        jdbcTemplate.update(format("INSERT INTO %s(x, y, r, ip) VALUES(?, ?, ?, ?)", Constant.TABLE), x, y, r, ip);
    }
}
