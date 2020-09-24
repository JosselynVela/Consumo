package com.josejoss.pruebatopicos.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {

    private DatabaseReference mDatabase;
    private GeoFire mGeofire;

    public GeofireProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("active_location");
        mGeofire= new GeoFire(mDatabase);
    }

    //guardar latitud y longitud
    public void saveLocation(String idAdmin, LatLng latLng){
        mGeofire.setLocation(idAdmin,new GeoLocation(latLng.latitude,latLng.longitude));
    }

    //borrar coordenadas
    public void removeLocation (String idAdmin){
        mGeofire.removeLocation(idAdmin);
    }


}
