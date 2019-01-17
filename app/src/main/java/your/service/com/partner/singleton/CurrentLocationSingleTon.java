package your.service.com.partner.singleton;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import your.service.com.partner.AppController;
import your.service.com.partner.ConnectivityReceiver;


public class CurrentLocationSingleTon implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private static final String TAG = CurrentLocationSingleTon.class.getSimpleName();
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_CODE = 1234;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    private static CurrentLocationSingleTon currentLocationSingleTon;
    public GoogleApiClient mGoogleApiClient;
    private Context context;
    private Activity activity;
    private GetCurrentLocationAddress getCurrentLocationAddress;
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private Handler handler;
    private boolean networkFlag = false;
    private ProgressDialog progressbar;

    public BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                    Log.d(TAG, "onReceive: GPS enable");
                    progressbar.show();
                    handlerPostExecute();

                }
            }
        }
    };

    private CurrentLocationSingleTon(Context context, Activity activity, GetCurrentLocationAddress getCurrentLocationAddress) {
        this.context = context;
        this.activity = activity;
        this.getCurrentLocationAddress = getCurrentLocationAddress;
        AppController.getInstance().setConnectivityListener(this);
        this.handler = new Handler();
        this.progressbar = new ProgressDialog(this.context);
        progressbar.setMessage("Please Wait While Fetching Your Location.....");
        progressbar.setProgressStyle(0);
        checkConnection();
    }

    public synchronized static CurrentLocationSingleTon getInstance(Context _Context, Activity _Activity, GetCurrentLocationAddress getCurrentLocationAddress) {

        if (currentLocationSingleTon == null) {
            currentLocationSingleTon = new CurrentLocationSingleTon(_Context, _Activity, getCurrentLocationAddress);
            return currentLocationSingleTon;
        }
        return currentLocationSingleTon;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
//                hideProgressBar();
                getLocationPermission();
            } else {
                showSettingDialog();
            }
        } else {
            showSettingDialog();
        }


    }

    public void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permission = {ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocationPermission: first permission granted");
            if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPermission: second permission granted");
                showSettingDialog();
            } else {
                progressbar.dismiss();
                ActivityCompat.requestPermissions(activity, permission, LOCATION_PERMISSION_CODE);
                Log.d(TAG, "getLocationPermission: second permission failed");
            }
        } else {
            progressbar.dismiss();
            ActivityCompat.requestPermissions(activity, permission, LOCATION_PERMISSION_CODE);
            Log.d(TAG, "getLocationPermission: first permission failed");
        }
    }


    public void getLocation() {
        if (mGoogleApiClient != null){
            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress(latitude, longitude);

                } else {
                    progressbar.dismiss();
                    Toast.makeText(context, "Something went wrong Please try again", Toast.LENGTH_SHORT).show();
                    Log.e("mLastLocation", "Empty");
                }

            } catch (SecurityException e) {
                e.printStackTrace();
                progressbar.dismiss();
                Toast.makeText(context, "Something went wrong Please try again", Toast.LENGTH_SHORT).show();
            }
    }else {
            progressbar.dismiss();

        }

    }

    public void getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            Log.d(TAG, "getAddress: call");
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0);
            getCurrentLocationAddress.setAddress(address,latitude,longitude);
            progressbar.dismiss();
        } catch (IOException e) {
            Log.d(TAG, "getAddress: exception"+e);
//            hideProgressBar();
            progressbar.dismiss();
            e.printStackTrace();
        }
    }

    public void showSettingDialog() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            progressbar.dismiss();
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            progressbar.dismiss();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:


                        break;
                }
            }
        });

    }


    public void initGoogleAPIClient() {
        if (networkFlag){
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        progressbar.show();
        checkPermissions();
        }else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopLocationUpdate() {

        if (mGoogleApiClient != null) {

            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
//            locationManager.removeUpdates(this);
        }
        currentLocationSingleTon = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: call");
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d(TAG, "onConnectionSuspended: call");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed: call");
    }

    public void handlerPostExecute(){
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                    getLocation();
            }
        },10000);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        networkFlag = isConnected;
    }

    private void checkConnection() {
        networkFlag = ConnectivityReceiver.isConnected();

    }


    public interface GetCurrentLocationAddress {
        void setAddress(String address,double latitude,double longitude);
    }
}
