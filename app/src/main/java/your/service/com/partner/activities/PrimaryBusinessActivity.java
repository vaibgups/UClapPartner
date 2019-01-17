package your.service.com.partner.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

import your.service.com.partner.AppController;
import your.service.com.partner.LoginActivity;
import your.service.com.partner.R;
import your.service.com.partner.adapter.DataAdapter;
import your.service.com.partner.adapter.RecyclerViewAdapter;
import your.service.com.partner.helper.UrlHelper;


public class PrimaryBusinessActivity extends AppCompatActivity
{


    ImageView back_icon;
    Button proceed;
    String id,cate,partner;
    List<DataAdapter> ListOfdataAdapter;
    RecyclerView recyclerView;
    View view;
    RecyclerViewAdapter recyclerViewadapter;
    Toolbar toolbar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editTextSearch;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_business);

        proceed = findViewById(R.id.proceed);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("Primary Business");

        editTextSearch = (EditText) findViewById(R.id.search);

        editTextSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                filter(editable.toString());
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                id = preferences.getString("catID","");
                cate = preferences.getString("category","");
                partner = preferences.getString("part_id","");
                JSON_HTTP_CALL1();
            }
        });

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        JSON_HTTP_CALL();

    }

    private void filter(String text)
    {
       List<DataAdapter> temp  =  new ArrayList<>();

        for (DataAdapter s : ListOfdataAdapter) {
            if (s.getCate_name().toLowerCase().contains(text)) {
                temp.add(s);
            }
        }
        recyclerViewadapter.updateList(temp);
    }


    private void JSON_HTTP_CALL1()
    {

        StringRequest request = new StringRequest(Request.Method.POST, UrlHelper.GET_SUB_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","well");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    String status = jsonObject.getString("status");

                    if (status.equals("1"))
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(PrimaryBusinessActivity.this,AdditionalBusinessActivity.class);
                        intent.putExtra("Response",response.toString());
                        startActivity(intent);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(PrimaryBusinessActivity.this,CreateAccountActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                }
            }
        }){
            @Override
            protected Map<String, String > getParams()
            {
                Map<String, String > params = new HashMap<String, String>();
                params.put("category_id",""+id);
                params.put("partner_id",""+partner);
                params.put("category",""+cate);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
        progressDialog = new ProgressDialog(PrimaryBusinessActivity.this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(0);
        progressDialog.show();

    }

    private void JSON_HTTP_CALL() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlHelper.GET_ALL_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response",response.toString());

                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("category");
                            for (int i = 0; i < heroArray.length(); i++)
                            {
                                JSONObject heroObject = heroArray.getJSONObject(i);
                                DataAdapter hero = new DataAdapter( heroObject.getString("category_id"),heroObject.getString("category"));
                                ListOfdataAdapter.add(hero);
                            }

                            progressDialog.dismiss();
                            recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, PrimaryBusinessActivity.this);
                            recyclerView.setAdapter(recyclerViewadapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest);
        progressDialog = new ProgressDialog(PrimaryBusinessActivity.this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
    }

    public void visibleProceedButton()
    {
        proceed.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
