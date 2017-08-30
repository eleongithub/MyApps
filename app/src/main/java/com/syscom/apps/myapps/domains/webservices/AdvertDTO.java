package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;

/**
 *Classe représentant une annonce
 *
 * Created by Eric LEGBA on 02/04/17.
 */

public class AdvertDTO implements Serializable {

    private String title;
    private String description;
    private Float price;
    private CustomerDTO customerDTO;

    public AdvertDTO(String title, String description, Float price, CustomerDTO customerDTO){
        this.title = title;
        this.description = description;
        this.price = price;
        this.customerDTO = customerDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

}
