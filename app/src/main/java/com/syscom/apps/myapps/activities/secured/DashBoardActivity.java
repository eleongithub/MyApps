package com.syscom.apps.myapps.activities.secured;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.activities.LoginActivity;

import static com.syscom.apps.myapps.utilities.Constants.SESSION;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.removeFromPrefs;

/**
 * Activité Dashboard
 *
 * @author Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class DashBoardActivity extends SecuredActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        TextView textViewName = (TextView) findViewById(R.id.name);
        Button btnCreateAdvice = (Button)findViewById(R.id.btnCreateAdvice);
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        textViewName.setText(session.getTokenDTO().getCustomerDTO().getName());
        btnLogout.setOnClickListener(onclickBtnLogout);
        btnCreateAdvice.setOnClickListener(onclickBtnCreateAdvert);

    }

    private View.OnClickListener onclickBtnCreateAdvert = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent advertActivity = new Intent(getApplicationContext(), AdvertActivity.class);
            startActivity(advertActivity);
        }
    };

    private View.OnClickListener onclickBtnLogout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeFromPrefs(getApplicationContext(), SESSION);
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }
    };

}
