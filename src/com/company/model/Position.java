package com.company.model;

public class Position {
    private Integer id;
    private String imya;
    private double oklad;
    private int DepartmentID;

    public Position(String imya, double oklad, int DepartmentID) {
        this(null, imya, oklad, DepartmentID);
    }

    public Position(Integer id, String imya, double oklad, int departmentID) {
        this.id = id;
        this.imya = imya;
        this.oklad = oklad;
        DepartmentID = departmentID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public double getOklad() {
        return oklad;
    }

    public void setOklad(double oklad) {
        this.oklad = oklad;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", imya='" + imya + '\'' +
                ", oklad=" + oklad +
                ", DepartmentID=" + DepartmentID +
                '}';
    }
}
