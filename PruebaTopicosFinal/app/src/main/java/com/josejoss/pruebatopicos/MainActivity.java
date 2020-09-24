package com.josejoss.pruebatopicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import com.josejoss.pruebatopicos.activities.administrar.MapActivity;

public class MainActivity extends AppCompatActivity {

    Button mButtonEscucharMusica;
    Button mButtonAdminiButton;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();


        mButtonEscucharMusica = findViewById(R.id.btnEscuchar);
        mButtonAdminiButton = findViewById(R.id.btnAdministrar);

        mButtonEscucharMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","musica");
                editor.apply();
                goToSelectAuth();
            }
        });
        mButtonAdminiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","administrar");
                editor.apply();
                goToSelectAuth();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            String user = mPref.getString("user","");
            if (user.equals("administrar")){
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                /*COLOCAR LA REDIRECCION LUEGO DEL LOGUE EN
                 ESTE CASO LA LISTA DE CANCIONES*/
                //Intent intent = new Intent(MainActivity.this,MapActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
            }
        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}