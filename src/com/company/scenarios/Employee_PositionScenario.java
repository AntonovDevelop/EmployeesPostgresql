package com.company.scenarios;

import com.company.model.Employee;
import com.company.model.Employee_Position;
import com.company.repository.EmployeeRepository;
import com.company.repository.Employee_PositionRepository;

import java.util.ArrayList;
import java.util.List;

public class Employee_PositionScenario {
    private static final Employee_PositionRepository repo = new Employee_PositionRepository();
    public static void play(){
        //Добавление покупателей
        List<Employee_Position> employee_positions = new ArrayList<>();
        employee_positions.add(new Employee_Position(1, 1, 1.25));
        employee_positions.add(new Employee_Position(1, 2, 0.25));
        employee_positions.add(new Employee_Position(2, 2, 1.5));
        for(Employee_Position employee_position: employee_positions){
            repo.addEmployee_Position(employee_position).ifPresent(employee_position::setId);
        }
        //Редактируем добавленных прежде в базу покупателей
        //employees.get(0).setFirstName("EmployeeAA");
        //employees.get(0).setLastName("EmployeeAA");
        //employees.get(0).setEmail("AA@gmail.com");
        //repo.updateEmployee(employees.get(0));
        //Получаем всех
        System.out.println("Все сотрудники на должности:");
        repo.getAllEmployee_Positions().forEach(System.out::println);
        //Удаление покупателя
        //deleteEmployee(secondEmployee);
    }
}
