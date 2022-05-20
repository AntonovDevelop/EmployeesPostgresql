package com.company.dao;

import com.company.model.Department;
import com.company.model.Mongo;
import com.company.utils.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDao implements Dao<Mongo, Integer>{
    private static final Logger LOGGER = Logger.getLogger(DepartmentDao.class.getName());
    private final Optional<Connection> connection;

    public MongoDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Collection<Mongo> getAll() {
        Collection<Mongo> mongos = new ArrayList<>();
        String sql = "SELECT Employee.imya, Employee.familiya, Employee.otchestvo, Employee.doplata,\n" +
                "Department.imya AS department_name, Position.imya AS position_name, Position.oklad, \n" +
                "Employee_Position.stavka\n" +
                "FROM Employee\n" +
                "JOIN Employee_Position ON Employee_Position.EmployeeID = Employee.ID\n" +
                "JOIN Position ON Employee_Position.PositionID = Position.ID\n" +
                "JOIN Department ON Position.DepartmentID = Department.ID\n" +
                "ORDER BY Employee.ID";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String imya = resultSet.getString("imya");
                    String familiya = resultSet.getString("familiya");
                    String otchestvo = resultSet.getString("otchestvo");
                    double doplata = resultSet.getDouble("doplata");
                    String department_name = resultSet.getString("department_name");
                    String position_name = resultSet.getString("position_name");
                    double oklad = resultSet.getDouble("oklad");
                    double stavka = resultSet.getDouble("stavka");

                    Mongo mongo=new Mongo(imya,familiya,otchestvo,doplata, department_name,position_name,oklad,stavka);
                    mongos.add(mongo);

                    //LOGGER.log(Level.INFO, "Found {0} in database", mongo);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return mongos;
    }

    @Override
    public Optional<Mongo> get(int id) {
        return Optional.empty();
    }
    @Override
    public Optional<Integer> save(Mongo mongo) { return Optional.empty(); }
    @Override
    public void update(Mongo mongo) { }
    @Override
    public void delete(Mongo mongo) { }
}
