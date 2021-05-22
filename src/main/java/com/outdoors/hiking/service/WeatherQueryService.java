package com.outdoors.hiking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outdoors.hiking.dto.weather.WeatherData;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Service used to query Weather APIs for data based on user's input
 */
public class WeatherQueryService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherQueryService.class);
    private final String weatherApiUrl;
    private final String rapidApiKey;
    private final String rapidApiHost;

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherQueryService(CloseableHttpClient httpClient, ObjectMapper objectMapper, String weatherApiUrl, String rapidApiKey, String rapidApiHost) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.weatherApiUrl = weatherApiUrl;
        this.rapidApiKey = rapidApiKey;
        this.rapidApiHost = rapidApiHost;
    }

    /**
     * Queries Open Weather Map API for weather data by location
     *
     * @param location location of the user
     * @return populated {@link WeatherData} if the API call was successful, otherwise data with a message about the failure
     */
    public WeatherData retrieveWeatherData(String location) {
        HttpGet request = new HttpGet(URI.create(String.format(weatherApiUrl, location)));
        request.setHeader("x-rapidapi-key", rapidApiKey);
        request.setHeader("x-rapidapi-host", rapidApiHost);

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(request);
            return convertToWeatherData(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            LOG.warn(failureMessage(e.getMessage()));
            return emptyWeatherData();
        }
    }

    private WeatherData convertToWeatherData(String response) {
        try {
            return objectMapper.readValue(response, WeatherData.class);
        } catch (JsonProcessingException e) {
            LOG.warn(failureMessage(e.getMessage()));
            return emptyWeatherData();
        }
    }

    private String failureMessage(String msg) {
        return String.format("Could not construct weather data due to: %s", msg);
    }

    private WeatherData emptyWeatherData () {
        WeatherData data = new WeatherData();
        data.setMessage("Could not retrieve weather information, please check parameters and try again.");
        return data;
    }
}