package com.syscom.apps.myapps.activities.secured;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.syscom.apps.myapps.R;

import static android.text.TextUtils.isEmpty;

/**
 * Activité pour gérer les annonces
 *
 * @author Eric LEGBA
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
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
//        btnCancel.setOnClickListener(onclickBtnCancel);
    }



    private View.OnClickListener onclickBtnPublish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validInputData() > 0) {
                return;
            }

            String advertTile = title.getText().toString();
            String advertDescription = description.getText().toString();
            float advertPrice = Float.parseFloat(price.getText().toString());
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

    };

}
