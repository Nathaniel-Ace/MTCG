package at.fhtw;

import at.fhtw.httpserver.server.Server;
import at.fhtw.httpserver.utils.Router;
import at.fhtw.sampleapp.controller.EchoController;
import at.fhtw.sampleapp.controller.PackageController;
import at.fhtw.sampleapp.controller.SessionController;
import at.fhtw.sampleapp.controller.UserController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();

        router.addService("/echo", new EchoController());
        router.addService("/users", new UserController());
        router.addService("/sessions", new SessionController());
        router.addService("/packages", new PackageController());

        return router;
    }
    String ka = "WaterDragon";

}
