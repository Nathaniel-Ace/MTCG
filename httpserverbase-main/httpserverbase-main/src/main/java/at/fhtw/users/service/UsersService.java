package at.fhtw.users.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.users.model.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class UsersService extends AbstractUsers {
    public UsersService() {

    }

    // GET /weather(:id
    public Response getUsers(String id) {
        Users users = new Users("admin", "test");
        String Json = null;
        try {
            Json = this.getObjectMapper().writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, Json);
    }

    // GET /weather
    public Response getUser() {
        Users user = new Users("admin", "test");
        String Json = null;
        try {
            Json = this.getObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, Json);
    }

    // POST /weather
    public Response addUser(Request request) {
        try {
            Users newUser = extractUserFromRequest(request);

            return new Response(HttpStatus.CREATED, ContentType.JSON, toJson(newUser));
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Fehler beim Hinzufügen des Benutzers: " + e.getMessage());
        }
    }


    // gleich wie "public Response getWeather()" nur mittels Repository
    public Response getWeatherPerRepository() {
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }

    // Extrahiert einen User aus dem Request-Objekt
    private Users extractUserFromRequest(Request request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = request.getBody();
        return objectMapper.readValue(requestBody, Users.class);
    }

    // Konvertiert ein User-Objekt in JSON
    private String toJson(Users user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }
}