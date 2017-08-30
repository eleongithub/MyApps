package com.syscom.apps.myapps.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.activities.secured.DashBoardActivity;
import com.syscom.apps.myapps.domains.Session;
import com.syscom.apps.myapps.domains.webservices.TokenDTO;
import com.syscom.apps.myapps.utilities.MyAppsUtility;
import com.syscom.apps.myapps.utilities.WebServiceUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static android.text.TextUtils.isEmpty;
import static com.syscom.apps.myapps.utilities.Constants.SESSION;
import static com.syscom.apps.myapps.utilities.MyAppsUtility.isValideMail;
import static com.syscom.apps.myapps.utilities.SharedPreferencesUtils.saveToSharedPreferences;

/**
 * Activité d'authentification de l'utilisateur
 *
 * @author Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class LoginActivity extends AppCompatActivity {

    protected static final String TAG = LoginActivity.class.getSimpleName();

    private EditText editTextMail = null;
    private EditText editTextPassword = null;
    private TextView textViewLoginError = null;
    private TextView editTextTitleMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        editTextMail = (EditText)findViewById(R.id.editTextMail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewLoginError = (TextView) findViewById(R.id.textViewLoginError);
        editTextTitleMsg = (TextView) findViewById(R.id.titleMessage);
        btnLoginRegister.setOnClickListener(onclickBtnLoginRegister);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                editTextTitleMsg.setText(sharedText);
            }
        }
        btnLogin.setOnClickListener(onclickBtnLogin);
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
            if(validInputData()>0){
                return;
            }
            new FetchLoginTask().execute();
        }
    };

    /**
     * Classe privée pour gérer les appels web Service API pour l'authentification.
     */
    private class FetchLoginTask extends AsyncTask<Void, Void, TokenDTO> {

        private String username;
        private String password;

        @Override
        protected void onPreExecute() {
            // build the message object
            this.username = editTextMail.getText().toString();
            this.password = editTextPassword.getText().toString();
        }

        @Override
        protected TokenDTO doInBackground(Void... params) {
            // Populate the HTTP Basic Authentitcation header with the username and password
            String credentials = String.format("%s:%s",username,password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                String encodedCredentials = new String(Base64.encode(credentials.getBytes(), Base64.NO_WRAP));
                HttpEntity<String> requestEntity = new HttpEntity<>(encodedCredentials, requestHeaders);
                // Make the network request
                Log.d(TAG, WebServiceUtils.LOGIN_API);
                ResponseEntity<TokenDTO> response = restTemplate.exchange(WebServiceUtils.LOGIN_API, HttpMethod.POST, requestEntity, TokenDTO.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getResponseBodyAsString(), e);
                return new TokenDTO(e.getResponseBodyAsString());
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getMessage(), e);
                return new TokenDTO(e.getMessage());
            }
        }
        @Override
        protected void onPostExecute(TokenDTO tokenDTO) {
            if(tokenDTO.getErrorMessage()!=null){
                showErrorResponse(tokenDTO.getErrorMessage());
                return;
            }
            Session session = new Session(tokenDTO,true);
            Gson gson = new Gson();
//          Save tokenDTO into SharedPreferences
            saveToSharedPreferences(getApplicationContext(), SESSION,gson.toJson(session, Session.class));
//          Start dashboard activity
            Intent dashboardIntenet = new Intent(getApplicationContext(),DashBoardActivity.class);
            startActivity(dashboardIntenet);
        }
    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Verfication des saisies clavier
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
