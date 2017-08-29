package com.syscom.apps.myapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.VolleySingleton;
import com.syscom.apps.myapps.activities.secured.DashBoardActivity;
import com.syscom.apps.myapps.domains.Session;
import com.syscom.apps.myapps.domains.TokenDTO;
import com.syscom.apps.myapps.rest.GsonRequest;
import com.syscom.apps.myapps.utilities.MyAppsUtility;
import com.syscom.apps.myapps.utilities.WebServiceUtils;
import java.io.UnsupportedEncodingException;
import static android.text.TextUtils.isEmpty;
import static com.syscom.apps.myapps.utilities.Constants.SESSION;
import static com.syscom.apps.myapps.utilities.Constants.TOKEN;
import static com.syscom.apps.myapps.utilities.MyAppsUtility.isValideMail;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.saveToSharedPreferences;

/**
 * Login Activity
 *
 * @author Eric LEGBA
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editTextMail = null;
    private EditText editTextPassword = null;
    private TextView textViewLoginError = null;
    private TextView editTextTitleMsg = null;
    private Button btnLogin = null;
    private Button btnLoginRegister = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        editTextMail = (EditText)findViewById(R.id.editTextMail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewLoginError = (TextView) findViewById(R.id.textViewLoginError);
        editTextTitleMsg = (TextView) findViewById(R.id.titleMessage);
        btnLogin.setOnClickListener(onclickBtnLogin);
        btnLoginRegister.setOnClickListener(onclickBtnLoginRegister);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                editTextTitleMsg.setText(sharedText);
            }
        }
    }


    /**
     *
     */
    private View.OnClickListener onclickBtnLoginRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(registerIntent);
        }
    };


    /**
     *
     */
    private View.OnClickListener onclickBtnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textViewLoginError.setText(MyAppsUtility.EMPTY);
            editTextTitleMsg.setText(getString(R.string.login_title));
            int error = validInputData();
            if(error==0){
                String mail = editTextMail.getText().toString();
                String password = editTextPassword.getText().toString();
                String credentials = String.format("%s:%s",mail,password);
                final String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
//                Call Web Service for authentication
                GsonRequest<TokenDTO> loginRequest = new GsonRequest<TokenDTO>(
                        Request.Method.POST,
                        WebServiceUtils.LOGIN_API,
                        TokenDTO.class,
                        null,
                        onRequestSuccessListener(),
                        onRequestErrorListener()
                ){
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return encodedCredentials == null ? null : encodedCredentials.getBytes(WebServiceUtils.UTF8_ENCODING);
                        } catch (UnsupportedEncodingException uee) {
                            return null;
                        }
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);

            }
        }
    };

    private Response.Listener<TokenDTO> onRequestSuccessListener() {
        return new Response.Listener<TokenDTO>() {
            @Override
            public void onResponse(TokenDTO response) {
                Gson gson = new Gson();
                Session session = new Session(response,true);
                String sessionValue = gson.toJson(session, Session.class);
//                    Save tokenDTO into SharedPreferences
                saveToSharedPreferences(getApplicationContext(), TOKEN,gson.toJson(response,TokenDTO.class));
//                    Save tokenDTO into SharedPreferences
                saveToSharedPreferences(getApplicationContext(), SESSION,sessionValue);
//                      Start dashboard activity
                Intent dashboardIntenet = new Intent(getApplicationContext(),DashBoardActivity.class);
                startActivity(dashboardIntenet);
            }
        };
    }

    private Response.ErrorListener onRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                switch(response.statusCode) {
                    case 400:
                    case 403:
                        String erroMessage = new String(response.data);
                        textViewLoginError.setText(erroMessage);
                        break;
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                    case 504:
                    case 505:
                        textViewLoginError.setText(getString(R.string.error_server_500));
                        break;
                    default:
                        textViewLoginError.setText(getString(R.string.unexpected_error_server));
                        break;
                }
            }
        };
    }


    /**
     *
     * @return {@link int}
     */
    private int validInputData(){
        int error = 0;
        if(!isValideMail(editTextMail.getText().toString())){
            editTextMail.setError(getString(R.string.error_mail));
            error++;
        }

        if(isEmpty(editTextPassword.getText().toString())){
            editTextPassword.setError(getString(R.string.error_enter_password));
            error++;
        }
        return error;
    }
}
