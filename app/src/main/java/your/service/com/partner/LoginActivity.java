package your.service.com.partner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import your.service.com.partner.activities.PrimaryBusinessActivity;
import your.service.com.partner.adapter.AllProfileAdapter;
import your.service.com.partner.adapter.CountrySTDCodeAdapter;
import your.service.com.partner.helper.EqualSpacingItemDecoration;
import your.service.com.partner.helper.UrlHelper;
import your.service.com.partner.model.partner.details.PartnerLoginResponse;
import your.service.com.partner.model.partner.details.UsrItem;
import your.service.com.partner.model.partner.profile.AllSubProfileResponse;
import your.service.com.partner.singleton.MyVolleySingleton;


public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,ConnectivityReceiver.ConnectivityReceiverListener,
        /*MyVolleySingleton.VolleyResponseInterface,*/ View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    String status;
    EditText enterMobNo,enterEmailID,enterOtp;
    TextInputLayout layout,layout1;
    Button login;
    Spinner spinner;
    String[] countryNames={"+91","+971","+92","+966","+965","+1","+44"};
    int flags[] = {R.drawable.india, R.drawable.united,R.drawable.pakistan,R.drawable.saudi,R.drawable.kuwait,R.drawable.unitedstates,R.drawable.unitedkingdom};
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    private Gson gson;
    private AllSubProfileResponse allSubProfileResponse;
    private AllProfileAdapter allProfileAdapter;
    private boolean networkFlag = false;
    private MyVolleySingleton myVolleySingleton;
    private RequestQueue requestQueue;
    private PartnerLoginResponse partnerLoginResponse;
    private UsrItem usrItem;
    private AlertDialog ald;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkConnection();
        init();


    }

    private void init() {
        gson = new Gson();
//        myVolleySingleton = MyVolleySingleton.getInstance(LoginActivity.this,LoginActivity.this);
//        requestQueue = myVolleySingleton.getRequestQueue();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Log in");
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        CountrySTDCodeAdapter customAdapter = new CountrySTDCodeAdapter(getApplicationContext(), flags, countryNames);
        spinner.setAdapter(customAdapter);
        enterMobNo = findViewById(R.id.enter_otp);
        enterEmailID = findViewById(R.id.enter_email);
        layout = findViewById(R.id.til1);
        layout1 = findViewById(R.id.til2);
        login = findViewById(R.id.getOtp);
        toolbar = findViewById(R.id.toolbar);

        login.setOnClickListener(this);

    }

    private void showStartDialog() {
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v1 = li.inflate(R.layout.otp_verify_dialog_box, null, false);
        enterOtp = v1.findViewById(R.id.get_otp);
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setView(v1);
        ald = ad.create();
        ald.setCancelable(false);
        ald.show();
        ImageView close = v1.findViewById(R.id.close_image);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ald.dismiss();
            }
        });

        final Button verify = v1.findViewById(R.id.verify);
        verify.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        networkFlag = isConnected;
        showSnack(isConnected);
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        networkFlag = isConnected;

    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show();
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

    }

    private boolean checkEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onResponse(String response, int _case) {
        switch (_case){
            case 1:{
                try {
                    partnerLoginResponse = gson.fromJson(response.toString(), PartnerLoginResponse.class);
                    usrItem = partnerLoginResponse.getUsr().get(0);
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, ""+usrItem.getVerifyCode(), Toast.LENGTH_SHORT).show();
                    showStartDialog();
                }catch (Exception e){
                    progressDialog.dismiss();
                }
                break;
            }
            case 2:
                Toast.makeText(this, "case 2", Toast.LENGTH_SHORT).show();
                try {
                    Log.d(TAG, "verify otp 2"+response.toString());
                    progressDialog.dismiss();
                    ald.dismiss();
                    showStartDialog1();
                }catch (Exception e){
                    progressDialog.dismiss();
                }
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error, int _case) {

        progressDialog.dismiss();
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getOtp:
            {
                if (networkFlag){
                    getOtp();
                }else {
                    showSnack(networkFlag);
                }
                break;
            }
            case R.id.verify:
            {
                verifyOtp();
//                Toast.makeText(this, "Otp verify button press", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getOtp(){
        String mobNo = enterMobNo.getText().toString().trim();
        String email = enterEmailID.getText().toString().trim();
        if (mobNo.isEmpty()) {
            layout.setError("Enter Mobile Number");
            layout.requestFocus();
        } else
            {
            layout.setError("");
            layout1.requestFocus();
            if (email.isEmpty()) {
                layout1.setError("Enter Email Address");
                layout.setError("");
            } else {
                if (checkEmail(email)) {
                    layout.setError("");
                    layout1.setError("");

                    StringRequest request = new StringRequest(Request.Method.POST, UrlHelper.LOGIN_API, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response","well");
                            //    Toast.makeText(LoginActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                            try
                            {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                status = jsonObject.getString("status");
                                //  String message = jsonObject.getString("user_status");
                                //       Toast.makeText(OtpVerificationActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = jsonObject.getJSONArray("usr");

                                for (int i=0; i <jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String partnerId = object.getString("partner_id");
                                    String mob = object.getString("partner_mobile");
                                    String verify = object.getString("verify_code");


                                    Toast.makeText(LoginActivity.this, ""+verify, Toast.LENGTH_SHORT).show();

                                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    editor = sharedPreferences.edit();
                                    editor.putString("mobile_no",mob);
                                    editor.putString("part_id",partnerId);
                                    editor.commit();
                                    progressDialog.dismiss();
                                    showStartDialog();

                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.toString());
                            progressDialog.dismiss();
                            //  Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String > getParams()
                        {
                            Map<String, String > params = new HashMap<String, String>();
                            params.put("mobile",""+enterMobNo.getText().toString());
                            params.put("email",""+enterEmailID.getText().toString());
                            return params;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(request);

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Please Wait.....");
                    progressDialog.setProgressStyle(0);
                    progressDialog.show();
                } else {
                    layout1.setError("Wrong email address");
                }
            }
        }
    }

    private void verifyOtp(){
        String otp = enterOtp.getText().toString();
        if (otp.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Enter Otp number", Toast.LENGTH_SHORT).show();
        }
        else {

            final String share_mob = sharedPreferences.getString("mobile_no","");
            StringRequest request = new StringRequest(Request.Method.POST, UrlHelper.VERIFY_OTP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response","well");
                    //   Toast.makeText(LoginActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String status = jsonObject.getString("status");
                        //  String message = jsonObject.getString("user_status");
                        //       Toast.makeText(OtpVerificationActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        for (int i=0; i <jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String partnerId = object.getString("partner_id");
                            String partnerName = object.getString("partner_name");
                            //  String verify = object.getString("verify_code");
                        }
                        ald.dismiss();
                        showStartDialog1();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.toString());
                    //    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String > getParams()
                {
                    Map<String, String > params = new HashMap<String, String>();
                    params.put("mobile",""+share_mob);
                    params.put("verify_code",""+enterOtp.getText().toString().trim());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(request);
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setMessage("Please Wait.....");
//            progressDialog.setProgressStyle(0);
//            progressDialog.show();
        }

    }

    private void showStartDialog1()
    {

       // String GET_PROFILE_DETAILS = "http://datepicker.in/index.php/api/Partner_api/allProfile";

        final String share_mob = sharedPreferences.getString("mobile_no","");
        //     Toast.makeText(this, ""+share_mob, Toast.LENGTH_SHORT).show();

        StringRequest request1 = new StringRequest(Request.Method.POST, UrlHelper.GET_ALL_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                gson = new Gson();
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1"))
                    {
                        allSubProfileResponse = gson.fromJson(response.toString(),AllSubProfileResponse.class);
                        System.out.println(response);
                        progressDialog.dismiss();
                        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View v1 = li.inflate(R.layout.select_account_dialog, null, false);

                        RecyclerView recyclerView = v1.findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(LoginActivity.this));
                        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(16,EqualSpacingItemDecoration.VERTICAL));
                        allProfileAdapter = new AllProfileAdapter(LoginActivity.this,allSubProfileResponse.getSubcategory());
                        recyclerView.setAdapter(allProfileAdapter);

                        final AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                        ad.setView(v1);
                        final AlertDialog ald1 = ad.create();
                        ald1.setCancelable(false);
                        ald1.show();

                        ImageView close = v1.findViewById(R.id.close_image);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ald1.dismiss();
                            }
                        });

                        Button create_new_accont = v1.findViewById(R.id.create_new_accont);
                        create_new_accont.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LoginActivity.this,PrimaryBusinessActivity.class);
                                startActivity(intent);
                                ald1.dismiss();
                            }
                        });

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,PrimaryBusinessActivity.class);
                        startActivity(intent);
                    }
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "onResponse: ",e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<String, String>();
                params.put("mobile",""+share_mob);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request1);
    }
}
