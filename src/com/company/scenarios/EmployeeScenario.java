package com.company.scenarios;

import com.company.model.Employee;
import com.company.repository.EmployeeRepository;

import java.util.Collection;

public class EmployeeScenario {
    EmployeeRepository employeeRepository=new EmployeeRepository();
    Collection<Employee> employees=employeeRepository.getAllEmployees();
    public void playWithIndexes() {
        employeeRepository.createIndex();
        playWithoutIndexes();
        employeeRepository.dropIndex();
    }
    public void playWithoutIndexes(){
        Collection<Employee> employees=employeeRepository.getEmployeesByName("Dima");
        if(employees.size()==0) {
            employees = employeeRepository.getEmployeesByName("Qwerty");
            employees.forEach(employee -> employee.setImya("Dima"));
        }
        employees.forEach(employee -> employee.setImya("Qwerty"));
        //ищет и меняет
    }
}
