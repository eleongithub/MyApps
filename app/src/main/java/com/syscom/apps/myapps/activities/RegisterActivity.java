package com.syscom.apps.myapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.syscom.apps.myapps.domains.CustomerDTO;
import com.syscom.apps.myapps.rest.GsonRequest;
import com.syscom.apps.myapps.utilities.MyAppsUtility;
import com.syscom.apps.myapps.utilities.WebServiceUtils;

import java.io.UnsupportedEncodingException;

import static android.text.TextUtils.isEmpty;

/**
 *
 * Activity to register user
 *
 * @author  Eric LEGBA
 */
public class RegisterActivity extends AppCompatActivity {

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
            int error = validInputData();
            if(error==0){
                String name = editTextName.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String mail = editTextMail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();
                CustomerDTO customerDTO = new CustomerDTO.Builder()
                                                .name(name)
                                                .firstName(firstName)
                                                .mail(mail)
                                                .phone(phone)
                                                .password(password)
                                                .build();
                Gson gson = new Gson();
                final String json = gson.toJson(customerDTO);
//                Call Web Service for authentication
                GsonRequest<String> registerRequest = new GsonRequest<String>(
                        Request.Method.POST,
                        WebServiceUtils.REGISTER_API,
                        String.class,
                        null,
                        onRequestSuccessListener(),
                        onRequestErrorListener()
                ){
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return json == null ? null : json.getBytes(WebServiceUtils.UTF8_ENCODING);
                        } catch (UnsupportedEncodingException uee) {
                            return null;
                        }
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(registerRequest);
            }
        }

        private Response.Listener<String> onRequestSuccessListener() {
            return new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Forward to login screen
                    Intent loginIntenet = new Intent(getApplicationContext(),LoginActivity.class);
                    loginIntenet.setAction(Intent.ACTION_SEND);
                    loginIntenet.putExtra(Intent.EXTRA_TEXT, getString(R.string.register_success_message));
                    startActivity(loginIntenet);
                }
            };
        }

        private Response.ErrorListener onRequestErrorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if(response != null && response.data != null){
                        switch(response.statusCode) {
                            case 400:case 401: case 403:
                                String erroMessage = new String(response.data);
                                editTextError.setText(erroMessage);
                                break;
                            case 500:case 501:case 502:case 503:case 504: case 505:
                                editTextError.setText(getString(R.string.error_server_500));
                                break;
                            default:
                                editTextError.setText(getString(R.string.unexpected_error_server));
                                break;
                        }
                    }
                }
            };
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
    };








}
