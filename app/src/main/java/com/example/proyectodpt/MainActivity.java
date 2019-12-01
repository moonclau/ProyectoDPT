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
    Button btnperfil, btnmsm, btnlogout;
    private DatabaseReference bbdd;
    Usuario users;
    TextView nomText;
    String idd;
    String correo, nombre, nombrePersona, numeroPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnperfil = (Button) findViewById(R.id.btnperfil);
        btnmsm = (Button) findViewById(R.id.btnmsm);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        nomText = (TextView) findViewById(R.id.nombre);
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        final String[] usuario= new String[4];
        //obtenemos el id de firebase para buscar los datos
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
        //metodo del boton ir a perfil
        btnperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Perfil.class));
            }
        });
        //Manda msm
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
                    //Log.i("nombre:",""+usuario[1]);
                        String tel=usuario[1].trim();
                    enviarMensaje(tel,"Saludos "+usuario[2]+"su conocido "+usuario[0]+" "+usuario[3]+" no esta en condiciones favor de contactarlo");


                }
            });
        }
        //boton cerrrar sesion
        btnlogout.setOnClickListener(new View.OnClickListener() {
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
// mandar mensaje metodo
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

    /*Button btnperfil, btnmsm,btnlogout;
    DatabaseReference mRootReference;
    TextView nomText;
    Usuario users;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String correo,nombre,nombrePersona,numeroPersona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnperfil = (Button) findViewById(R.id.btnperfil);
        btnmsm = (Button) findViewById(R.id.btnmsm);
        btnlogout=(Button) findViewById(R.id.btnlogout);
        nomText=(TextView) findViewById(R.id.nombre);
        mRootReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        auth = FirebaseAuth.getInstance();
        /* obtenemos los datos de firebase*/
      /*  final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        if (authd != null) {
            correo = authd.getEmail();
            String idd = authd.getUid();
            solicitarDatosFirebase(idd);
        }
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
                //    Log.i("nombre:",""+numeroPersona);
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
    private void solicitarDatosFirebase(final String idd) {
        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre= dataSnapshot.child(idd).child("nombre").getValue().toString();
                mText.setText(nombre);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void signOut() {
        auth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }


    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
           // sms.sendTextMessage(numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    }


       */
