package com.example.consumoapicontactos.contactosapi;
import com.example.consumoapicontactos.models.Contactos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ContactosApiService {

    @GET("/users")
    Call<ArrayList<Contactos>> obtenerListaContacto();

}
