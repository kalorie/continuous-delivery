package io.klr.ci.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ExternalHoverflyTimingServiceTest {

    private TimingService ts;

    @Before
    public void init() {
        ts = new TimingService();
        ts.setUrl("http://time.jsontest.com/");
        // Better set during starting process to cooperate with Jenkins CI
        // System.setProperty("proxySet", String.valueOf(true));
        // System.setProperty("http.proxyHost", "localhost");
        // System.setProperty("http.proxyPort", String.valueOf(8500));
    }

    @Test
    public void getNormalResponse() {
        final String time = ts.invoke();
        assertThat(time, equalTo("05-22-2019 07:33:19 AM"));
    }
}
