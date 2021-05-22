package com.outdoors.hiking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outdoors.hiking.dto.weather.WeatherData;
import com.outdoors.hiking.service.WeatherQueryService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests covering {@link WeatherQueryService}
 */
@ExtendWith(MockitoExtension.class)
public class WeatherQueryServiceTest {

    public static final String TEST_LOCATION = "vilnius";

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private CloseableHttpResponse response;

    @Mock
    private HttpEntity httpEntity;

    private WeatherQueryService weatherQueryService;

    @BeforeEach
    void setUp() {
        weatherQueryService = new WeatherQueryService(httpClient, mapper, "location=%s", "key", "host");
    }

    @Test
    void shouldSuccessfullyQueryForWeatherData() throws IOException {
        //given
        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("".getBytes()));

        WeatherData expected = new WeatherData();
        expected.setBase("testBase");
        expected.setId(1L);
        when(mapper.readValue(anyString(), eq(WeatherData.class))).thenReturn(expected);

        //when
        WeatherData actual = weatherQueryService.retrieveWeatherData(TEST_LOCATION);

        //then
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getBase(), actual.getBase());
        Assertions.assertEquals(expected.getId(), actual.getId());

        verify(httpClient).execute(any(HttpGet.class));
    }

    @Test
    void shouldHandleHttpClientExecuteException() throws IOException {
        //given
        when(httpClient.execute(any(HttpGet.class))).thenThrow(IOException.class);

        //when
        WeatherData actual = weatherQueryService.retrieveWeatherData(TEST_LOCATION);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getMessage());
    }

    @Test
    void shouldHandleObjectMapperException() throws IOException {
        //given
        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("".getBytes()));

        when(mapper.readValue(anyString(), eq(WeatherData.class))).thenThrow(JsonProcessingException.class);

        //when
        WeatherData actual = weatherQueryService.retrieveWeatherData(TEST_LOCATION);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getMessage());

        verify(httpClient).execute(any(HttpGet.class));
    }
}