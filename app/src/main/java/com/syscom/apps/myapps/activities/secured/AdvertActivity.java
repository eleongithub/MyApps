package com.syscom.apps.myapps.activities.secured;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.VolleySingleton;
import com.syscom.apps.myapps.domains.AdvertDTO;
import com.syscom.apps.myapps.domains.CustomerDTO;
import com.syscom.apps.myapps.rest.GsonRequest;
import com.syscom.apps.myapps.utilities.WebServiceUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

import static android.text.TextUtils.isEmpty;

/**
 * Activité pour gérer les annonces
 *
 * @author Eric LEGBA
 */

public class AdvertActivity extends SecuredActivity {

    private EditText title;
    private EditText description;
    private EditText price;
    private TextView textViewError;
    private Button btnPublish;
    private Button btnCancel;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_advert);
        title = (EditText) findViewById(R.id.editText_advert_title);
        description = (EditText) findViewById(R.id.editText_advert_description);
        price = (EditText) findViewById(R.id.editText_advert_price);
        textViewError = (TextView) findViewById(R.id.textView_advert_error);
        btnPublish = (Button) findViewById(R.id.btn_advert_publish);
        btnPublish.setOnClickListener(onclickBtnPublish);
        btnCancel = (Button) findViewById(R.id.btn_advert_cancel);
        btnCancel.setOnClickListener(onclickBtnCancel);
    }


    // ***************************************
    // Private classes
    // ***************************************
    private class PostMessageTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                requestHeaders.add(WebServiceUtils.AUTHORIZATION, tokenDTO.getValue());

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate(true);

                MultiValueMap<String, Object> datas = new LinkedMultiValueMap<>();
                datas.add("expenseId", 1);
                datas.add("advertTile", "advertTile");
                datas.add("advertDescription", "advertDescription");
                datas.add("advertPrice", 150);
//                datas.add("customer", new FileSystemResource(absolutePath));

//                String advertTile = title.getText().toString();
//                String advertDescription = description.getText().toString();
//                float advertPrice = Float.parseFloat(price.getText().toString());
//                CustomerDTO customer = tokenDTO.getCustomerDTO();
//                AdvertDTO advert = new AdvertDTO(advertTile, advertDescription, advertPrice, customer);

                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                        datas, requestHeaders);


                // Make the network request, posting the message, expecting a String in response from the server
                ResponseEntity<String> response = restTemplate.exchange(WebServiceUtils.ADD_ADVERT_API, HttpMethod.POST, requestEntity,
                        String.class);

                // Return the response body to display to the user
                return response.getBody();
            } catch (Exception e) {
                Log.e("", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(AdvertActivity.this, "Data : "+result, Toast.LENGTH_LONG).show();
        }

    }


    private View.OnClickListener onclickBtnPublish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int error = validInputData();
            if (error == 0) {
                String advertTile = title.getText().toString();
                String advertDescription = description.getText().toString();
                float advertPrice = Float.parseFloat(price.getText().toString());
                CustomerDTO customerDTO = tokenDTO.getCustomerDTO();
                AdvertDTO advertDTO = new AdvertDTO(advertTile, advertDescription, advertPrice, customerDTO);
                Gson gson = new Gson();
                final String json = gson.toJson(advertDTO);

                GsonRequest<String> createAdvertRequest = new GsonRequest<String>(
                        Request.Method.POST,
                        WebServiceUtils.ADD_ADVERT_API,
                        String.class,
                        buildSecuredHeaders(),
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
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(createAdvertRequest);
            }
        }


        private int validInputData(){
            int error = 0;

            title = (EditText) findViewById(R.id.editText_advert_title);
            description = (EditText) findViewById(R.id.editText_advert_description);
            price = (EditText) findViewById(R.id.editText_advert_price);

            if(isEmpty(title.getText().toString())){
                title.setError(getString(R.string.error_advert_title));
                error++;
            }

            if(isEmpty(description.getText().toString())){
                description.setError(getString(R.string.error_advert_description));
                error++;
            }

            if(isEmpty(price.getText().toString())){
                price.setError(getString(R.string.error_advert_price));
                error++;
            }
            return error;
        }

        private Response.Listener<String> onRequestSuccessListener() {
            return new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "AdvertDTO is recorded. response is :"+response, Toast.LENGTH_LONG).show();
                };
            };
        }

        private Response.ErrorListener onRequestErrorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if(response == null ){
                        startLoginActivity();
                        return;
                    }
                    switch(response.statusCode) {
                        case 401:
                            startLoginActivity();
                            break;
                        case 400:
                        case 403:
                            if (response.data != null) {
                                String erroMessage = new String(response.data);
                                textViewError.setText(erroMessage);
                            }else{
                                textViewError.setText(getString(R.string.error_bad_request));
                            }
                            break;
                        case 500:
                        case 501:
                        case 502:
                        case 503:
                        case 504:
                        case 505:
                            textViewError.setText(getString(R.string.error_server_500));
                            break;
                        default:
                            textViewError.setText(getString(R.string.unexpected_error_server));
                            break;
                    }
                }
            };
        }
    };


    private View.OnClickListener onclickBtnCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdvertActivity.this);
            alertDialogBuilder.setTitle(getString(R.string.advert_cancel_dialog_title));
            alertDialogBuilder.setMessage(getString(R.string.advert_cancel_dialog_message));
            alertDialogBuilder.setPositiveButton(R.string.lbl_btn_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startDashboardActivity();
                }
            });
            alertDialogBuilder.setNegativeButton(R.string.lbl_btn_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    };

}
