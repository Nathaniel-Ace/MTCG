package at.fhtw.users.controller;
import at.fhtw.httpserver.http.Method;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.users.model.Users;
import at.fhtw.users.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class UsersController implements RestController {
    private final UsersService usersService;

    public UsersController() {
        this.usersService = new UsersService();
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {
            return this.usersService.getUsers(request.getPathParts().get(1));
        } else if (request.getMethod() == Method.GET) {
            return this.usersService.getUser();
        } else if (request.getMethod() == Method.POST) {
            return this.usersService.addUser(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "Ungültige Anfrage"
        );
    }
}