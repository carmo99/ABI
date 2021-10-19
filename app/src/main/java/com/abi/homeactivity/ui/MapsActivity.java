package com.abi.homeactivity.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abi.homeactivity.GeoFireProvider;
import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private int TIEMPO = 500;
    private Marker currentMarker = null;
    private boolean sale = true;
    private int contador = 0;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocation;
    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEST_CODE = 2;

    private LatLng mCurrentLatLng;
    private GeoFireProvider geoFireProvider;

    private boolean PrimeraVez = true;

    private List<Marker> mUsuariosMarkers = new ArrayList<>();

    LocationCallback mLocationCallBack = new LocationCallback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getContext() != null) {
                    mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.i("Latitud ", location.getLatitude() + " Longitud: "+ location.getLongitude());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_LATITUD, String.valueOf(location.getLatitude()));
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_LONGITUD, String.valueOf(location.getLongitude()));
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FECHA,""+DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
                    if (currentMarker != null ){
                        currentMarker.remove();
                    }

                    currentMarker = mMap.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(), location.getLongitude())
                            )
                            .title("Mi Ubicaciòn")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_usuario))
                    );
                    //Obtenemos la ubicacion del usuario en tiempo real
                    if(PrimeraVez == true)
                    {
                        PrimeraVez = false;
                        getUsuariosActivos();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(16f)
                                        .build()
                        ));
                    }
                    if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ESTADO).equals("NORMAL"))
                    {
                        updateLocation();
                    }
                    else
                    {
                        geoFireProvider.removeLocation(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID));
                    }
                }
            }
        }
    };

    private void updateLocation()
    {
        geoFireProvider.saveLocation(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID), mCurrentLatLng);
    }

    private void getUsuariosActivos()
    {
        geoFireProvider.getUbicacionUsuario(mCurrentLatLng).addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for(Marker marker: mUsuariosMarkers)
                {
                    if(marker.getTag() != null)
                    {
                        if(marker.getTag().equals(key))
                        {
                            return;
                        }
                    }
                }
                LatLng UsuarioEmergencia = new LatLng(location.latitude, location.longitude);
                Marker nvomarker = mMap.addMarker(new MarkerOptions()
                        .position(UsuarioEmergencia)
                        .title("Nuevo Usuario")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_usuario)));
                nvomarker.setTag(key);
                mUsuariosMarkers.add(nvomarker);
            }

            @Override
            public void onKeyExited(String key) {
                for(Marker marker: mUsuariosMarkers)
                {
                    if(marker.getTag() != null)
                    {
                        if(marker.getTag().equals(key))
                        {
                            marker.remove();
                            mUsuariosMarkers.remove(marker);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for(Marker marker: mUsuariosMarkers)
                {
                    if(marker.getTag() != null)
                    {
                        if(marker.getTag().equals(key))
                        {
                            marker.setPosition(new LatLng(location.latitude, location.longitude));
                            return;
                        }
                    }
                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

    }

    public MapsActivity(Activity a) {
        activity = a;
    }

    Handler handler = new Handler();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        geoFireProvider = new GeoFireProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_maps, container, false);



        mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        return rootView;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(200);

        Log.i("info", "hola hola bellakota");

        startLocation();
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR, null);

        // Add a marker in Sydney and move the camera


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if ( gpsActive() ){
                        mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                    }else{
                        showAlertDialogNoGPS();
                    }
                } else {
                    checkLocationPermisions();
                }
            } else {
                checkLocationPermisions();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && gpsActive()) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
        }else{
            showAlertDialogNoGPS();
        }
    }

    private void showAlertDialogNoGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Porfavor activa tu GPS para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEST_CODE);
                    }
                })
                .create()
                .show();
    }


    private boolean gpsActive(){
        boolean isActive = false;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isActive = true;
        }
        return isActive;
    }
    private void startLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if ( gpsActive() ){
                    mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                }else{
                    showAlertDialogNoGPS();
                }
            }else{
                checkLocationPermisions();
            }
        }else{
            if ( gpsActive() ){
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
            }else{
                showAlertDialogNoGPS();
            }
        }
    }

    private void  checkLocationPermisions(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicaciòn requiere de los permisos de ubicacion para poder utilizarse")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();
            }else{
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR, "Esperando");
        sale = false;
    }

}