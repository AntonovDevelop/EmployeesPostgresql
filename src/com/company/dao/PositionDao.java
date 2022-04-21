package com.company.dao;

import com.company.model.Position;
import com.company.model.Position;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PositionDao implements Dao<Position, Integer>{
    private static final Logger LOGGER = Logger.getLogger(PositionDao.class.getName());
    private final Optional<Connection> connection;

    public PositionDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Position> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Position> position = Optional.empty();
            String sql = "SELECT * FROM position WHERE position_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String imya = resultSet.getString("imya");
                    double oklad = resultSet.getDouble("oklad");
                    int DepartmentID = resultSet.getInt("DepartmentID");

                    position = Optional.of(new Position(id, imya, oklad, DepartmentID));

                    LOGGER.log(Level.INFO, "Found {0} in database", position.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return position;
        });
    }

    @Override
    public Collection<Position> getAll() {
        Collection<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String imya = resultSet.getString("imya");
                    double oklad = resultSet.getDouble("oklad");
                    int DepartmentID = resultSet.getInt("DepartmentID");

                    Position position = new Position(id, imya, oklad, DepartmentID);

                    positions.add(position);

                    LOGGER.log(Level.INFO, "Found {0} in database", position);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return positions;
    }

    @Override
    public Optional<Integer> save(Position position) {
        String message = "The position to be added should not be null";
        Position nonNullPosition = Objects.requireNonNull(position, message);
        String sql = "INSERT INTO "
                + "position(familiya, imya, otchestvo, doplata) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullPosition.getImya());
                statement.setDouble(2, nonNullPosition.getOklad());
                statement.setInt(3, nonNullPosition.getDepartmentID());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                nonNullPosition.setId(generatedId.get());
                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullPosition, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Position position) {
        String message = "The position to be updated should not be null";
        Position nonNullPosition = Objects.requireNonNull(position, message);
        String sql = "UPDATE position "
                + "SET "
                + "imya = ?, "
                + "oklad = ? "
                + "DepartmentID = ? "
                + "WHERE "
                + "position_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullPosition.getImya());
                statement.setDouble(2, nonNullPosition.getOklad());
                statement.setInt(3, nonNullPosition.getDepartmentID());
                statement.setInt(4, nonNullPosition.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the position updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Position position) {
        String message = "The position to be deleted should not be null";
        Position nonNullPosition = Objects.requireNonNull(position, message);
        String sql = "DELETE FROM position WHERE position_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullPosition.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the position deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
