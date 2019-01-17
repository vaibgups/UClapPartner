package your.service.com.partner.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import your.service.com.partner.AppController;
import your.service.com.partner.R;
import your.service.com.partner.helper.UrlHelper;


public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_icon;
    Button create_account;
    TextView city,showNumber;
    EditText enterMobNo,enterEmailID;
    TextInputLayout layout,layout1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String selectedItem;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Account");

        create_account = findViewById(R.id.create_account);
        city = findViewById(R.id.city);
        enterMobNo = findViewById(R.id.enter_otp);
        enterEmailID = findViewById(R.id.enter_email);
        layout = findViewById(R.id.til1);
        layout1 = findViewById(R.id.til2);
        showNumber = findViewById(R.id.showNumber);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String mobile = sharedPreferences.getString("mobile_no","");
        showNumber.setText(mobile);

        city.setOnClickListener(this);
        create_account.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        switch (id)
        {
            case R.id.city:
            {
               // Toast.makeText(this, "city", Toast.LENGTH_SHORT).show();


                String[] movie_names = new String[] {"Delhi","Gurgaon","Ghaziabad","Faridabad","Noida","Bangalore","Pune","Mumbai","Hyderabad","Chennai","Kolkata","Ahmedabad","Jaipur","Chandigarh","Navi Mumbai"};

                LayoutInflater inflater = getLayoutInflater();
                View v1 = inflater.inflate(R.layout.get_city_name,null,true);
                final AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setView(v1);
                final AlertDialog ald = ad.create();
                ald.setCancelable(false);
                ListView listView = v1.findViewById(R.id.List);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,movie_names);
                listView.setAdapter(adapter);
                ald.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedItem = (String)parent.getItemAtPosition(position);
                        city.setText(selectedItem);
                        create_account.setBackgroundColor(Color.rgb(0, 0, 0));
                        ald.dismiss();
                    }
                });


                break;
            }
            case R.id.create_account:
            {

                String Name = enterMobNo.getText().toString().trim();
                String email = enterEmailID.getText().toString().trim();

                if (Name.isEmpty())
                {
                    layout.setError("Enter your name");
                }
                else
                {
                    layout1.requestFocus();
                    layout.setError("");
                    if (email.isEmpty())
                    {
                        layout1.setError("Enter your Email Address");
                    }
                    else
                    {
                        layout.setError("");
                        layout1.setError("");


                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        final String partId = sharedPreferences.getString("part_id","");

                        //String CONFIRM_URL = "http://datepicker.in/index.php/api/Partner_api/partner_profile_update";

                        StringRequest request = new StringRequest(Request.Method.POST, UrlHelper.UPDATE_PROFILE, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response","well");
                                //Toast.makeText(CreateAccountActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                                try
                                {
                                    JSONObject object = new JSONObject(response.toString());
                                    //Toast.makeText(CreateAccountActivity.this, ""+object, Toast.LENGTH_SHORT).show();
                                    String sta = object.getString("status");
                                   // Toast.makeText(CreateAccountActivity.this, ""+sta, Toast.LENGTH_SHORT).show();
                                    if (sta.equals("1"))
                                    {
                                        Intent intent = new Intent(CreateAccountActivity.this,ProfileActivity.class);
                                        startActivity(intent);
                                    }
                                    else 
                                    {
                                        Toast.makeText(CreateAccountActivity.this, "email id already registerd", Toast.LENGTH_SHORT).show();
                                    }
                                 //   Toast.makeText(CreateAccountActivity.this, ""+object, Toast.LENGTH_SHORT).show();
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
//                                try {
//                                    JSONObject object = new JSONObject(response.toString());
//                                    String status = object.getString("status");
//                                    String message =  object.getString("message");
//
//                                    int i = Integer.parseInt(status);
//                                    Toast.makeText(CreateAccountActivity.this, ""+i, Toast.LENGTH_SHORT).show();
//                                    if (i==1)
//                                    {
//                                        Toast.makeText(CreateAccountActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(CreateAccountActivity.this,ProfileActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(CreateAccountActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error",error.toString());
                         //       Toast.makeText(CreateAccountActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String > getParams()
                            {
                                Map<String, String > params = new HashMap<String, String>();
                                params.put("partnerId",""+partId);
                                params.put("name",""+enterMobNo.getText().toString());
                                params.put("email",""+enterEmailID.getText().toString());
                                params.put("country","INDIA");
                                params.put("city",""+selectedItem);
                                return params;
                            }
                        };
                        AppController.getInstance().addToRequestQueue(request);
                       // Toast.makeText(getApplicationContext(), "Vua news published Successfully!", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            }
        }
    }
}
