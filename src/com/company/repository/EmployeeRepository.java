package com.company.repository;

import com.company.dao.EmployeeDao;
import com.company.dao.Dao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Employee;
import com.company.model.EmployeeCountName;

import java.util.Collection;
import java.util.Optional;

public class EmployeeRepository {
    private final EmployeeDao employeeDao = new EmployeeDao();

    public Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> employee = employeeDao.get(id);
        return employee.orElseThrow(NonExistentCustomerException::new);
    }

    public Collection<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    public Optional<Integer> addEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
    }

    public Collection<EmployeeCountName> getEmployeesGroupedByNames(){
        return employeeDao.getEmployeesGroupedByNames();
    }

    public Collection<Employee> getEmployeesByName(String name){
        return employeeDao.getByName(name);
    }
    public Collection<Employee> getAllSortedByName(){return employeeDao.getAllSortedByName();}
    public void createIndex(){
        employeeDao.createIndexesOnEmployee();
    }
    public void dropIndex(){
        employeeDao.dropIndexesOnEmployee();
    }
}
