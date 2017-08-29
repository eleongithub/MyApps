package com.syscom.apps.myapps.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.domains.webservices.CustomerDTO;
import com.syscom.apps.myapps.utilities.MyAppsUtility;
import com.syscom.apps.myapps.utilities.WebServiceUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import static android.text.TextUtils.isEmpty;

/**
 *
 * Activity to register user
 *
 * @author  Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RegisterActivity extends AppCompatActivity {

    protected static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText editTextName = null;
    private EditText editTextFirstName = null;
    private EditText editTextMail = null;
    private EditText editTextPhone = null;
    private EditText editTextPassword = null;
    private TextView editTextError = null;
    private Button btnRegister = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextMail = (EditText)findViewById(R.id.editTextregisterMail);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextError = (TextView)findViewById(R.id.editTextError);
        btnRegister.setOnClickListener(onclickBtnRegister);
    }

    private View.OnClickListener onclickBtnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validInputData()>0){
                return;
            }
            new FetchRegistrerTask().execute();
        }
    };

    // ***************************************
    // Private classe to call Web Service API for register
    // ***************************************
    private class FetchRegistrerTask extends AsyncTask<Void, Void, String> {

        private CustomerDTO customerDTO;
        private boolean error;

        @Override
        protected void onPreExecute() {
            // build the message object
            String name = editTextName.getText().toString();
            String firstName = editTextFirstName.getText().toString();
            String mail = editTextMail.getText().toString();
            String phone = editTextPhone.getText().toString();
            String password = editTextPassword.getText().toString();
            customerDTO = new CustomerDTO.Builder()
                                         .name(name)
                                         .firstName(firstName)
                                         .mail(mail)
                                         .phone(phone)
                                         .password(password)
                                         .build();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CustomerDTO> requestEntity = new HttpEntity<CustomerDTO>(customerDTO, requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, WebServiceUtils.REGISTER_API);
                ResponseEntity<String> responseEntity = restTemplate.exchange(WebServiceUtils.REGISTER_API, HttpMethod.POST, requestEntity, String.class);
                return responseEntity.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getResponseBodyAsString(), e);
                error = true;
                return e.getResponseBodyAsString();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getMessage(), e);
                error = true;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(error){
                editTextError.setText(result);
            }
            Intent loginIntenet = new Intent(getApplicationContext(),LoginActivity.class);
            loginIntenet.setAction(Intent.ACTION_SEND);
            loginIntenet.putExtra(Intent.EXTRA_TEXT, getString(R.string.register_success_message));
            startActivity(loginIntenet);
        }
    }

    private int validInputData(){
        int error = 0;
        if(isEmpty(editTextName.getText().toString())){
            editTextName.setError(getString(R.string.error_name));
            error++;
        }
        if(isEmpty(editTextFirstName.getText().toString())){
            editTextFirstName.setError(getString(R.string.error_firstname));
            error++;
        }
        if(!MyAppsUtility.isValideMail(editTextMail.getText().toString())){
            editTextMail.setError(getString(R.string.error_mail));
            error++;
        }
        if(isEmpty(editTextPhone.getText().toString())){
            editTextPhone.setError(getString(R.string.error_phone));
            error++;
        }
        if(isEmpty(editTextPassword.getText().toString())){
            editTextPassword.setError(getString(R.string.error_password));
            error++;
        }
        return error;
    }
}
