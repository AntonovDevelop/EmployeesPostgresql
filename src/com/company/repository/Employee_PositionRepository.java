package com.company.repository;

import com.company.dao.Dao;
import com.company.dao.Employee_PositionDao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Employee_Position;

import java.util.Collection;
import java.util.Optional;

public class Employee_PositionRepository {
    private static final Dao<Employee_Position, Integer> employee_PositionDao = new Employee_PositionDao();

    public static Employee_Position getEmployee_Position(int id) throws NonExistentEntityException {
        Optional<Employee_Position> employee_Position = employee_PositionDao.get(id);
        return employee_Position.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Employee_Position> getAllEmployee_Positions() {
        return employee_PositionDao.getAll();
    }

    public static void updateEmployee_Position(Employee_Position employee_Position) {
        employee_PositionDao.update(employee_Position);
    }

    public static Optional<Integer> addEmployee_Position(Employee_Position employee_Position) {
        return employee_PositionDao.save(employee_Position);
    }

    public static void deleteEmployee_Position(Employee_Position employee_Position) {
        employee_PositionDao.delete(employee_Position);
    }
}
