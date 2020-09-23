package com.josejoss.pruebatopicos.providers;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.josejoss.pruebatopicos.models.Music;

public class MusicProvider {
    DatabaseReference mDatabase;
    public MusicProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("musica");
    }
    public Task<Void> create(Music music){
        Map<String,Object> map = new HashMap<>();
        map.put("nombre",music.getNombre());
        map.put("email",music.getEmail());

        return mDatabase.child(music.getId()).setValue(map);
    }
}
