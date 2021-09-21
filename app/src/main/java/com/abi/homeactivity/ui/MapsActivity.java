package com.abi.homeactivity.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private int TIEMPO = 500;
    private Marker currentMarker = null;
    private boolean sale=true;
    private int contador = 0;

    public MapsActivity(Activity a)
    {
        activity=a;
    }

    Handler handler = new Handler();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        return rootView;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR, null);
        actualizarUbicacion();

        // Add a marker in Sydney and move the camera


    }
    private void actualizarUbicacion()
    {
        handler.postDelayed(new Runnable() {
            public void run()
            {
                if(sale == false && SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CONTADOR) == null)
                {
                    handler.removeCallbacks(this);
                    return;
                }
                Log.i("Latitud", ""+SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO));
                if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO).equals("30") ||
                        SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ESTADO).equals("EMERGENCIA"))
                {
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO, "0");
                    // función a ejecutar
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
                    if (ActivityCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null)
                                    {
                                        latitud = location.getLatitude();
                                        longitud = location.getLongitude();
                                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_LATITUD, String.valueOf(latitud));
                                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_LONGITUD, String.valueOf(longitud));
                                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FECHA,""+DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
                                        LatLng myUbicacion = new LatLng(latitud, longitud);
                                        Log.i("Latitud ", latitud + " Longitud: "+ longitud);
                                        if ( currentMarker == null){
                                            currentMarker = mMap.addMarker(new MarkerOptions().position(myUbicacion).title("Mi ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                            float zoomlevel= 16;
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myUbicacion, zoomlevel));
                                        }else {
                                            mMap.clear();
                                            currentMarker.remove();
                                            currentMarker = mMap.addMarker(new MarkerOptions().position(myUbicacion).title("Mi ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                            float zoomlevel= 16;
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myUbicacion, zoomlevel));
                                        }
                                    }
                                }
                            });
                }
                else
                {
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO,Integer.toString(
                            (Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO))+1)
                    ));
                }
                handler.postDelayed(this, TIEMPO);
            }
        }, TIEMPO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR, "Esperando");
        sale = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) MyApp.getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null)
                        {
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();
                        }
                    }
                });

    }
}