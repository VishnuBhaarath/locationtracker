package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity  extends AppCompatActivity {
 private FusedLocationProviderClient fusedLocationProviderClient;
 private LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestlocationupdates();
        callpermissions();




    }
    public void requestlocationupdates(){
        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(4000);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                Log.e("Mainactivity","lat"+locationResult.getLastLocation().getLatitude());
                Log.e("Mainactivity","Long"+ locationResult.getLastLocation().getLongitude());
            }
        },getMainLooper());
    }
    public void callpermissions(){
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                requestlocationupdates();
                // do your task.
            }

            /**
             * This method will be called if some of the requested permissions have been denied.
             *
             * @param context           The application context.
             * @param deniedPermissions The list of permissions which have been denied.
             */
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callpermissions();
            }

        });
    }
}
