package com.example.groundhopping_mobile.data.model;

public class Vehicule {
    private Integer id;
    private String model;
    private String type;
    private Double Consumption;

    public Vehicule() {

    }

    public Vehicule(Integer id, String model, String type, Double consumption) {
        this.id = id;
        this.model = model;
        this.type = type;
        Consumption = consumption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getConsumption() {
        return Consumption;
    }

    public void setConsumption(Double consumption) {
        Consumption = consumption;
    }
}
