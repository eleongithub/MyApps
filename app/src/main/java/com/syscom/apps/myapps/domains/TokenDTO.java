package com.syscom.apps.myapps.domains;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Classe représentant les données d'un jeton d'authentification
 * Created by Eric LEGBA on 26/02/17.
 *@author Eric LEGBA
 */
public class TokenDTO implements Serializable {

    @SerializedName("value")
    private String value;

    @SerializedName("expiration")
    private String expiration;

    @SerializedName("customerDTO")
    private CustomerDTO customerDTO;

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
