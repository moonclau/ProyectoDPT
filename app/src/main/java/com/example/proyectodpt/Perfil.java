package com.example.proyectodpt;

import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Profile;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodpt.clases.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Perfil extends AppCompatActivity {

   TextView nomusuario,correo,telefono,nompersona,sensor;
   Button btnmodificar,btneliminar,btnregresar;
    private DatabaseReference bbdd;
    String idd;
    //final String[] usuario = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nomusuario = (TextView) findViewById(R.id.nombreusuario);
        correo = (TextView) findViewById(R.id.uscorreo);
        nompersona = (TextView) findViewById(R.id.usper);
        telefono = (TextView) findViewById(R.id.ustel);
        btnregresar = (Button) findViewById(R.id.btnregresar);
        btneliminar = (Button) findViewById(R.id.btneliminar);
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        final String[] usuario= new String[7];
        //obtenemos el id de firebase para buscar los datos
        // Mostrar usuario---------------------------------------------------------
        if (authd != null) {
            idd = authd.getUid();
            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usuario[0] = dataSnapshot.child(idd).child("nombre").getValue().toString();
                    usuario[1] = dataSnapshot.child(idd).child("apellido").getValue().toString();
                    usuario[2] = dataSnapshot.child(idd).child("email").getValue().toString();
                    usuario[3] = dataSnapshot.child(idd).child("nombrePersona").getValue().toString();
                    usuario[4] = dataSnapshot.child(idd).child("numeroPersona").getValue().toString();
                    nomusuario.setText(usuario[0]+" "+usuario[1]);
                    correo.setText(usuario[2]);
                    nompersona.setText(usuario[3]);
                    telefono.setText(usuario[4]);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //BOTONES-------------------------------------------------------------------
        //metodo del boton ir a modificar
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, MainActivity.class));
            }
        });
        //metodo del boton ir a eliminar
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(Perfil.this, MainActivity.class));
            }
        });

    }
}
        //Mostrar usuario---------------------------------------------------------
/*
    bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
    final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
    final String[] usuario= new String[4];
    //obtenemos el id de firebase para buscar los datos
    if (authd != null) {
        idd = authd.getUid();
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario[0] = dataSnapshot.child(idd).child("nombre").getValue().toString();
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
            startActivity(new Intent(Perfil.this, MainActivity.class));
        }
    });
    //metodo del boton ir a pagina de gps
    btngps.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nomusuario.set("Hola");
            //startActivity(new Intent(Perfil.this, MainActivity.class));
        }
    });
    //metodo del boton ir a pagina sensor
    btnusu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //startActivity(new Intent(Perfil.this, MainActivity.class));
        }
    });
    //metodo salir de la cuenta
    btnusu.setOnClickListener(new View.OnClickListener() {
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
        startActivity(new Intent(Perfil.this, Login.class));
        finish();
    }

       /*mText = (TextView) findViewById(R.id.usuario);
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        if (authd != null) {
            correo = authd.getEmail();
            idd = authd.getUid();
            solicitarDatosFirebase(idd);
        }

    }

        private void solicitarDatosFirebase(final String idd) {
            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nombre= dataSnapshot.child(idd).child("nombre").getValue().toString();
                    mText.setText(nombre);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }*/



