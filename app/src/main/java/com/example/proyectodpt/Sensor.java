package com.example.proyectodpt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodpt.clases.DatoSensor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sensor extends AppCompatActivity {

    TextView nomusuario, correo, telefono, nompersona, sensor;
    Button btnguardar,btnreg;
    EditText edit;
    private DatabaseReference bbdd;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        edit = (EditText) findViewById(R.id.numsensor);
        btnguardar = (Button) findViewById(R.id.btnguardare);
        btnreg = (Button) findViewById(R.id.btnreg);
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        final FirebaseUser authd = FirebaseAuth.getInstance().getCurrentUser();
        final String[] usuario = new String[7];
//metodo del boton ir a modificar
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sensor.this, MainActivity.class));
            }
        });
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = edit.getText().toString();
                if(!TextUtils.isEmpty(titulo)) {
                    if (authd != null) {
                        idd = authd.getUid();
                        bbdd.child(idd).child("numero").setValue(titulo);
                        Toast.makeText(getApplicationContext(), "se ha guardadp el numero.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {

                    Toast.makeText(getApplicationContext(), "Ingrese el numero del sensor.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}

