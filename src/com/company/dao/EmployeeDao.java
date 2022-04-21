package com.company.dao;
import com.company.model.Employee;
import com.company.utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDao implements Dao<Employee, Integer> {

    //private static final Logger LOGGER = Logger.getLogger(EmployeeDao.class.getName());
    private final Optional<Connection> connection;

    public EmployeeDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Employee> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Employee> employee = Optional.empty();
            String sql = "SELECT * FROM employee WHERE employee_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String familiya = resultSet.getString("familiya");
                    String imya = resultSet.getString("imya");
                    String otchestvo = resultSet.getString("otchestvo");
                    double doplata = resultSet.getDouble("doplata");

                    employee = Optional.of(new Employee(id, familiya, imya, otchestvo, doplata));

                    //LOGGER.log(Level.INFO, "Found {0} in database", employee.get());
                }
            } catch (SQLException ex) {
                //LOGGER.log(Level.SEVERE, null, ex);
            }

            return employee;
        });
    }

    @Override
    public Collection<Employee> getAll() {
        Collection<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String familiya = resultSet.getString("familiya");
                    String imya = resultSet.getString("imya");
                    String otchestvo = resultSet.getString("otchestvo");
                    double doplata = resultSet.getDouble("doplata");

                    Employee employee = new Employee(id, familiya, imya, otchestvo, doplata);

                    employees.add(employee);

                    //LOGGER.log(Level.INFO, "Found {0} in database", employee);
                }

            } catch (SQLException ex) {
                //LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return employees;
    }

    @Override
    public Optional<Integer> save(Employee employee) {
        String message = "The employee to be added should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "INSERT INTO "
                + "employee(familiya, imya, otchestvo, doplata) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullEmployee.getFamiliya());
                statement.setString(2, nonNullEmployee.getImya());
                statement.setString(3, nonNullEmployee.getOtchestvo());
                statement.setDouble(4, nonNullEmployee.getDoplata());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                nonNullEmployee.setId(generatedId.get());
                //LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        //new Object[]{nonNullEmployee, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                //LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Employee employee) {
        String message = "The employee to be updated should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "UPDATE employee "
                + "SET "
                + "familiya = ?, "
                + "imya = ?, "
                + "otchestvo = ? "
                + "doplata = ? "
                + "WHERE "
                + "employee_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullEmployee.getFamiliya());
                statement.setString(2, nonNullEmployee.getImya());
                statement.setString(3, nonNullEmployee.getOtchestvo());
                statement.setDouble(4, nonNullEmployee.getDoplata());
                statement.setInt(5, nonNullEmployee.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                //LOGGER.log(Level.INFO, "Was the employee updated successfully? {0}",
                        //numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                //LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Employee employee) {
        String message = "The employee to be deleted should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "DELETE FROM employee WHERE employee_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullEmployee.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                //LOGGER.log(Level.INFO, "Was the employee deleted successfully? {0}",
                        //numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                //LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}