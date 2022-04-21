package com.company.repository;

import com.company.dao.EmployeeDao;
import com.company.dao.Dao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Employee;

import java.util.Collection;
import java.util.Optional;

public class EmployeeRepository {
    private static final Dao<Employee, Integer> employeeDao = new EmployeeDao();

    public static Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> employee = employeeDao.get(id);
        return employee.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public static void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    public static Optional<Integer> addEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    public static void deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
    }
}
