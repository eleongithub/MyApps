package com.syscom.apps.myapps.activities.secured;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import com.syscom.apps.myapps.activities.LoginActivity;
import com.syscom.apps.myapps.domains.TokenDTO;
import com.syscom.apps.myapps.utilities.WebServiceUtils;

import java.util.HashMap;
import java.util.Map;

import static com.syscom.apps.myapps.utilities.Constants.TOKEN;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.DEFAULT;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.getFromSharedPreferences;

/**
 * Created by Eric LEGBA on 02/04/17.
 * @author  Eric LEGBA
 */

public class SecuredActivity extends AppCompatActivity {

    protected TokenDTO tokenDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String storedToken = getFromSharedPreferences(getApplicationContext(), TOKEN);
        if(storedToken==null || DEFAULT.equals(storedToken)){
            Intent loginIntenet = new Intent(getApplicationContext(),LoginActivity.class);
            loginIntenet.setAction(Intent.ACTION_SEND);
            startActivity(loginIntenet);
            return;
        }
        Gson gson = new Gson();
        tokenDTO = gson.fromJson(storedToken, TokenDTO.class);
    }

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }

    public void setTokenDTO(TokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }


    public Map<String, String> buildSecuredHeaders(){
        Map<String, String> headers =  new HashMap<>();
        headers.put(WebServiceUtils.AUTHORIZATION, tokenDTO.getValue());
        return  headers;
    }

    protected void startDashboardActivity(){
        Intent dashboardIntenet = new Intent(getApplicationContext(),DashBoardActivity.class);
        startActivity(dashboardIntenet);
    }

    protected void startLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);
    }

}
