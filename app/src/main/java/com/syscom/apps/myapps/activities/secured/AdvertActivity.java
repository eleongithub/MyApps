package com.syscom.apps.myapps.activities.secured;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syscom.apps.myapps.R;
import com.syscom.apps.myapps.domains.webservices.AdvertDTO;
import com.syscom.apps.myapps.domains.webservices.CustomerDTO;
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
 * Activité pour gérer les annonces.
 *
 * @author Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AdvertActivity extends SecuredActivity {

    protected static final String TAG = AdvertActivity.class.getSimpleName();

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



    private View.OnClickListener onclickBtnPublish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validInputData() > 0) {
                return;
            }
            new FetchAdvertTask().execute();
        }
    };

    private View.OnClickListener onclickBtnCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    /**
     * Classe privée pour gérer les appels web Service API afin d'enregistrer une annonce.
     */
    private class FetchAdvertTask extends AsyncTask<Void, Void, String> {

        private AdvertDTO advertDTO;
        private boolean error;

        @Override
        protected void onPreExecute() {
            String advertTile = title.getText().toString();
            String advertDescription = description.getText().toString();
            float advertPrice = Float.parseFloat(price.getText().toString());
            CustomerDTO customer = session.getTokenDTO().getCustomerDTO();
            advertDTO = new AdvertDTO(advertTile, advertDescription, advertPrice, customer);
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.add(WebServiceUtils.AUTHORIZATION,session.getTokenDTO().getValue());
            HttpEntity<AdvertDTO> requestEntity = new HttpEntity<>(advertDTO, requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            try {
                // Make the network request
                Log.d(TAG, WebServiceUtils.REGISTER_API);
                ResponseEntity<String> responseEntity = restTemplate.exchange(WebServiceUtils.ADD_ADVERT_API, HttpMethod.POST, requestEntity, String.class);
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
                textViewError.setText(result);
                return;
            }
            Toast.makeText(getApplicationContext(), "AdvertDTO is recorded. response is :"+result, Toast.LENGTH_LONG).show();
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

}
