package com.company;

import com.company.scenarios.EmployeeScenario;

public class Main {

    public static void main(String[] args) {
        EmployeeScenario employeeScenario=new EmployeeScenario();

        int sum=0;
        for(int i=0;i<10;i++) {
            long startTime = System.currentTimeMillis();
            employeeScenario.playWithoutIndexes();
            long stopTime = System.currentTimeMillis();
            System.out.println(stopTime-startTime);
            sum+=stopTime-startTime;
        }
        System.out.println("Среднее :" + sum/10);
    }
}
