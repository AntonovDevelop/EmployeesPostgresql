package com.company.scenarios;

import com.company.repository.EmployeeRepository;

public class EmployeeScenario {
    private static final EmployeeRepository repo = new EmployeeRepository();
    public static void play(){
        //Добавление покупателей
//        List<Employee> employees = new ArrayList<>();
//        employees.add(new Employee("Ella", "Beldom", "Alexovich", 12000));
//        employees.add(new Employee("Thalia", "Behly", "Belovich", 25000));
//        employees.add(new Employee("Shina", "Albany", "Sarovich", 20000));
//        for(Employee employee: employees){
//            repo.addEmployee(employee).ifPresent(employee::setId);
//        }
        //Редактируем добавленных прежде в базу покупателей
        //employees.get(0).setFirstName("EmployeeAA");
        //employees.get(0).setLastName("EmployeeAA");
        //employees.get(0).setEmail("AA@gmail.com");
        //repo.updateEmployee(employees.get(0));
        //Получаем всех
        System.out.println("Все работники:");
        long sum = 0;
        for(int i=0;i<10;i++) {
            long startTime = System.currentTimeMillis();
            repo.getAllEmployees();
            long stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        System.out.println(sum/10);
        //Удаление покупателя
        //deleteEmployee(secondEmployee);
    }
}
