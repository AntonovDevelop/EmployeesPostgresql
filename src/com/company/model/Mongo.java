package com.company.model;

public class Mongo {
    private String imya;
    private String familiya;
    private String otchestvo;
    private double doplata;
    private String department_name;
    private String position_name;
    private double oklad;
    private double stavka;

    public Mongo(String imya, String familiya, String otchestvo, double doplata, String department_name, String position_name, double oklad, double stavka) {
        this.imya = imya;
        this.familiya = familiya;
        this.otchestvo = otchestvo;
        this.doplata = doplata;
        this.department_name = department_name;
        this.position_name = position_name;
        this.oklad = oklad;
        this.stavka = stavka;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
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

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public double getOklad() {
        return oklad;
    }

    public void setOklad(double oklad) {
        this.oklad = oklad;
    }

    public double getStavka() {
        return stavka;
    }

    public void setStavka(double stavka) {
        this.stavka = stavka;
    }
}
