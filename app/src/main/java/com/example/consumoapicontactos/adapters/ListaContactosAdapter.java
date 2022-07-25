package com.example.consumoapicontactos.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumoapicontactos.R;
import com.example.consumoapicontactos.models.Contactos;
import com.example.consumoapicontactos.models.ItemContacto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ViewHolder> {

    private ArrayList<Contactos> dataset;
    private ArrayList<Contactos> listaFiltrada;

    public ListaContactosAdapter() {
        this.dataset = new ArrayList<>();
        this.listaFiltrada = new ArrayList<>();

    }

    @NonNull
    @Override
    public ListaContactosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaContactosAdapter.ViewHolder holder, int position) {

        Contactos c =dataset.get(position);
        holder.txtNombre.setText(c.getName());
        holder.txtTelefono.setText(c.getPhone());
        holder.txtEmail.setText(c.getEmail());
        holder.txtVerPublicaciones.setText("VER PUBLICACIONES");



        holder.txtVerPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ItemContacto.class);
                intent.putExtra("itemContacto", c);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    public void filtrado(final String buscador, Context context) {
        try {
            int longitud = buscador.length();
            if (longitud == 0) {
                dataset.clear();
                listaFiltrada.clear();
                dataset.addAll(listaFiltrada);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dataset.clear();
                    List<Contactos> coleccion = listaFiltrada.stream()
                            .filter(i -> i.getName().toLowerCase().contains(buscador))
                            .collect(Collectors.toList());
                    dataset.addAll(coleccion);
                    if (dataset.isEmpty()){
                     SingleToast.show(context, "lista vacia", Toast.LENGTH_SHORT);
                    }
                } else {
                    dataset.clear();
                    for (Contactos c : listaFiltrada) {
                        if (c.getName().toLowerCase().contains(buscador)) {
                            dataset.add(c);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionalListaContacto(ArrayList<Contactos> listaContactos) {
        dataset.addAll(listaContactos);
        listaFiltrada.addAll(dataset);
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView txtNombre;
        private TextView txtTelefono;
        private TextView txtEmail;
        private TextView txtVerPublicaciones;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre =  itemView.findViewById(R.id.txtNombre);
            txtTelefono =  itemView.findViewById(R.id.txtTelefono);
            txtEmail =  itemView.findViewById(R.id.txtEmail);
            txtVerPublicaciones= itemView.findViewById(R.id.txtVerPublicaciones);
        }

    }

    public static class SingleToast {
        private static Toast mToast;

        public static void show(Context context, String text, int duration) {
            if (mToast != null)
                mToast.cancel();
                mToast = Toast.makeText(context, text, duration);
                mToast.show(); }

    }
}
