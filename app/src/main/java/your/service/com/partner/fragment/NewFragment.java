package your.service.com.partner.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import your.service.com.partner.R;
import your.service.com.partner.activities.RechargeActivity;
import your.service.com.partner.adapter.LeadAdapter;
import your.service.com.partner.helper.EqualSpacingItemDecoration;
import your.service.com.partner.model.PartnerDetails;
import your.service.com.partner.model.lead.LeadItem;
import your.service.com.partner.model.lead.LeadResponse;
import your.service.com.partner.model.server.trancation.TransactionData;

import static com.android.volley.VolleyLog.TAG;
import static your.service.com.partner.AppController.sharedPreferences2;
import static your.service.com.partner.helper.UrlHelper.GET_LEAD;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private PartnerDetails partnerDetails;
    private Gson gson;
    LeadResponse leadResponse;
    LeadAdapter leadAdapter;
    private RecyclerView recyclerView;
    private List<LeadItem> leadItemList;
    private LeadItem leadItem;
    private Activity activity;
    private Context context;
    private TextView recharge,noNewLead,credits_balance_text_view, creditsTextView;
    private LinearLayout booked_service_lead_history_ll;
    private long credits = 0;

    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new, container, false);
        init(view);
        getBookServiceList(partnerDetails);
        return view;
    }

    private void init(View view) {
        this.activity = getActivity();
        this.context = getContext();
        partnerDetails = new PartnerDetails();
        getUserCredits();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        partnerDetails.setCatId(sharedPreferences.getString("catID",""));
        partnerDetails.setStat(sharedPreferences.getString("stat",""));
        partnerDetails.setSubCategory(sharedPreferences.getString("category11",""));
        partnerDetails.setMobile_no(sharedPreferences.getString("mobile_no",""));
        partnerDetails.setCategory(sharedPreferences.getString("category",""));
        partnerDetails.setPartId(sharedPreferences.getString("part_id",""));
        recharge = view.findViewById(R.id.recharge);
        noNewLead = view.findViewById(R.id.no_new_lead_tv);
        credits_balance_text_view = view.findViewById(R.id.credits_balance_text_view);
        creditsTextView = view.findViewById(R.id.credits);
        booked_service_lead_history_ll = (LinearLayout) view.findViewById(R.id.booked_service_lead_history_ll);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewBookedService);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(14,EqualSpacingItemDecoration.VERTICAL));

        recharge.setOnClickListener(NewFragment.this);
        setWidgetValue();

    }


    private void getBookServiceList(final PartnerDetails partnerDetails) {
        this.gson = new Gson();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LEAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+ response.toString());
                String status;
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    if (status.equals("1")){
                       leadResponse = gson.fromJson(response.toString(),LeadResponse.class);
                       leadItemList = leadResponse.getLead();

                        if (leadItemList!=null && leadItemList.size()>0){
                            setGoneVisibility(noNewLead);
                            setViewVisibility(booked_service_lead_history_ll);
                            leadAdapter = new LeadAdapter(context,leadItemList);
                            recyclerView.setAdapter(leadAdapter);
                        }else {
                            setGoneVisibility(booked_service_lead_history_ll);
                            setViewVisibility(noNewLead);
                        }

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

//                setViewVisibility(booked_service_lead_history_ll);
                Log.d(TAG, "onErrorResponse: "+ error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("cate_id","10");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void setGoneVisibility(View view) {
        view.setVisibility(View.GONE);
    }
    private void setViewVisibility(View view){
        view.setVisibility(View.VISIBLE);
    }

    private void getUserCredits() {
//        if (sharedPreferences2.contains(TransactionData.class.getSimpleName())) {
            credits = sharedPreferences2.getLong("credits", 0);

//        }
    }

    private void setWidgetValue() {
        if (credits>0) {
            credits_balance_text_view.setText(String.valueOf(credits));
            credits_balance_text_view.setTextColor(getResources().getColor(R.color.green));
            creditsTextView.setText(getResources().getString(R.string.credit_balance_text));
            creditsTextView.setTextColor(getResources().getColor(R.color.green));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recharge:
                {
                Intent intent = new Intent(getContext(), RechargeActivity.class);
                intent.putExtra(PartnerDetails.class.getSimpleName(),partnerDetails);
                startActivity(intent);
               /* final AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                builder.setMessage(getResources().getString(R.string.alertbox_message));
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builder.show();
           */
            }
            break;

        }
    }
}
