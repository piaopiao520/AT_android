package com.example.administrator.atandroid.Citythree;

public class DistrictModel {
    private String name;

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "DistrictModel [name=" + name + "]";
    }

}
