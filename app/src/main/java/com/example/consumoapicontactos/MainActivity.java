package com.example.consumoapicontactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.consumoapicontactos.adapters.ListaContactosAdapter;
import com.example.consumoapicontactos.contactosapi.ContactosApiService;
import com.example.consumoapicontactos.models.ContactoRespuesta;
import com.example.consumoapicontactos.models.Contactos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    //retrofit
    Retrofit retrofit;
    List<Contactos> listaContactos;
    SearchView buscador;
    RecyclerView recyclerView;
    ListaContactosAdapter listaContactosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscador = findViewById(R.id.buscador);
        recyclerView= findViewById(R.id.recyclerView);

        listaContactosAdapter = new ListaContactosAdapter();
        recyclerView.setAdapter(listaContactosAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager =  (new GridLayoutManager(this,1));
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData();
        buscador.setOnQueryTextListener(this);
    }

    private void getData() {

        ContactosApiService service = retrofit.create(ContactosApiService.class);
        Call<ArrayList<Contactos>> contactoRespuestaCall = service.obtenerListaContacto();
        contactoRespuestaCall.enqueue(new Callback<ArrayList<Contactos>>() {
            @Override
            public void onResponse(Call<ArrayList<Contactos>> call, Response<ArrayList<Contactos>> response) {
                System.out.println("lista antes: "+ response.body().toString());
                if (response.isSuccessful()) {
                    listaContactos = response.body();
                    System.out.println("lista: " + listaContactos.size());
                    /*for (int i = 0; i < listaContactos.size(); i++) {

                        listaContactos.get(i);

                        Contactos c = new Contactos();
                        Log.i("TAG", "name: " + listaContactos.get(i).getName());
                        Log.i("TAG", "email: " + listaContactos.get(i).getEmail());
                        Log.i("TAG", "phone: " + listaContactos.get(i).getPhone());
                        Log.i("TAG", "----------------------------");
                    }*/
                    listaContactosAdapter.adicionalListaContacto((ArrayList<Contactos>) listaContactos);
                } else {
                    Log.e("TAG: ", "onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Contactos>> call, Throwable t) {
                Log.e("TAG: ", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listaContactosAdapter.filtrado(s, this );
        if (s.length()==0 ){
            getData();
        }
        return false;
    }




  /* private void getData() {
       String url = "https://jsonplaceholder.typicode.com/users";
       // creating a new variable for our request queue
       RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
       // in this case the data we are getting is in the form
       // of array so we are making a json array request.
       // below is the line where we are making an json array
       // request and then extracting data from each json object.
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
//
               for (int i = 0; i < response.length(); i++) {
                   // creating a new json object and
                   // getting each object from our json array.
                   try {
                       // we are getting each json object.
                       JSONObject responseObj = response.getJSONObject(i);
//
//
                       String Name = responseObj.getString("name");
                       String Email = responseObj.getString("email");
                       String Telefono = responseObj.getString("phone");
//
                       listaUsuarios.add(new Contactos(Name,  Email, Telefono));
                       Contactos c = listaUsuarios.get(i);

                       Log.i("TAG nombre: ", c.getNombre());
                       Log.i("TAG telefono: ", c.getTelefono());
                       Log.i("TAG Email: ", c.getEmail());
                       System.out.println("----------------------");
//
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }


               }
               StringBuilder sb=new StringBuilder();
               for (Contactos lista : listaUsuarios){
                   sb.append(lista.getNombre()+ "\n");
                   sb.append(lista.getEmail()+ "\n");
                   sb.append(lista.getTelefono()+ "\n\n");
               }

               txtApi.setText("lista: "+ sb.toString());
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               System.out.println("error api");
           }
       });
       queue.add(jsonArrayRequest);
       System.out.println("tamaño: "+ listaUsuarios.size());
    }*/
}
