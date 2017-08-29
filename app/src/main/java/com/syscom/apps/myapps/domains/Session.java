package com.syscom.apps.myapps.domains;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 * Created by Eric LEGBA on 26/02/17.
 * @author Eric LEGBA
 */

public class Session implements Serializable {

    @SerializedName("tokenDTO")
    private TokenDTO tokenDTO;

    @SerializedName("logged")
    private  boolean logged;

    public Session(){

    }

    public Session(TokenDTO tokenDTO, boolean logged) {
        this.tokenDTO = tokenDTO;
        this.logged = logged;
    }

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }

    public void setTokenDTO(TokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
