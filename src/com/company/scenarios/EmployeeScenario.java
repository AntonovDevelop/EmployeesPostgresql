package com.company.scenarios;

import com.company.model.Customer;
import com.company.model.Employee;
import com.company.model.EmployeeCountName;
import com.company.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeScenario {
    EmployeeRepository employeeRepository=new EmployeeRepository();
    Collection<Employee> employees=employeeRepository.getAllEmployees();
    public void createIndexes(){employeeRepository.createIndex();}
    public void dropIndexes(){employeeRepository.dropIndex();}
    public void playGetEmployeesByName(){
        Collection<Employee> employees=employeeRepository.getEmployeesByName("Qwerty");
    }
    public void playGetEmployeesGroupedByName(){
        Collection<EmployeeCountName> employeeCountNames=employeeRepository.getEmployeesGroupedByNames();
    }
    public void playUpdates(){
        employees = employeeRepository.getEmployeesByName("Qwerty");
        if(employees.size()==0) {
            employees = employeeRepository.getEmployeesByName("Dima");
            employees.forEach(employee -> employee.setImya("Qwerty"));
        }
        employees.forEach(employee -> employee.setImya("Dima"));

    }
    public void playAddEmployees(List<Employee> employees){
        for(Employee employee: employees) {
            employeeRepository.addEmployee(employee).ifPresent(employee::setId);
        }
    }
    public void playUpdateEmployees(List<Employee> employees){
        for(Employee employee: employees) {
            employeeRepository.updateEmployee(employee);
        }
    }
    public Collection<Employee> playReadEmployees(){
        return employeeRepository.getAllEmployees();
    }
    public void playDeleteEmployees(Employee employee){
        employeeRepository.deleteEmployee(employee);
    }
}
