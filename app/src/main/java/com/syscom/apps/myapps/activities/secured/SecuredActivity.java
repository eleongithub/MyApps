package com.syscom.apps.myapps.activities.secured;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.syscom.apps.myapps.activities.LoginActivity;
import com.syscom.apps.myapps.domains.Session;

import static com.syscom.apps.myapps.utilities.Constants.SESSION;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.DEFAULT;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.getFromSharedPreferences;

/**
 * Created by Eric LEGBA on 02/04/17.
 * @author  Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class SecuredActivity extends AppCompatActivity {

    protected Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String storedSession = getFromSharedPreferences(getApplicationContext(), SESSION);
        if(storedSession==null || DEFAULT.equals(storedSession)){
            startLoginActivity();
            return;
        }
        session = new Gson().fromJson(storedSession, Session.class);
    }


    protected void startDashboardActivity(){
        Intent dashboardIntenet = new Intent(getApplicationContext(),DashBoardActivity.class);
        startActivity(dashboardIntenet);
    }

    protected void startLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        loginIntent.setAction(Intent.ACTION_SEND);
        startActivity(loginIntent);
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
