package com.example.proyectodpt;

import android.provider.ContactsContract;
import android.provider.ContactsContract.Profile;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
/*    private TextView mText;
    private DatabaseReference bbdd;
    int cont=0;
    Usuario users;
    String correo;
    String idd;
  */
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
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
        }

