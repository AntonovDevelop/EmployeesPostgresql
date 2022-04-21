package com.company.model;

import java.util.Date;

public class Salary {
    private Integer id;
    private double value;
    private String date;
    private int EmployeeID;

    public Salary(double value, String date, int EmployeeID) {
        this(null, value, date, EmployeeID);
    }

    public Salary(Integer id, double value, String date, int employeeID) {
        this.id = id;
        this.value = value;
        this.date = date;
        EmployeeID = employeeID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", value=" + value +
                ", date=" + date +
                ", EmployeeID=" + EmployeeID +
                '}';
    }
}
