package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.Card;

import java.util.List;

public interface PackagesRepository {

    void addCards(List<Card> cards);

}
