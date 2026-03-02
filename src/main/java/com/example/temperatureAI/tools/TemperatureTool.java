package com.example.temperatureAI.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class TemperatureTool {

    private final RestClient weatherClient;
    private final RestClient geoClient;

    public TemperatureTool(@Value("${weather.api.base-url}") String weatherUrl,
                           @Value("${weather.geocoding-url}") String geoUrl) {
        this.weatherClient = RestClient.create(weatherUrl);
        this.geoClient = RestClient.create(geoUrl);
    }

    @Tool(description = "Get current temperature for a city name")
    public String getCurrentTemperature(@ToolParam(description = "City name, e.g. Berlin") String location) {
        try {
            // 1. Geocoding: Get Lat/Lon for the city
            GeoResponse geo = geoClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("name", location)
                            .queryParam("count", 1).build())
                    .retrieve()
                    .body(GeoResponse.class);

            if (geo == null || geo.results() == null || geo.results().isEmpty()) {
                return "I can't find " + location + ". Is that a real place or just a figment of your lonely imagination?";
            }

            var city = geo.results().get(0);

            // 2. Weather: Get actual data
            WeatherResponse weather = weatherClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("latitude", city.latitude())
                            .queryParam("longitude", city.longitude())
                            .queryParam("current_weather", true)
                            .build())
                    .retrieve()
                    .body(WeatherResponse.class);

            return String.format("In %s, it's currently %.1f°C. Not that you'd enjoy the fresh air anyway.",
                    location, weather.current_weather().temperature());

        } catch (Exception e) {
            return "The weather API is down. The universe is literally conspiring against your request.";
        }
    }

    // DTOs for Open-Meteo
    private record GeoResponse(List<GeoResult> results) {
    }

    private record GeoResult(double latitude, double longitude) {
    }

    private record WeatherResponse(CurrentWeather current_weather) {
    }

    private record CurrentWeather(double temperature) {
    }
}
