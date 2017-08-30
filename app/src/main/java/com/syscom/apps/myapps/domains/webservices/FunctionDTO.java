package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;

/**
 * Created by Eric LEGBA on 30/08/17.
 */

public class FunctionDTO implements Serializable {

    private String code;
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
