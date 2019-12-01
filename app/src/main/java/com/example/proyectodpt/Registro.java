package com.example.proyectodpt;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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
//libreria para guardar en firebase
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
//import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {
    private EditText inputEmail, inputPassword,nombre,nombrep,nump,ape;
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
        //guarda datos
        mrefin= FirebaseDatabase.getInstance().getReference("Usuarios");
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        nombre=(EditText) findViewById(R.id.nombre);
        nombrep=(EditText) findViewById(R.id.nomper);
        nump=(EditText) findViewById(R.id.numper);
        ape=(EditText) findViewById(R.id.apellido);


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
/*registrarse boton */
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String nomp = nombrep.getText().toString().trim();
                final String nom = nombre.getText().toString().trim();
                final String num=nump.getText().toString().trim();
                final String apellido=ape.getText().toString().trim();
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
                //guardar datps en realtime database


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
                                     //llamamos a firebase para obtener el id de auth y guardarlo como id en realtimebase
                                     FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
                                     if (authd != null) {
                                         String idd = authd.getUid();
                                         //guardamos en nuestro pojo usuario
                                         user = new Usuario(nom,apellido,email,nomp,num);
                                         mrefin.child(idd).setValue(user);

                                         EnviarMsM(num, "Saludos "+nomp+" tu conocido "+nom+" "+apellido+" se registro con nosotros por el motivo, que quiere tomar precausion para no manejar" +
                                                 " en un estado inconveniente,por lo cual, lo a registrado a usted como persona de confianza, Favor de comunicarse con esta persona" +
                                                 "para tener mas información ");
                                     }
                                     Intent intent = new Intent(Registro.this, MainActivity.class);
                                     startActivity(intent);
                                     finish();
                                }
                            }
                        });

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void EnviarMsM(String numero,String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
           // Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
