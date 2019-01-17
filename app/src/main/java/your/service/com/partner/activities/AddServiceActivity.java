package your.service.com.partner.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

import your.service.com.partner.AppController;
import your.service.com.partner.BuildConfig;
import your.service.com.partner.ConnectivityReceiver;
import your.service.com.partner.R;
import your.service.com.partner.helper.UrlHelper;
import your.service.com.partner.singleton.CurrentLocationSingleTon;

public class AddServiceActivity extends AppCompatActivity implements CurrentLocationSingleTon.GetCurrentLocationAddress
{

    private static final String TAG = AddServiceActivity.class.getSimpleName();
    ImageView back;
    EditText location_ed;
    TextView current_location_btn;

    private static final int LOCATION_PERMISSION_CODE = 1234;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    CurrentLocationSingleTon currentLocationSingleTon;
    private String requiredAddress;
    private BroadcastReceiver gpsLocationReceiver;
    SharedPreferences sharedPreferences;
    String part_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        part_id = sharedPreferences.getString("part_id","");

        back = findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        location_ed = (EditText) findViewById(R.id.location_ed);
       // location_ed.addTextChangedListener(this);

        current_location_btn = (TextView) findViewById(R.id.current_location_btn);
        current_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLocationSingleTon.initGoogleAPIClient();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        currentLocationSingleTon = CurrentLocationSingleTon.getInstance(AddServiceActivity.this, AddServiceActivity.this, AddServiceActivity.this);
        gpsLocationReceiver = currentLocationSingleTon.gpsLocationReceiver;
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null) {
            unregisterReceiver(gpsLocationReceiver);

        }
//        currentLocationSingleTon.hideProgressBar();
        if (currentLocationSingleTon != null){
            currentLocationSingleTon.stopLocationUpdate();
            currentLocationSingleTon = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: is called");
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //If permission granted show location dialog if APIClient is not null
                    if (currentLocationSingleTon.mGoogleApiClient == null) {
                        currentLocationSingleTon.initGoogleAPIClient();
                    } else
                        currentLocationSingleTon.showSettingDialog();
                } else {
//                    currentLocationSingleTon.hideProgressBar();
                    //         updateGPSStatus("Location Permission denied.");
                    Toast.makeText(AddServiceActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    // Build intent that displays the App settings screen.
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }

    }


    @Override
    public void setAddress(String address,double longitude, double latitude) {
        location_ed.setText(address);
        uploadAddres(address,longitude,latitude);
       /* userDetailsSingleTon.setCurrentLocation(address);
        startActivity(new Intent(AddServiceActivity.this, MainActivity.class).putExtra(AddServiceActivity.class.getSimpleName(), address));
        finish();*/
    }

    private void uploadAddres(final String address, final double longitude, final double latitude)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlHelper.ADD_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("upload_address",response.toString());
                Toast.makeText(AddServiceActivity.this, "Address is Sucessfully upload", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("upload_address_error",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("partner_id",part_id);
                hashMap.put("location",address);
                hashMap.put("lat",""+latitude);
                hashMap.put("lang",""+longitude);
                return hashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
