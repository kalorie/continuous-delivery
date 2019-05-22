package io.klr.ci.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.rule.HoverflyRule;

public class TimingServiceTest {

    private TimingService ts;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(SimulationSource.classpath("timing.json"));

    @Before
    public void init() {
        ts = new TimingService();
        ts.setUrl("http://time.jsontest.com/");
    }

    @Test
    public void getNormalResponse() {
        final String time = ts.invoke();
        assertThat(time, equalTo("05-22-2019 07:33:19 AM"));
    }
}
