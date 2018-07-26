package io.klr.ci.controller;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import io.klr.ci.util.Constant;

/**
 * @author <a href="mailto:prefix614@gmail.com">KLR</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalculatorControllerTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private JdbcTemplate jt;

    @Before
    public void init() {
        jt.update(format("DELETE FROM %s", Constant.TABLE));
    }

    @Test
    public void add() throws Exception {
        final String body = client.getForObject("/add?x=1&y=2", String.class);
        assertThat(body, equalTo("3"));

        final int result = jt.queryForObject(format("SELECT COUNT(*) FROM %s", Constant.TABLE), Integer.class);
        assertThat(result, equalTo(1));

        final Map<String, Object> map = jt.queryForMap(format("SELECT * FROM %s", Constant.TABLE));
        assertThat(Integer.parseInt(map.get("r").toString()), equalTo(3));
    }
}
