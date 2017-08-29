package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;

/**
 * Created by Eric LEGBA on 29/08/17.
 */

public class BaseDTO implements Serializable {

    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
