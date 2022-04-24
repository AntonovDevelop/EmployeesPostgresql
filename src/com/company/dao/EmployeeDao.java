package com.company.dao;

import com.company.model.Employee;
import com.company.model.EmployeeCountName;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class EmployeeDao implements Dao<Employee, Integer> {

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
                }
            } catch (SQLException ex) {
                System.out.println(ex);
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
                }

            } catch (SQLException ex) {
                System.out.println(ex);
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
            } catch (SQLException ex) {
                System.out.println(ex);
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
                + "otchestvo = ?, "
                + "doplata = ? "
                + "WHERE "
                + "id = ?;";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullEmployee.getFamiliya());
                statement.setString(2, nonNullEmployee.getImya());
                statement.setString(3, nonNullEmployee.getOtchestvo());
                statement.setDouble(4, nonNullEmployee.getDoplata());
                statement.setInt(5, nonNullEmployee.getId());

                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
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

                statement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
    }
    public Collection<Employee> getByName(String name) {
        Collection<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE imya = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String familiya = resultSet.getString("familiya");
                    String imya = resultSet.getString("imya");
                    String otchestvo = resultSet.getString("otchestvo");
                    double doplata = resultSet.getDouble("doplata");

                    Employee employee = new Employee(id, familiya, imya, otchestvo, doplata);
                    employees.add(employee);
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        return employees;
    }

    public Collection<EmployeeCountName> getEmployeesGroupedByNames() {
        Collection<EmployeeCountName> employees = new ArrayList<>();
        String sql = "SELECT COUNT(id), imya\n" +
                "FROM Employee\n" +
                "GROUP BY imya;";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    String imya = resultSet.getString("imya");

                    EmployeeCountName employee = new EmployeeCountName(count,imya);

                    employees.add(employee);
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        return employees;
    }
    public Collection<Employee> getAllSortedByName() {
        Collection<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY imya ASC;";

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
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        return employees;
    }
    public void createIndexesOnEmployee() {
        String sql = "create index index_fio on employee USING hash(imya);";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
            } catch (SQLException ex) {
                //System.out.println(ex);
            }
        });
    }
    public void dropIndexesOnEmployee() {
        String sql = "drop index index_fio;";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                //System.out.println(resultSet);
            } catch (SQLException ex) {
                //System.out.println(ex);
            }
        });
    }
}