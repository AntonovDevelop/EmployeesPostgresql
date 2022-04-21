package com.company.scenarios;

import com.company.model.Salary;
import com.company.repository.SalaryRepository;

import java.util.ArrayList;
import java.util.List;

public class SalaryScenario {
    private static final SalaryRepository repo = new SalaryRepository();
    public static void play(){
        //Добавление покупателей
        List<Salary> salarys = new ArrayList<>();
        salarys.add(new Salary(20000, "2021-07-22", 1));
        salarys.add(new Salary(25000, "2021-07-22", 2));
        salarys.add(new Salary(30000, "2021-07-22", 3));
        for(Salary salary: salarys){
            repo.addSalary(salary).ifPresent(salary::setId);
        }
        //Редактируем добавленных прежде в базу покупателей
        //employees.get(0).setFirstName("EmployeeAA");
        //employees.get(0).setLastName("EmployeeAA");
        //employees.get(0).setEmail("AA@gmail.com");
        //repo.updateEmployee(employees.get(0));
        //Получаем всех
        System.out.println("Все должности:");
        repo.getAllSalarys().forEach(System.out::println);
        //Удаление покупателя
        //deleteEmployee(secondEmployee);
    }
}
