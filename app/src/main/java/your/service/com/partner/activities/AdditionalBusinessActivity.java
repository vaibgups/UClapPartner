package your.service.com.partner.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import your.service.com.partner.AppController;
import your.service.com.partner.R;
import your.service.com.partner.adapter.DataAdapter;
import your.service.com.partner.adapter.RecyclerViewAdapter1;
import your.service.com.partner.helper.UrlHelper;


public class AdditionalBusinessActivity extends AppCompatActivity {

    TextView skiplayout;
    Button proceed;
    List<DataAdapter> ListOfdataAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    String cate,partner;
    View view;
    RecyclerViewAdapter1 recyclerViewadapter;
    ArrayList<String> ImageTitleNameArrayListForClick;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_business);

        proceed = findViewById(R.id.proceed);
        skiplayout = findViewById(R.id.proceed_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Additional Business");


        skiplayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdditionalBusinessActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                cate = preferences.getString("category11","");
                partner = preferences.getString("part_id","");
                JSON_HTTP_CALL1();
            }
        });


        ImageTitleNameArrayListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        if (intent != null)
        {
            String response = intent.getExtras().getString("Response");
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("subcategory");

                for (int i=0; i <jsonArray.length(); i++)
                {
                    JSONObject heroObject = jsonArray.getJSONObject(i);
                    DataAdapter hero = new DataAdapter( heroObject.getString("category_id"),heroObject.getString("category"));
                    ListOfdataAdapter.add(hero);
                }
                recyclerViewadapter = new RecyclerViewAdapter1(ListOfdataAdapter, AdditionalBusinessActivity.this);
                recyclerView.setAdapter(recyclerViewadapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void visibleProceedButton1()
    {
        proceed.setVisibility(View.VISIBLE);
    }
    public void visibleProceedButton11()
    {
        proceed.setVisibility(View.INVISIBLE);
    }

    private void JSON_HTTP_CALL1()
    {

        StringRequest request = new StringRequest(Request.Method.POST, UrlHelper.UPDATE_SUB_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","well");
                Intent intent = new Intent(AdditionalBusinessActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        }){
            @Override
            protected Map<String, String > getParams()
            {
                Map<String, String > params = new HashMap<String, String>();
                params.put("partner_id",""+partner);
                params.put("sub_category",""+cate);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);

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
