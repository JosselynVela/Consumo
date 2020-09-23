package com.josejoss.pruebatopicos.activities.administrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.josejoss.pruebatopicos.models.Admin;
import com.josejoss.pruebatopicos.providers.AdminProvider;
import com.josejoss.pruebatopicos.providers.AuthProvider;

public class RegisterAdminstrarActivity extends AppCompatActivity {


    AuthProvider mAuthProvider;
    AdminProvider mAdminProvider;

    //views
    Button mButtonRegistrer;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_adminstrar);

        MyToolbar.show(this,"Registro de administrador",true);

        //registro
        mAuthProvider = new AuthProvider();
        mAdminProvider = new AdminProvider();



        mDialog = new SpotsDialog.Builder().setContext(RegisterAdminstrarActivity.this).setMessage("Espere un momento").build();


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
                    Admin admin= new Admin(id,name,email);

                    create(admin);
                }else{

                    Toast.makeText(RegisterAdminstrarActivity.this,"No se pudo registrar el administrador", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    void create(Admin admin){

        mAdminProvider.create(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterAdminstrarActivity.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                    //Esto  es para que con el botòn atrás no pueda retornar
                    Intent intent = new Intent(RegisterAdminstrarActivity.this,MapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterAdminstrarActivity.this,"No se pudo crear el administrador", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}