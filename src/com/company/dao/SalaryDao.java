package com.company.dao;

import com.company.model.Salary;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaryDao implements Dao<Salary, Integer>{
    private static final Logger LOGGER = Logger.getLogger(SalaryDao.class.getName());
    private final Optional<Connection> connection;

    public SalaryDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Salary> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Salary> salary = Optional.empty();
            String sql = "SELECT * FROM salary WHERE salary_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    double value = resultSet.getDouble("value");
                    String date = resultSet.getString("date");
                    int EmployeeID = resultSet.getInt("EmployeeID");

                    salary = Optional.of(new Salary(id, value, date, EmployeeID));

                    LOGGER.log(Level.INFO, "Found {0} in database", salary.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return salary;
        });
    }

    @Override
    public Collection<Salary> getAll() {
        Collection<Salary> salarys = new ArrayList<>();
        String sql = "SELECT * FROM salary";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double value = resultSet.getDouble("value");
                    String date = resultSet.getString("date");
                    int EmployeeID = resultSet.getInt("EmployeeID");

                    Salary salary = new Salary(id, value, date, EmployeeID);

                    salarys.add(salary);

                    LOGGER.log(Level.INFO, "Found {0} in database", salary);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return salarys;
    }

    @Override
    public Optional<Integer> save(Salary salary) {
        String message = "The salary to be added should not be null";
        Salary nonNullSalary = Objects.requireNonNull(salary, message);
        String sql = "INSERT INTO "
                + "salary(value, date, EmployeeID) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setDouble(1, nonNullSalary.getValue());
                statement.setString(2, nonNullSalary.getDate());
                statement.setInt(3, nonNullSalary.getEmployeeID());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                nonNullSalary.setId(generatedId.get());
                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullSalary, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Salary salary) {
        String message = "The salary to be updated should not be null";
        Salary nonNullSalary = Objects.requireNonNull(salary, message);
        String sql = "UPDATE salary "
                + "SET "
                + "value = ?, "
                + "date = ?, "
                + "EmployeeID = ? "
                + "WHERE "
                + "salary_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setDouble(1, nonNullSalary.getValue());
                statement.setString(2, nonNullSalary.getDate());
                statement.setInt(3, nonNullSalary.getEmployeeID());
                statement.setInt(4, nonNullSalary.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the salary updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Salary salary) {
        String message = "The salary to be deleted should not be null";
        Salary nonNullSalary = Objects.requireNonNull(salary, message);
        String sql = "DELETE FROM salary WHERE salary_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullSalary.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the salary deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
