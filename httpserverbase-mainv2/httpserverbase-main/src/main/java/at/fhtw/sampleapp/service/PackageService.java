package at.fhtw.sampleapp.service;

import at.fhtw.sampleapp.model.Card;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.model.MonsterType;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.PackagesRepository;
import at.fhtw.sampleapp.persistence.repository.PackagesRepositoryImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class PackageService extends AbstractService {

    private PackagesRepository packagesRepository;

    public PackageService() { packagesRepository = new PackagesRepositoryImpl(new UnitOfWork()); }

    public Response addCards(Request request) {
        try {
            String body = request.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            List<Card> cards = new ArrayList<>();
            for (JsonNode cardNode : jsonNode) {
                String id = cardNode.get("Id").asText();
                String name = cardNode.get("Name").asText();
                Integer damage = cardNode.get("Damage").asInt();
                MonsterType type;
                if (name.contains("Fire")){
                    type = MonsterType.FIRE;
                } else if (name.contains("Water")) {
                    type = MonsterType.WATER;
                } else if (name.contains("Regular")){
                    type = MonsterType.REGULAR;
                } else {
                    type = MonsterType.NEUTRAL;
                }

                Card card = new Card(id, name, damage, type);
                cards.add(card);
            }

            packagesRepository.addCards(cards);

            return new Response(HttpStatus.CREATED, ContentType.JSON, "Package and cards successfully created");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "Card not added");
        }

    }

}
