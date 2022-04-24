package com.company.model;

public class Employee {
    private Integer id;
    private String familiya;
    private String imya;
    private String otchestvo;
    private double doplata;

    public Employee(String familiya, String imya, String otchestvo, double doplata) {
        this(null, familiya, imya, otchestvo, doplata);
    }

    public Employee(Integer id, String familiya, String imya, String otchestvo, double doplata) {
        this.id = id;
        this.familiya = familiya;
        this.imya = imya;
        this.otchestvo = otchestvo;
        this.doplata = doplata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public double getDoplata() {
        return doplata;
    }

    public void setDoplata(double doplata) {
        this.doplata = doplata;
    }

    @Override
    public String toString() {
        return "Сотрудник{" +
                "familiya='" + familiya + '\'' +
                ", imya='" + imya + '\'' +
                ", otchestvo='" + otchestvo + '\'' +
                ", doplata=" + doplata +
                '}';
    }
}
