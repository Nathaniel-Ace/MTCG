package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.UserService;
import com.sun.net.httpserver.HttpExchange;

import javax.management.StringValueExp;
import java.io.OutputStream;

public class UserController implements RestController {

    private final UserService userService;

    public UserController() { this.userService = new UserService(); }

    @Override
    public Response handleRequest(Request request){

        System.out.println(request.getBody());
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 2
                && request.getPathParts().get(1).equals("username")) {
            String username = request.getPathParts().get(2);
            return this.userService.getUserByUsername(username);
        } else if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {
            String userId = request.getPathParts().get(1);
            return this.userService.getUser(Integer.parseInt(userId));

        } else if (request.getMethod() == Method.POST) {

            return this.userService.addUser(request);

        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
