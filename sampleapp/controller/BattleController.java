package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.BattleService;

public class BattleController implements RestController {

    private final BattleService battleService;

    public BattleController() {this.battleService = new BattleService(); }

    public Response handleRequest(Request request){

        if (request.getMethod() == Method.POST) {
            if (request.getHeaderMap().getHeader("Authorization") == null || request.getHeaderMap().getHeader("Authorization").isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Access token is missing or invalid"
                );
            }

            return new Response(
                    HttpStatus.NOT_IMPLEMENTED,
                    ContentType.JSON,
                    "Not implemented yet"
            );
        }

        return new Response(
            HttpStatus.BAD_REQUEST,
            ContentType.JSON,
            "[]");

    }

}
