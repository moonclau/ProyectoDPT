package com.example.proyectodpt;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proyectodpt.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
//import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {
    private EditText inputEmail, inputPassword,nombre,nombrep,nump;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mrefin;
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //instancia de firebase
        auth = FirebaseAuth.getInstance();
        mrefin= FirebaseDatabase.getInstance().getReference();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        nombre=(EditText) findViewById(R.id.usu);
        nombrep=(EditText) findViewById(R.id.nomper);
        nump=(EditText) findViewById(R.id.numper);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Registro.this, ResetarContrasenia.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Registro.this, Login.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String nomp = nombrep.getText().toString().trim();
                String nom = nombre.getText().toString().trim();
                String num=nump.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Ingresa tu correo!", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Ingresa tu contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña muy corta, deben ser minimo 6 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }
                RegistrarUsu(nom,email,nomp,num);


                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Registro.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                 if (!task.isSuccessful()) {
                                    Toast.makeText(Registro.this, "La autentificacion fallo." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                     Intent intent = new Intent(Registro.this, MainActivity.class);
                                     intent.putExtra("correo", inputEmail.getText().toString());
                                     startActivity(intent);
                                     finish();
                                }
                            }
                        });

            }
        });
    }
 public void RegistrarUsu(String nomu,String correo,String nomp,String nump){

        Map<String, Object> users = new HashMap<>();
        users.put("nombre",nomu);
        users.put("email",correo);
        users.put("nombrePersona",nomp);
        users.put("numeroPersona",nump);
        mrefin.child("Usuarios").push().setValue(users);
        Toast.makeText(this,"Se ha registrado exitosamente",Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
