package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepositoryImpl implements SessionRepository{
    private UnitOfWork unitOfWork;

    public SessionRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public boolean SearchUsername(User user) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("SELECT * FROM public.users WHERE username = ? AND password = ?"))
        {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            // Print the SQL query
            System.out.println("Executing SQL query: " + preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("No user found");
                return false;
            } else {
                System.out.println("User found");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }
}
