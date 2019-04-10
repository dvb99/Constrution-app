package com.example.admin.constructionsite;

import java.io.Serializable;

public class workInfo implements Serializable{
    private String material;
    private String material_type;
    private String diameter;
    private float today;
    private String uptodate;

    public workInfo()
    {

    }

    public workInfo(String material, String material_type, String diameter, float today, String uptodate) {

        this.material = material;
        this.material_type = material_type;
        this.diameter = diameter;
        this.today = today;
        this.uptodate = uptodate;
    }

    public String getMaterial() {
        return material;
    }

    public String getMaterial_type() {
        return material_type;
    }

    public String getDiameter() {
        return diameter;
    }

    public float getToday() {
        return today;
    }

    public String getUptodate() {
        return uptodate;
    }
}
