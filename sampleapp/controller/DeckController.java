package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.DeckService;

import java.util.List;

public class DeckController implements RestController {

    private final DeckService deckService;

    public DeckController() { this.deckService = new DeckService(); }

    @Override
    public Response handleRequest(Request request){

        if (request.getMethod() == Method.GET) {
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
                String username = parts[0];
                System.out.println("username: " + username);

                // Get the format parameter from the query parameters
                String format = null;
                String params = request.getParams();
                if (params != null) {
                    String[] queryParams = params.split("&");
                    for (String param : queryParams) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length == 2 && "format".equals(keyValue[0])) {
                            format = keyValue[1];
                            break;
                        }
                    }
                }

                return this.deckService.getDeck(username, format);
            } else {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }
        }

        else if (request.getMethod() == Method.PUT) {
            if (request.getHeaderMap().getHeader("Authorization") == null || request.getHeaderMap().getHeader("Authorization").isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }

            String token = request.getHeaderMap().getHeader("Authorization");
            token = token.replace("Bearer ", "");

            if (validToken(token)) {
                String[] parts = token.split("-");
                String username = parts[0];

                // Update the deck
                return this.deckService.updateDeck(username, request);

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
