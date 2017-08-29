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
 * Dashboard activity
 * @author Eric LEGBA
 */

public class DashBoardActivity extends SecuredActivity {

    private TextView textViewName = null;
    private Button btnLogout = null;
    private Button btnCreateAdvice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        textViewName = (TextView) findViewById(R.id.name);
        btnCreateAdvice = (Button)findViewById(R.id.btnCreateAdvice);
        btnLogout = (Button) findViewById(R.id.btnLogout);
//        textViewName.setText(tokenDTO.getCustomerDTO().getName()); TODO
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
