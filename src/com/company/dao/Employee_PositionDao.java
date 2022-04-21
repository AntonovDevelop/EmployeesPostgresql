package com.company.dao;

import com.company.model.Employee;
import com.company.model.Employee_Position;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee_PositionDao implements Dao<Employee_Position, Integer>{
    private static final Logger LOGGER = Logger.getLogger(Employee_PositionDao.class.getName());
    private final Optional<Connection> connection;

    public Employee_PositionDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Employee_Position> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Employee_Position> employee_position = Optional.empty();
            String sql = "SELECT * FROM employee_position WHERE employee_position_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int EmployeeID = resultSet.getInt("EmployeeID");
                    int PositionID = resultSet.getInt("PositionID");
                    double stavka = resultSet.getDouble("stavka");

                    employee_position = Optional.of(new Employee_Position(id, EmployeeID, PositionID, stavka));

                    LOGGER.log(Level.INFO, "Found {0} in database", employee_position.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return employee_position;
        });
    }

    @Override
    public Collection<Employee_Position> getAll() {
        Collection<Employee_Position> employee_positions = new ArrayList<>();
        String sql = "SELECT * FROM employee_position";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int EmployeeID = resultSet.getInt("EmployeeID");
                    int PositionID = resultSet.getInt("PositionID");
                    double stavka = resultSet.getDouble("stavka");

                    Employee_Position employee_position = new Employee_Position(id, EmployeeID, PositionID, stavka);

                    employee_positions.add(employee_position);

                    LOGGER.log(Level.INFO, "Found {0} in database", employee_position);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return employee_positions;
    }

    @Override
    public Optional<Integer> save(Employee_Position employee_position) {
        String message = "The employee_position to be added should not be null";
        Employee_Position nonNullEmployee_position = Objects.requireNonNull(employee_position, message);
        String sql = "INSERT INTO "
                + "employee_position(value, date, EmployeeID) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullEmployee_position.getEmployeeID());
                statement.setInt(2, nonNullEmployee_position.getPositionID());
                statement.setDouble(3, nonNullEmployee_position.getStavka());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                nonNullEmployee_position.setId(generatedId.get());
                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullEmployee_position, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Employee_Position employee_position) {
        String message = "The employee_position to be updated should not be null";
        Employee_Position nonNullEmployee_position = Objects.requireNonNull(employee_position, message);
        String sql = "UPDATE employee_position "
                + "SET "
                + "EmployeeID = ? "
                + "PositionID = ? "
                + "stavka = ?, "
                + "WHERE "
                + "employee_position_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullEmployee_position.getEmployeeID());
                statement.setInt(2, nonNullEmployee_position.getPositionID());
                statement.setDouble(3, nonNullEmployee_position.getStavka());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee_position updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Employee_Position employee_position) {
        String message = "The employee_position to be deleted should not be null";
        Employee_Position nonNullEmployee_position = Objects.requireNonNull(employee_position, message);
        String sql = "DELETE FROM employee_position WHERE employee_position_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullEmployee_position.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee_position deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
