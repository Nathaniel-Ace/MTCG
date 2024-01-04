package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.CardService;

public class CardController implements RestController {

    private final CardService cardService;

    public CardController() { this.cardService = new CardService(); }

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

                return this.cardService.getCards(username);
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
            "[]");
    }
}
