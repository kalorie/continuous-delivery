package io.klr.ci.service;

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimingService {

    private RestTemplate restTemplate;

    private String url;

    public TimingService() {
        this.restTemplate = new RestTemplate();
    }

    public String invoke() {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }
        final String response = restTemplate.getForObject(uri, String.class);
        try {
            final JSONObject json = new JSONObject(response);
            final String date = json.getString("date");
            final String time = json.getString("time");
            return format("%s %s", date, time);
        } catch (final JSONException e) {
            return LocalTime.now().toString();
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
