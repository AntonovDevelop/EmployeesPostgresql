package com.company.model;

public class Department {
    private Integer id;
    private String imya;
    private int employee_number;
    private int employee_max_number;

    public Department(Integer id, String imya, int employee_number, int employee_max_number) {
        this.id = id;
        this.imya = imya;
        this.employee_number = employee_number;
        this.employee_max_number = employee_max_number;
    }

    public Department(String imya, int employee_number, int employee_max_number) {
        this(null, imya, employee_number, employee_max_number);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public int getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(int employee_number) {
        this.employee_number = employee_number;
    }

    public int getEmployee_max_number() {
        return employee_max_number;
    }

    public void setEmployee_max_number(int employee_max_number) {
        this.employee_max_number = employee_max_number;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", imya='" + imya + '\'' +
                ", employee_number=" + employee_number +
                ", employee_max_number=" + employee_max_number +
                '}';
    }
}
