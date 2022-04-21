package com.company.scenarios;

import com.company.model.Employee;
import com.company.model.Position;
import com.company.repository.EmployeeRepository;
import com.company.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;

public class PositionScenario {
    private static final PositionRepository repo = new PositionRepository();
    public static void play(){
        //Добавление покупателей
        List<Position> positions = new ArrayList<>();
        positions.add(new Position("Manager", 30000, 1));
        positions.add(new Position("Tester", 45000, 1));
        positions.add(new Position("Teamlead", 50000, 1));
        for(Position position: positions){
            repo.addPosition(position).ifPresent(position::setId);
        }
        //Редактируем добавленных прежде в базу покупателей
        //employees.get(0).setFirstName("EmployeeAA");
        //employees.get(0).setLastName("EmployeeAA");
        //employees.get(0).setEmail("AA@gmail.com");
        //repo.updateEmployee(employees.get(0));
        //Получаем всех
        System.out.println("Все должности:");
        repo.getAllPositions().forEach(System.out::println);
        //Удаление покупателя
        //deleteEmployee(secondEmployee);
    }
}
