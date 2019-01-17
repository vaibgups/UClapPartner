package your.service.com.partner.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import your.service.com.partner.Config;
import your.service.com.partner.R;
import your.service.com.partner.adapter.RechargeAdapterButton;
import your.service.com.partner.model.PartnerDetails;
import your.service.com.partner.model.payment.PayPalPaymentResponse;
import your.service.com.partner.model.recharge.DataItem;
import your.service.com.partner.model.recharge.RechargeResponse;
import your.service.com.partner.model.server.trancation.Transaction;
import your.service.com.partner.model.server.trancation.TransactionData;
import your.service.com.partner.model.server.trancation.TransactionServerResponse;
import your.service.com.partner.model.server.trancation.update.TestResponse;

import static your.service.com.partner.AppController.editor1;
import static your.service.com.partner.AppController.sharedPreferences2;
import static your.service.com.partner.helper.UrlHelper.GET_PAYMENTS_BUTTON;
import static your.service.com.partner.helper.UrlHelper.POST_PAYMENTS_DETAILS;
import static your.service.com.partner.helper.UrlHelper.POST_UPDATE_PAYMENTS_DETAILS;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener,
        RechargeAdapterButton.ClickListenerInterface {
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    //The views
    private Button buttonPay;
    private EditText editTextAmount;
    private ImageView backButtonIV;
    private TextView toolbarActivityNameTV, getHelpTV, creditsBalanceTV;
    private RelativeLayout backIconRL;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManagerOfRecyclerView;
    //Payment Amount
    private String paymentAmount;
    private Gson gson;
    private RechargeResponse rechargeResponse;
    private DataItem dataItem;
    private List<DataItem> dataItemList;
    private RechargeAdapterButton rechargeAdapterButton;
    private PayPalPaymentResponse payPalPaymentResponse;
    private your.service.com.partner.model.payment.Response response;
    private long credits = 0;
    private ProgressDialog progressbar;
    private Transaction transaction;
    private PartnerDetails partnerDetails;
    private Intent intent;
    private static final String TAG = RechargeActivity.class.getSimpleName();
    private String uuid;
    private String transactionStatus;
    private TransactionServerResponse transactionServerResponse;
    TransactionData transactionData;
    private TestResponse testResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        transactionData = new TransactionData();
        intent = getIntent();
        if (intent.hasExtra(PartnerDetails.class.getSimpleName())){
            this.partnerDetails = (PartnerDetails) intent.getSerializableExtra(PartnerDetails.class.getSimpleName());
        }
//        getUserCredits();
        init();
        getPaymentButton();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void init() {
        gson = new Gson();
        progressbar = new ProgressDialog(RechargeActivity.this);
        progressbar.setTitle("Please wait");
        progressbar.setMessage("While transaction is proceed");
        progressbar.setProgressStyle(0);
        uuid =UUID.randomUUID().toString()+partnerDetails.getMobile_no();
        transactionStatus = "pending";


        Log.d(TAG, "init: UUID"+uuid);
        backButtonIV = (ImageView) findViewById(R.id.back);
        toolbarActivityNameTV = (TextView) findViewById(R.id.toolbar_activity_name);
        getHelpTV = (TextView) findViewById(R.id.get_help_recharge_wallet);
        creditsBalanceTV = (TextView) findViewById(R.id.credits_balance_text_view);
        backIconRL = (RelativeLayout) findViewById(R.id.back_icon_rl);

        backButtonIV.setOnClickListener(this);
        getHelpTV.setOnClickListener(this);
        backIconRL.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.wallet_recharge_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfRecyclerView = new GridLayoutManager(RechargeActivity.this, 3);
        recyclerView.setLayoutManager(layoutManagerOfRecyclerView);
        getUserCredits();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_icon_rl:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.toolbar_activity_name:
                break;
            case R.id.get_help_recharge_wallet:
                break;
        }

    }

    private void getPayment() {

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        payPalPaymentResponse = gson.fromJson(paymentDetails, PayPalPaymentResponse.class);
                        this.response = payPalPaymentResponse.getResponse();

                        if (response != null && response.getState().equals("approved")) {

                            if (dataItem != null) {
//                                credits = credits + Long.parseLong(dataItem.getSPoint());
                                transactionData.setTransactionPaypalId(response.getId());
                                transactionData.setTransactionComDate(response.getCreateTime());
                                transactionData.setTransactionStatus("success");
                                transactionData.setTransactionReport(payPalPaymentResponse.getResponseType());
                                updateTransaction();
                            }
                        }


                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
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
        creditsBalanceTV.setText( String.valueOf(credits));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void getPaymentButton() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_PAYMENTS_BUTTON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response.toString());
                String status;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        rechargeResponse = gson.fromJson(response.toString(), RechargeResponse.class);
                        dataItemList = rechargeResponse.getData();

                        if (dataItemList != null && dataItemList.size() > 0) {
                            rechargeAdapterButton = new RechargeAdapterButton(RechargeActivity.this, dataItemList, RechargeActivity.this);
                            recyclerView.setAdapter(rechargeAdapterButton);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                setViewVisibility(booked_service_lead_history_ll);
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RechargeActivity.this);
        requestQueue.add(stringRequest);


    }

    private void createTransaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_PAYMENTS_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: createTransaction" + response.toString());
                String status;
                gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.equals("1")) {
                        transactionServerResponse = gson.fromJson(response.toString(),TransactionServerResponse.class);
                        System.out.println(transactionServerResponse);
                        transactionData = transactionServerResponse.getData().get(0);
                        transactionData.setTransactionPaypalId("test");
                        transactionData.setTransactionComDate(transactionData.getTransactionDate());
                        transactionData.setTransactionStatus("success");
                        transactionData.setTransactionReport("payment");
                        if (!transactionData.getTotalCredit().equals(""))
                        {
                            credits = Long.parseLong(transactionData.getTotalCredit());
                            editor1.putString(TransactionData.class.getSimpleName(), response.toString());
                            editor1.putLong("credits", credits);
                            editor1.commit();
                        }
                        setWidgetValue();
//                        updateTransaction();

                        progressbar.dismiss();
                        getPayment();
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
                hashMap.put("amount",transactionData.getAmount());
                hashMap.put("credit",transactionData.getCredits());
                hashMap.put("transaction_id",transactionData.getTransactionId());
                hashMap.put("transaction_date",transactionData.getTransactionDate());
                hashMap.put("transaction_status",transactionData.getTransactionStatus());
                hashMap.put("partner_id",transactionData.getPartnerId());
                hashMap.put("mobile",transactionData.getMobile());

                return hashMap;
            }
        };
        int socketTimeouts = 30000;
        RetryPolicy policys = new DefaultRetryPolicy(socketTimeouts, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policys);
        RequestQueue requestQueue = Volley.newRequestQueue(RechargeActivity.this);
        requestQueue.add(stringRequest);


    }

    private void updateTransaction() {
        progressbar.show();
        gson = new Gson();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_UPDATE_PAYMENTS_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: updateTransaction" + response.toString());
                String status;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        progressbar.dismiss();
                        credits = credits + 200;
//                        transactionServerResponse = gson.fromJson(response.toString(),TransactionServerResponse.class);
//                        transactionData = transactionServerResponse.getData().get(0);
//                        Log.d(TAG, "onResponse: total credits: "+transactionData.getTotalCredit());
//                        credits = Long.parseLong(transactionData.getTotalCredit());
//                        editor1.putString(TransactionData.class.getSimpleName(), response.toString());
                        editor1.putLong("credits", credits);
                        editor1.commit();
                        setWidgetValue();
                    }
                } catch (JSONException e) {
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
                hashMap.put("partner_id",transactionData.getPartnerId());
                hashMap.put("id",transactionData.getId());
                hashMap.put("transaction_id",transactionData.getTransactionId());
                hashMap.put("transaction_paypal_id",transactionData.getTransactionPaypalId());
                hashMap.put("transaction_status",transactionData.getTransactionStatus());
                hashMap.put("transaction_com_date",transactionData.getTransactionComDate());
//                hashMap.put("credit",transactionData.getCredits());
                hashMap.put("total_credit","200");
//                hashMap.put("transaction_date",transactionData.getTransactionDate());
                hashMap.put("transaction_report",transactionData.getTransactionReport());
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RechargeActivity.this);
        requestQueue.add(stringRequest);


    }



    @Override
    public void onItemClick(int position, View v) {
        transactionData = new TransactionData();
        progressbar.show();
        dataItem = dataItemList.get(position);
        paymentAmount = dataItem.getSPrice();
        if (paymentAmount != null && paymentAmount != "") {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String stringDate = formatter.format(date);
            transactionData.setAmount(dataItem.getSPrice());
            transactionData.setCredits(dataItem.getSPoint());
            transactionData.setTransactionId(uuid);
            transactionData.setTransactionDate(stringDate);
            transactionData.setTransactionStatus(transactionStatus);
            transactionData.setPartnerId(partnerDetails.getPartId());
            transactionData.setMobile(partnerDetails.getMobile_no());
//            transactionData.setTotalCredit(String.valueOf(credits));
            createTransaction();
            }
    }
}
