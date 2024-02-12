package br.com.udemy.tasks.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("cep")
    private String zipCode;
    @JsonProperty("logradouro")
    private String street;
    @JsonProperty("complemento")
    private String complement;
    @JsonProperty("bairro")
    private String neighborhood;
    @JsonProperty("localidade")
    private String city;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("uf")
    private String state;
}
