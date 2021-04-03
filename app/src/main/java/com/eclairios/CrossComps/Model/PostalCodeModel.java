package com.eclairios.CrossComps.Model;

public class PostalCodeModel {

    String postalCode_id,postalCode;

    public PostalCodeModel() {
    }

    public String getPostalCode_id() {
        return postalCode_id;
    }

    public void setPostalCode_id(String postalCode_id) {
        this.postalCode_id = postalCode_id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return postalCode;
    }
}
