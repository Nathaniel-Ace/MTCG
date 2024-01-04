package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.UserService;

public class UserController implements RestController {

    private final UserService userService;

    public UserController() { this.userService = new UserService(); }

    @Override
    public Response handleRequest(Request request){

        //System.out.println(request.getBody());
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1 && request.getPathParts().get(0).equals("users")) {
            String usernameFromPath = request.getPathParts().get(1);

            if (request.getHeaderMap().getHeader("Authorization") == null || request.getHeaderMap().getHeader("Authorization").isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }

            String token = request.getHeaderMap().getHeader("Authorization");

            // Remove the "Bearer " part from the token
            token = token.replace("Bearer ", "");

            // Check if the token is valid
            if (validToken(token)) {
                // Split the token around the "-" character
                String[] parts = token.split("-");
                // The first part is the username
                String usernameFromToken = parts[0];

                if (!usernameFromPath.equals(usernameFromToken)) {
                    return new Response(
                            HttpStatus.UNAUTHORIZED,
                            ContentType.JSON,
                            "Access token does not match the username"
                    );
                }

                System.out.println("username: " + usernameFromToken);
                return this.userService.getUserByUsername(usernameFromToken);
            } else {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }
        } else if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {
            String userId = request.getPathParts().get(1);
            return this.userService.getUser(Integer.parseInt(userId));

        } else if (request.getMethod() == Method.POST) {

            return this.userService.addUser(request);

        } else if (request.getMethod() == Method.PUT && request.getPathParts().size() > 1 && request.getPathParts().get(0).equals("users")) {
            String usernameFromPath = request.getPathParts().get(1);

            if (request.getHeaderMap().getHeader("Authorization") == null || request.getHeaderMap().getHeader("Authorization").isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }

            String token = request.getHeaderMap().getHeader("Authorization");

            // Remove the "Bearer " part from the token
            token = token.replace("Bearer ", "");

            // Check if the token is valid
            if (validToken(token)) {
                // Split the token around the "-" character
                String[] parts = token.split("-");
                // The first part is the username
                String usernameFromToken = parts[0];

                if (!usernameFromPath.equals(usernameFromToken)) {
                    return new Response(
                            HttpStatus.UNAUTHORIZED,
                            ContentType.JSON,
                            "Access token does not match the username"
                    );
                }

                return this.userService.editUser(usernameFromToken, request);
            } else {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
