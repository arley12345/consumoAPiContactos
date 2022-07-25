package com.example.consumoapicontactos.models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.consumoapicontactos.R;

import java.io.Serializable;

public class ItemContacto extends AppCompatActivity implements Serializable {

    private TextView itemNombre;
    private TextView itemTelefono;
    private TextView itemEmail;
    private Contactos itemContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_contacto);
        setTitle(getClass().getSimpleName());
        itemNombre= findViewById(R.id.itemNombre);
        itemTelefono = findViewById(R.id.itemTelefono);
        itemEmail= findViewById(R.id.itemEmail);

        initValues();

    }



    private void initValues(){
        itemContactos = (Contactos) getIntent().getExtras().getSerializable("itemContacto");
        itemNombre.setText(itemContactos.getName());
        itemTelefono.setText(itemContactos.getPhone());
        itemEmail.setText(itemContactos.getEmail());
    }
}