package io.klr.ci.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.SimulationSource;

public class TryCatchResourceTimingServiceTest {

    private TimingService ts;

    @Before
    public void init() {
        ts = new TimingService();
        ts.setUrl("http://time.jsontest.com/");
    }

    @Test
    public void getNormalResponse() {
        try (final Hoverfly hoverfly = new Hoverfly(HoverflyMode.SIMULATE)) {
            hoverfly.start();
            hoverfly.simulate(SimulationSource.classpath("timing.json"));
            final String time = ts.invoke();
            assertThat(time, equalTo("05-22-2019 07:33:19 AM"));
        }
    }
}
