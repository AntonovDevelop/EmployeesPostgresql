package com.company;

import com.company.model.Employee;
import com.company.repository.EmployeeRepository;
import com.company.scenarios.EmployeeScenario;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var employeeScenario=new EmployeeScenario();
            sc();
        }
    public static void sc(){
        var repo=new EmployeeRepository();
        var sc=new Scanner(System.in);
        boolean exit=false;
        while (!exit) {
            System.out.println("1-Создать, 2-Обновить," +
                    " 3-Удалить, 4-Прочитать одного, 5-Прочитать всех 6-Выйти");
            int selection = sc.nextInt();
            int id;
            double doplata;
            String familiya, imya, otchestvo = "";
            switch (selection) {
                case 1:
                    System.out.println("Введите фио и доплату сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    doplata = sc.nextDouble();
                    Employee em=new Employee(familiya, imya, otchestvo, doplata);
                    System.out.println(em);
                    repo.addEmployee(new Employee(familiya, imya, otchestvo, doplata));
                    break;
                case 2:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                System.out.println("Введите фио отдельно и доплату измененного сотрудника");
                                familiya = sc.next();
                                imya = sc.next();
                                otchestvo = sc.next();
                                doplata = sc.nextDouble();
                                employee.setImya(imya);
                                employee.setFamiliya(familiya);
                                employee.setOtchestvo(otchestvo);
                                employee.setDoplata(doplata);
                                repo.updateEmployee(employee);
                                break;
                            }
                    }
                    else {
                        System.out.println("Нет такого человека!");
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                repo.deleteEmployee(employee);
                                System.out.println("Удалили " + employee);
                                break;
                            }
                        }
                    break;
                case 4:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                System.out.println(employee);
                            }
                        }
                    break;
                case 5:
                    System.out.println("Сотрудники");
                    for(var employee:repo.getAllEmployees()){
                        System.out.println(employee);
                    }
                    break;
                case 6:
                    exit=true;
                    break;
            }
        }
    }
    public static double sc1(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playUpdates();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    public static double sc2(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playGetEmployeesByName();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    public static double sc3(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playGetEmployeesGroupedByName();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    //        employeeScenario.createIndexes();
    //        double res = sc2(employeeScenario);
    //        employeeScenario.dropIndexes();
    //        System.out.println("Среднее :" + res);
}
