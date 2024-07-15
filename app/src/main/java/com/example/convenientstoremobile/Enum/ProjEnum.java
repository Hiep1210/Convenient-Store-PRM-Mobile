package com.example.convenientstoremobile.Enum;

public enum ProjEnum {
    MyPREFERENCES("PREF"), ProdPREFERENCE("PROD"), ORDPREFERENCE("ORDR");
    private String value;
    ProjEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
