package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.Card;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.DatabaseManager;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PackagesRepositoryImpl implements PackagesRepository {

    private UnitOfWork unitOfWork;

    public PackagesRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public void addCards(List<Card> cards) {
        try {
            Connection conn = DatabaseManager.INSTANCE.getConnection();
            conn.setAutoCommit(false);
            unitOfWork.setConnection(conn);

            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);

                // INSERT-Statement für "cards"-Tabelle
                String insertCardsSql = "INSERT INTO \"cards\" (id, name, damage, type) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatementCards = this.unitOfWork.prepareStatement(insertCardsSql)) {
                    preparedStatementCards.setString(1, card.getId());
                    preparedStatementCards.setString(2, card.getName());
                    preparedStatementCards.setInt(3, card.getDamage());
                    preparedStatementCards.setInt(4, card.getType());
                    preparedStatementCards.executeUpdate();
                }
            }
            // INSERT-Statement für "packages"-Tabelle
            String insertPackagesSql = "INSERT INTO \"packages\" (card1_id, card2_id, card3_id, card4_id, card5_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatementPackages = this.unitOfWork.prepareStatement(insertPackagesSql)) {
                for (int i = 0; i < cards.size(); i++) {
                    preparedStatementPackages.setString(i + 1, cards.get(i).getId());
                }
                preparedStatementPackages.executeUpdate();
            }

            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Insert nicht erfolgreich", e);
        }
    }

}
