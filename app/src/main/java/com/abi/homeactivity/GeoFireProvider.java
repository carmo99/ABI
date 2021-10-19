package com.abi.homeactivity;

import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeoFireProvider
{
    private DatabaseReference mDatabase;
    private GeoFire mGeofire;

    public GeoFireProvider ()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ubicacion_usuarios");
        mGeofire = new GeoFire(mDatabase);
        Log.i("BASE_DATOS", "CONSTRUCTOR");
    }

    public void saveLocation(String idUsuario, LatLng latLng)
    {
        mGeofire.setLocation(idUsuario, new GeoLocation(latLng.latitude, latLng.longitude));
        Log.i("BASE_DATOS", "SALVAR");
    }

    public void removeLocation(String idUsuario)
    {
        mGeofire.removeLocation(idUsuario);
    }

    public GeoQuery getUbicacionUsuario(LatLng latLng)
    {
        GeoQuery geoQuery = mGeofire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 50);
        geoQuery.removeAllListeners();
        return geoQuery;
    }

}
