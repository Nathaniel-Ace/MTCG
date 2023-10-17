package at.fhtw.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractUsers {

    private ObjectMapper objectMapper;

    public AbstractUsers() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}