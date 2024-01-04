package at.fhtw.httpserver.server;

public interface RestController {

    public default boolean validToken(String token) {
        // Split the token around the "-" character
        String[] parts = token.split("-");

        // Check if the token is correctly given
        return token != null && token.contains("-") && parts[1].equals("mtcgToken");
    }

    Response handleRequest(Request request);

}
