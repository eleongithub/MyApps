package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;

/**
 * Classe représentant les données d'un jeton d'authentification
 * Created by Eric LEGBA on 26/02/17.
 *@author Eric LEGBA
 */
public class TokenDTO extends BaseDTO {

    private String value;

    private String expiration;

    private CustomerDTO customerDTO;

    public TokenDTO(){

    }

    public TokenDTO(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
