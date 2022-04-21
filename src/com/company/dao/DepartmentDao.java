package com.company.dao;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDao implements Dao<Department, Integer>{
    private static final Logger LOGGER = Logger.getLogger(DepartmentDao.class.getName());
    private final Optional<Connection> connection;

    public DepartmentDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Department> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Department> department = Optional.empty();
            String sql = "SELECT * FROM department WHERE department_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String imya = resultSet.getString("imya");
                    int employee_number = resultSet.getInt("employee_number");
                    int employee_max_number = resultSet.getInt("employee_max_number");

                    department = Optional.of(new Department(id, imya, employee_number, employee_max_number));

                    LOGGER.log(Level.INFO, "Found {0} in database", department.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return department;
        });
    }

    @Override
    public Collection<Department> getAll() {
        Collection<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String imya = resultSet.getString("imya");
                    int employee_number = resultSet.getInt("employee_number");
                    int employee_max_number = resultSet.getInt("employee_max_number");

                    Department department = new Department(id, imya, employee_number, employee_max_number);

                    departments.add(department);

                    LOGGER.log(Level.INFO, "Found {0} in database", department);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return departments;
    }

    @Override
    public Optional<Integer> save(Department department) {
        String message = "The department to be added should not be null";
        Department nonNullDepartment = Objects.requireNonNull(department, message);
        String sql = "INSERT INTO "
                + "department(imya, employee_number, employee_max_number) "
                + "VALUES(?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullDepartment.getImya());
                statement.setInt(2, nonNullDepartment.getEmployee_number());
                statement.setInt(3, nonNullDepartment.getEmployee_max_number());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                nonNullDepartment.setId(generatedId.get());
                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullDepartment, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Department department) {
        String message = "The department to be updated should not be null";
        Department nonNullDepartment = Objects.requireNonNull(department, message);
        String sql = "UPDATE department "
                + "SET "
                + "imya = ?, "
                + "employee_number = ? "
                + "employee_max_number = ? "
                + "WHERE "
                + "department_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullDepartment.getImya());
                statement.setInt(2, nonNullDepartment.getEmployee_number());
                statement.setInt(3, nonNullDepartment.getEmployee_max_number());
                statement.setInt(4, nonNullDepartment.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the department updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Department department) {
        String message = "The department to be deleted should not be null";
        Department nonNullDepartment = Objects.requireNonNull(department, message);
        String sql = "DELETE FROM department WHERE department_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullDepartment.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the department deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
