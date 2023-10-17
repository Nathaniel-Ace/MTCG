package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.controller.WeatherController;
import at.fhtw.sampleapp.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WeatherService extends AbstractService {

    public WeatherService() {

    }

    // GET /weather(:id
    public Response getWeather(String id)
    {
            return new Response(HttpStatus.NOT_IMPLEMENTED);
    }
    // GET /weather
    public Response getWeather() {
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }

    // POST /weather
    public Response addWeather(Request request) {
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }

    // GET /weather
    // gleich wie "public Response getWeather()" nur mittels Repository

    public Response getWeatherPerRepository() {
        System.out.println("getWeatherPerRepository");;
        Weather weather = new Weather(1, "Austria", "Viennna", 22);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(weather);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
