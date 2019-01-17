package your.service.com.partner.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import your.service.com.partner.AppController;
import your.service.com.partner.LoginActivity;
import your.service.com.partner.R;
import your.service.com.partner.adapter.ServiceDetailsAdapter;
import your.service.com.partner.model.lead.LeadItem;
import your.service.com.partner.model.lead.QuesAnsItem;
import your.service.com.partner.model.lead.ServiceItem;
import your.service.com.partner.model.lead.UserDetailsItem;
import your.service.com.partner.model.server.trancation.TransactionData;
import your.service.com.partner.model.server.trancation.TransactionServerResponse;

import static your.service.com.partner.AppController.editor1;
import static your.service.com.partner.AppController.sharedPreferences2;
import static your.service.com.partner.helper.UrlHelper.BUY_SERVICE;
import static your.service.com.partner.helper.UrlHelper.POST_PAYMENTS_DETAILS;

public class LeadDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LeadDetailsActivity.class.getSimpleName();
    Toolbar toolbar;
    private List<LeadItem> leadItemList;
    Intent intent;
    private LeadItem leadItem;
    private UserDetailsItem userDetailsItem;
//    private List<QuesAnsItem> quesAnsItemList;
    private List<ServiceItem> serviceItemList;
    private RecyclerView recyclerView;
    private ServiceDetailsAdapter serviceDetailsAdapter;
    private ImageView backImageView;
    private  TextView client_name,service_name,requirement,budget,hiring,location, toolbar_activity_name,credits_text_view;
    private Button reject_service_btn,respond_service_btn;
    private long credits = 0;
    private ProgressDialog progressbar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LeadDetailsActivity.this);
        leadItemList = new ArrayList<>();
        intent = getIntent();
        if (intent.hasExtra(LeadItem.class.getSimpleName())){
            leadItem = (LeadItem) intent.getSerializableExtra(LeadItem.class.getSimpleName());
            userDetailsItem = leadItem.getUserDetails().get(0);
            serviceItemList = leadItem.getService();
            serviceDetailsAdapter = new ServiceDetailsAdapter(LeadDetailsActivity.this,serviceItemList);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerViewServiceDetails);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(LeadDetailsActivity.this));
            recyclerView.setAdapter(serviceDetailsAdapter);
        }
        init();


    }

    private void init() {

        progressbar = new ProgressDialog(LeadDetailsActivity.this);
        progressbar.setTitle("Please wait");
        progressbar.setProgressStyle(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backImageView = findViewById(R.id.lead_details_back_iv);
        toolbar_activity_name = (TextView) findViewById(R.id.toolbar_activity_name);
        credits_text_view = (TextView) findViewById(R.id.credits_text_view);
        client_name = (TextView) findViewById(R.id.client_details_name);
        service_name = (TextView) findViewById(R.id.client_category_name);
        reject_service_btn = (Button) findViewById(R.id.reject_service_btn);
        respond_service_btn = (Button) findViewById(R.id.respond_service_btn);
        getUserCredits();

        toolbar_activity_name.setText(userDetailsItem.getUserName());
        client_name.setText(userDetailsItem.getUserName());
        service_name.setText(leadItem.getService().get(0).getService());

        respond_service_btn.setText("Respond"+" ( "+leadItem.getCredit()+" ) "+"Credit");

        backImageView.setOnClickListener(this);
        reject_service_btn.setOnClickListener(this);
        respond_service_btn.setOnClickListener(this);


        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lead_details_back_iv:{
                finish();
                break;
            }
            case R.id.reject_service_btn:
            {
                Toast.makeText(this, "Reject", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.respond_service_btn:
            {
                String credit = respond_service_btn.getText().toString();
                String numberOnly= credit.replaceAll("[^0-9]", "");
                if (credits >= Long.parseLong(numberOnly)){
                respondService(numberOnly);
            }else{
                    Toast.makeText(this, "Please recharge wallet", Toast.LENGTH_SHORT).show();
            }

                break;
            }
        }
    }


    private void getUserCredits() {
//        if (sharedPreferences2.contains(TransactionData.class.getSimpleName())) {
            credits = sharedPreferences2.getLong("credits", 0);

            setWidgetValue();
//        }
    }

    private void setWidgetValue() {
        credits_text_view.setText( String.valueOf(credits));
    }


    private void cancelService() {
        progressbar.setMessage("While reject this service");
        progressbar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_PAYMENTS_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: createTransaction" + response.toString());
                String status;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {
//                        transactionServerResponse = gson.fromJson(response.toString(),TransactionServerResponse.class);

                        progressbar.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressbar.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressbar.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();

                return hashMap;
            }
        };
        int socketTimeouts = 30000;
        RetryPolicy policys = new DefaultRetryPolicy(socketTimeouts, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policys);
        RequestQueue requestQueue = Volley.newRequestQueue(LeadDetailsActivity.this);
        requestQueue.add(stringRequest);


    }

    private void respondService(final String numberOnly){
        final String part_id = sharedPreferences.getString("part_id","");
        progressbar.setMessage("While respond this service");
        progressbar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BUY_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: buyService" + response.toString());
                String status,creditMessage;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    creditMessage = jsonObject.getString("credit");
                    if (status.equals("1")) {
//                        transactionServerResponse = gson.fromJson(response.toString(),TransactionServerResponse.class);
                        credits = credits -Long.parseLong(numberOnly);
                        editor1.putLong("credits", credits);
                        editor1.commit();
                        getUserCredits();
                        Toast.makeText(LeadDetailsActivity.this, ""+creditMessage, Toast.LENGTH_SHORT).show();
                        progressbar.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressbar.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressbar.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("partner_id",part_id);
                hashMap.put("credit",numberOnly);
                return hashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}
