package com.example.proyectodpt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectodpt.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {
    Button btnperfil, btnmsm,btnlogout;
    DatabaseReference mRootReference;
    Usuario users;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnperfil = (Button) findViewById(R.id.btnperfil);
        btnmsm = (Button) findViewById(R.id.btnmsm);
        btnlogout=(Button) findViewById(R.id.btnlogout);
        mRootReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();

            solicitarDatosFirebase(email);
        }
        //Recibe valor.



        btnperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Perfil.class));
            }
        });

        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest
                        .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
        } else {


            btnmsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //enviarMensaje(users.getNumeroPersona(),"Saludos "+users.getNombrePersona()+"su conocido "+users.getNombre()+"no esta en condiciones favor de ");
                enviarMensaje(users.getNumeroPersona(),"Buen dia");
                }
            });
        }
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


    }
    private void signOut() {
        auth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }

    private void solicitarDatosFirebase(final String email) {


                Query q=mRootReference.orderByChild("email").equalTo(email);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;

                            users= datasnapshot.getValue(Usuario.class);
                            String nombre = users.getNombre();
                            String nombrePersona = users.getNombrePersona();
                            String numeroPersona = users.getNumeroPersona();
                            users=new Usuario(nombre,email,nombrePersona,numeroPersona);
                            Log.e("nombre:",""+nombre);
                            Log.e("nombrePersona:",""+nombrePersona);
                            Log.e("numeroPersona:",""+numeroPersona);


                            Toast.makeText(MainActivity.this, "He encontrado "+cont, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        /*mRootReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("Usuarios").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user= snapshot.getValue(Usuario.class);
                            String nombre = user.getName();
                            String nombrep = user.getNomp();
                            int telefono = Integer.parseInt(user.getNump());

                            Log.e("NombreUsuario:",""+nombre);
                            Log.e("NombrePersona:",""+nombrep);
                            Log.e("TelefonoPersona:",""+telefono);
                            Log.e("Datos:",""+snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    }

