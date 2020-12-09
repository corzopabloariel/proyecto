package com.example.proyecto.entity;

import android.content.ContentValues;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Categorias {
    /**
     * Arreglo de objetos {@link Publicacion.Articulo} que simula una fuente de datos
     */
    public static final List<Categoria> ITEMS = new ArrayList<Categoria>();

    /**
     * Mapa simulador de búsquedas de articulos por id
     */
    public static final Map<String, Categoria> MAPA_ITEMS = new HashMap<String, Categoria>();

    /**
    Referencia base de datos
    */



    static {
        FirebaseAuth mAuth;
        DatabaseReference mDatabase;
        mDatabase=FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference().child("servicio")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("tag","Children" + dataSnapshot.getKey());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Categoria categoria = snapshot.getValue(Categoria.class);
                            Log.d("clase de categoria",snapshot.getValue().toString());
                            String nombre= (String) snapshot.child("nombre").getValue();
                            String url= (String) snapshot.child("icono512").getValue();
                            String imgToolbar=(String)snapshot.child("imgToolbar").getValue();
                            Categoria categoria=new Categoria(nombre,url,snapshot.getKey(),imgToolbar);
                          // categoria.setID(snapshot.getKey());
                          //  Log.d("idcategoria",snapshot.getKey());
                           agregarItem(categoria);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private static void agregarItem(Categoria item) {
        ITEMS.add(item);
        MAPA_ITEMS.put(item.getId(), item);
    }


    public static class Categoria {


        private String nombre;
        private String icono512;
        private Map<String, Object> proveedores;
        private String imgToolbar;

        public Categoria() {

        }
        public Categoria(String nombre, String icono512,String id,String imgToolbar) {
            this.nombre = nombre;
            this.id=id;
            this.imgToolbar = imgToolbar;
            this.icono512 = icono512;
        }

        public String getNombre() {
            return nombre;
        }

        public Map<String, Object> getProveedores() {
            return proveedores;
        }

        public String getIcono512() {
            return icono512;
        }
        public String getImgToolbar(){
            return imgToolbar;
        }
        @Exclude
        private String id;

        public String getId() {
            return id;
        }

        public void setID(String key) {
            this.id=key;
        }

    }
}