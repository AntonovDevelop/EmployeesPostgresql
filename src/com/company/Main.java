package com.company;

import com.company.scenarios.CustomerScenario;
import com.company.scenarios.DepartmentScenario;
import com.company.scenarios.EmployeeScenario;
import com.company.utils.JdbcConnection;

public class Main {

    public static void main(String[] args) {
        //CustomerScenario.play();
        EmployeeScenario.play();
        //DepartmentScenario.play();
    }
}
