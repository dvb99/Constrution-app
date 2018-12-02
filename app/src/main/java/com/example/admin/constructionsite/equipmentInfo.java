package com.example.admin.constructionsite;

public class equipmentInfo {
    private String equipmentWithNumberplate;
    private String initialReading;
    private String finalReading;

    public equipmentInfo() {
    }

    public equipmentInfo(String equipmentWithNumberplate, String initialReading, String finalReading) {
        this.equipmentWithNumberplate = equipmentWithNumberplate;
        this.initialReading = initialReading;
        this.finalReading = finalReading;
    }

    public String getEquipmentWithNumberplate() {
        return equipmentWithNumberplate;
    }

    public void setEquipmentWithNumberplate(String equipmentWithNumberplate) {
        this.equipmentWithNumberplate = equipmentWithNumberplate;
    }

    public String getInitialReading() {
        return initialReading;
    }

    public void setInitialReading(String initialReading) {
        this.initialReading = initialReading;
    }

    public String getFinalReading() {
        return finalReading;
    }

    public void setFinalReading(String finalReading) {
        this.finalReading = finalReading;
    }
}
