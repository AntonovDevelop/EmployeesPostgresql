package com.company.scenarios;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentScenario {
    private static final DepartmentRepository repo = new DepartmentRepository();
    public static void play(){
        //Добавление покупателей
        List<Department> departments = new ArrayList<>();
        departments.add(new Department("QA", 0, 60));
        departments.add(new Department("PR", 0, 60));
        departments.add(new Department("Mobile", 0, 60));
        for(Department department:departments){
            repo.addDepartment(department).ifPresent(department::setId);
        }
        //Редактируем добавленных прежде в базу покупателей
        //employees.get(0).setFirstName("EmployeeAA");
        //employees.get(0).setLastName("EmployeeAA");
        //employees.get(0).setEmail("AA@gmail.com");
        //repo.updateEmployee(employees.get(0));
        //Получаем всех
        System.out.println("Все отделы:");
        repo.getAllDepartments().forEach(System.out::println);
        //Удаление покупателя
        //deleteEmployee(secondEmployee);
    }
}
