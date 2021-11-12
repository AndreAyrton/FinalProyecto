package com.example.finalproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

        EditText Correo,Password,Nombres,Apellidos,Edad,Telefono,Direccion;
        Button REGISTRARUSUARIO;

        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Password = findViewById(R.id.Password);
        Nombres = findViewById(R.id.Nombres);
        Apellidos = findViewById(R.id.Apellidos);
        Edad = findViewById(R.id.Edad);
        Telefono = findViewById(R.id.Telefono);
        Direccion = findViewById(R.id.Direccion);
        REGISTRARUSUARIO = findViewById(R.id.REGISTRARUSUARIO);


        firebaseAuth = FirebaseAuth.getInstance();

        REGISTRARUSUARIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                Correo.setError("Correo no Valido");
                Correo.setFocusable(true);
            }else if(pass.length()<6){
                Password.setError("Contraseña debe ser mayor a 6");
                Password.setFocusable(true);
            }else {
                REGISTRAR(correo,pass);
            }
            }
        });
    }

    private void REGISTRAR(String correo, String pass){
        firebaseAuth.createUserWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            assert user !=null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Password.getText().toString();
                            String nombres = Nombres.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();
                            String telefono = Telefono.getText().toString();
                            String direccion = Direccion.getText().toString();

                             //Mandando datos a firebase
                            HashMap<Object,String> DatosUsuario = new HashMap<>();
                            DatosUsuario.put("uid",uid);
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("pass",pass);
                            DatosUsuario.put("nombres",nombres);
                            DatosUsuario.put("apellidos",apellidos);
                            DatosUsuario.put("edad",edad);
                            DatosUsuario.put("telefono",telefono);
                            DatosUsuario.put("direccion",direccion);

                            DatosUsuario.put("Imagen"," ");


                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("USUARIOS_DE_APP");

                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(Registro.this, "Se registro Exitosamenente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this,Inicio.class));

                        }else {
                            Toast.makeText(Registro.this, "Algo a salido mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}