package com.syscom.apps.myapps.activities.secured;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.syscom.apps.myapps.activities.LoginActivity;

import static com.syscom.apps.myapps.utilities.Constants.SESSION;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.DEFAULT;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.getFromSharedPreferences;

/**
 * Created by Eric LEGBA on 02/04/17.
 * @author  Eric LEGBA
 */

public class SecuredActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String storedSession = getFromSharedPreferences(getApplicationContext(), SESSION);
        if(storedSession==null || DEFAULT.equals(storedSession)){
            Intent loginIntenet = new Intent(getApplicationContext(),LoginActivity.class);
            loginIntenet.setAction(Intent.ACTION_SEND);
            startActivity(loginIntenet);
            return;
        }
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
