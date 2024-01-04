package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.TransactionRepository;
import at.fhtw.sampleapp.persistence.repository.TransactionRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public class TransactionService extends AbstractService{

    private TransactionRepository transactionRepository;

    public TransactionService() { transactionRepository = new TransactionRepositoryImpl(new UnitOfWork()); }

    public Response selectCards() {
        try {
            List<Map<String, Object>> cards = transactionRepository.selectCards();
            String json = this.getObjectMapper().writeValueAsString(cards);
            return new Response(HttpStatus.OK, ContentType.JSON, json);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("No packages found")) {
                return new Response(HttpStatus.OK, ContentType.JSON, "No card package available for buying");
            } else {
                return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "Internal Server Error");
            }
        }
    }

    public Response acquirePackages(String username) {
        try {
            // Call the acquirePackages method from the TransactionRepository
            transactionRepository.acquirePackages(username);
            // If the operation is successful, return a success message
            return new Response(HttpStatus.OK, ContentType.JSON, "A package has been successfully bought");
        } catch (Exception e) {
            e.printStackTrace();
            // If an error occurs, print the stack trace and return an error message
            if (e.getMessage().equals("No packages found")) {
                return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No card package available for buying");
            } else if (e.getMessage().equals("Not enough coins")) {
                return new Response(HttpStatus.FORBIDDEN, ContentType.JSON, "Not enough money for buying a card package");
            } else {
                return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "Internal Server Error");
            }
        }
    }

}
