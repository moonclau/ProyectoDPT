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
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {
    TextView nomusuario,txtestado;
    Button btnusu, btnsensor, btngps, btncerrar;
    private DatabaseReference bbdd;
    String idd;
    final String[] usuario = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomusuario = (TextView) findViewById(R.id.textusuario);
        txtestado = (TextView) findViewById(R.id.estado);
        btnusu = (Button) findViewById(R.id.btnperfil);

        btngps = (Button) findViewById(R.id.btngps);
        btnsensor = (Button) findViewById(R.id.btnsensor);
        btncerrar = (Button) findViewById(R.id.btnsignout);
        //Mostrar usuario---------------------------------------------------------

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        final String[] usuario = new String[4];
        //obtenemos el id de firebase para buscar los datos
        if (authd != null) {
            idd = authd.getUid();
            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usuario[0] = dataSnapshot.child(idd).child("nombre").getValue().toString();
                    usuario[1] = dataSnapshot.child(idd).child("estado").getValue().toString();
                    String estado=usuario[1];
                    if(estado.equals("1")){
                        txtestado.setText("Se encuentra perfecto para manejar");
                    }
                    if(estado.equals("2")){
                        txtestado.setText("Se apto para manejar, pero se recomienda no tomar");
                    }
                    if(estado.equals("3")){
                        txtestado.setText("usted no se encuentra en condiciones,podra manejar su auto cuando este bien");
                    }
                    nomusuario.setText(usuario[0]);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //BOTONES-------------------------------------------------------------------
        //metodo del boton ir a perfil
        btnusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this,Perfil.class));
            }
        });
        //metodo del boton ir a pagina de gps
        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Perfil.this, MainActivity.class));
            }
        });
        //metodo del boton ir a pagina sensor
        btnsensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Sensor.class));
            }
        });
        //metodo salir de la cuenta
        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    //metodo de cerrar sesion
    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }
}



        /*//obtenemos el id de firebase para buscar los datos
        if (authd != null) {
            correo = authd.getEmail();
            idd = authd.getUid();
            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usuario[0] = dataSnapshot.child(idd).child("nombre").getValue().toString();
                    usuario[1] = dataSnapshot.child(idd).child("numeroPersona").getValue().toString();
                    usuario[2] = dataSnapshot.child(idd).child("nombrePersona").getValue().toString();
                    usuario[3] = dataSnapshot.child(idd).child("apellido").getValue().toString();
                    Log.d( "Value is: " , usuario[0]+usuario[1]);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
/*        //Manda msm
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest
                        .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
        } else {


     /*       btnmsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.i("nombre:",""+usuario[1]);
                        String tel=usuario[1].trim();
                    enviarMensaje(tel,"Saludos "+usuario[2]+"su conocido "+usuario[0]+" "+usuario[3]+" no esta en condiciones favor de contactarlo");


                }
            });
        }

// mandar mensaje metodo
/*private void enviarMensaje (String numero, String mensaje){
    try {
        SmsManager sms = SmsManager.getDefault();
         sms.sendTextMessage(numero,null,mensaje,null,null);
        Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
    }
    catch (Exception e) {
        Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }
}*/

