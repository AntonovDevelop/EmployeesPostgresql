package com.company.model;

public class Employee_Position {
    private Integer id;
    private int EmployeeID;
    private int PositionID;
    private double stavka;

    public Employee_Position(int EmployeeID, int PositionID, double stavka) {
        this(null, EmployeeID, PositionID, stavka);
    }

    public Employee_Position(Integer id, int employeeID, int positionID, double stavka) {
        this.id = id;
        this.stavka = stavka;
        EmployeeID = employeeID;
        PositionID = positionID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getStavka() {
        return stavka;
    }

    public void setStavka(double stavka) {
        this.stavka = stavka;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int positionID) {
        PositionID = positionID;
    }

    @Override
    public String toString() {
        return "Employee_Position{" +
                "id=" + id +
                ", EmployeeID=" + EmployeeID +
                ", PositionID=" + PositionID +
                ", stavka=" + stavka +
                '}';
    }
}
