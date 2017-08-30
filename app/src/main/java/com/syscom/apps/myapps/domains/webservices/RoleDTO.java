package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric LEGBA on 30/08/17.
 */

public class RoleDTO implements Serializable {

    private String name;
    private String code;
    private List<FunctionDTO> functionDTOs;

    public RoleDTO(){
        functionDTOs = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FunctionDTO> getFunctionDTOs() {
        return functionDTOs;
    }

    public void setFunctionDTOs(List<FunctionDTO> functionDTOs) {
        this.functionDTOs = functionDTOs;
    }
}
