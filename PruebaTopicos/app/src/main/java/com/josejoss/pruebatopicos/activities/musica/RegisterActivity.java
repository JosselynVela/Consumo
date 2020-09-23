package com.josejoss.pruebatopicos.activities.musica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.josejoss.pruebatopicos.R;

import dmax.dialog.SpotsDialog;
import com.josejoss.pruebatopicos.includes.MyToolbar;
import com.josejoss.pruebatopicos.models.Music;
import com.josejoss.pruebatopicos.providers.AuthProvider;
import com.josejoss.pruebatopicos.providers.MusicProvider;

public class RegisterActivity extends AppCompatActivity {


    AuthProvider mAuthProvider;
    MusicProvider mMusicProvider;

    //views
    Button mButtonRegistrer;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show(this,"Registro de Usuario",true);

        //registro
        mAuthProvider = new AuthProvider();
        mMusicProvider = new MusicProvider();



        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();


        mButtonRegistrer = findViewById(R.id.btnRegister);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);


        mButtonRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRegister();
            }
        });
    }

    private void clickRegister() {
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        if (!name.isEmpty()&&!email.isEmpty()&&!password.isEmpty()){
            if(password.length()>=6){
                mDialog.show();
                register(name,email,password);
            } else{
                Toast.makeText(this,"La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }


        } else{
            Toast.makeText(this,"Ingrese Todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    void register (final String name,final String email, String password){
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Music music= new Music(id,name,email);

                    create(music);
                }else{

                    Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    void create(Music music){

        mMusicProvider.create(music).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(RegisterAdminstrarActivity.this, MapActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    private void saveUser(String id,String nombre,String email) {
        String selectedUser = mPref.getString("user","");
        User user = new User();
        user.setEmail(email);
        user.setNombre(nombre);

        if (selectedUser.equals("musica")){
            mDatabase.child("Users").child("musica").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Fallo El Registro",Toast.LENGTH_SHORT).show();

                    }
                }

            });
        }else if(selectedUser.equals("administrar")){
            mDatabase.child("Users").child("administrar").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Fallo El Registro",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
 */
}