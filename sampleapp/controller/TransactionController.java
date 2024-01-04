package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.TransactionService;

public class TransactionController implements RestController {

    private final TransactionService transactionService;

    public TransactionController() { this.transactionService = new TransactionService(); }

    @Override
    public Response handleRequest(Request request){

        if(request.getPathname().equals("/transactions/packages")) {
            if (request.getMethod() == Method.POST) {
                String token = request.getHeaderMap().getHeader("Authorization");

                // Remove the "Bearer " part from the token
                token = token.replace("Bearer ", "");

                // Check if the token is valid
                if (token == null || !token.contains("-") || !token.split("-")[1].equals("mtcgToken")) {
                    return new Response(
                            HttpStatus.UNAUTHORIZED,
                            ContentType.JSON,
                            "Access token is missing or invalid"
                    );
                }

                // Split the token around the "-" character
                String[] parts = token.split("-");
                // The first part is the username
                String username = parts[0];
                System.out.println("username: " + username);

                return this.transactionService.acquirePackages(username);
            } else if (request.getMethod() == Method.GET) {
                //System.out.println("get cards");
                return this.transactionService.selectCards();
            }
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
