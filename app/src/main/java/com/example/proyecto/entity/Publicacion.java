package com.example.proyecto.entity;

import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.proyecto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Modelo de datos para los artículos que se inflarán en la lista
 */
public class Publicacion {

    /**
     * Arreglo de objetos {@link Articulo} que simula una fuente de datos
     */
    public static final List<Articulo> ITEMS = new ArrayList<Articulo>();

    /**
     * Mapa simulador de búsquedas de articulos por id
     */
    static DatabaseReference mDatabase;


    public static final Map<String, Articulo> MAPA_ITEMS = new HashMap<String, Articulo>();

public static void cargarPublicaciones() {
    Articulo publicaC = new Articulo("De prueba", "fecha", "imagen", "latitud","longitud", "User","Tarjeta");
    agregarItem(publicaC);
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference mDatabase;
    mDatabase=FirebaseDatabase.getInstance().getReference();
  FirebaseDatabase.getInstance().getReference().child("publicacion")
                .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("tag","Children" + dataSnapshot.getKey());

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String usuarioactual=mAuth.getUid();
                Log.d("taaag",usuarioactual);
                String provedor=(String) snapshot.child("proveedor").getValue();
                Log.d("provedor",provedor);
                //traigo todos porque no funca esto
                if (usuarioactual== usuarioactual){
                    Log.d("entre en if",provedor);
                    String descripcion=(String) snapshot.child("descripcion").getValue();
                    String titulo= (String) snapshot.child("titulo").getValue();
                    String img= (String) snapshot.child("img").getValue();
                    String latitud=(String)snapshot.child("latitud").getValue();
                    String longitud=(String)snapshot.child("longitud").getValue();
                    String fecha=(String)snapshot.child("fecha").getValue();
                    Articulo publica = new Articulo(descripcion, fecha, img, latitud,longitud, usuarioactual,titulo);
                    Log.d("publica",descripcion+titulo+img+latitud+longitud+fecha);
                    agregarItem(publica);}
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
    /*mDatabase=FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
    Date date = new Date();
    String fechita = dateFormat.format(date);
    String usu = FirebaseAuth.getInstance().getCurrentUser().getUid();

    */
}


    private static void agregarItem(Articulo item) {
        ITEMS.add(item);
        MAPA_ITEMS.put(item.id, item);
    }


    /**
     * Un 'articulo' representa la estructura general de cada tip de salud
     */
    public static class Articulo {

        private String id;

        private String titulo;

        private String descripcion;
        private String img;

        private String proveedor;
        private String latitud;
        public String getImg(){
            return img;
        }
        public String getId() {
            return id;
        }
        public void setId(String id){
            this.id=id;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getProveedor() {
            return proveedor;
        }

        public String getLatitud() {
            return latitud;
        }

        public String getLongitud() {
            return longitud;
        }

        public String getFecha() {
            return fecha;
        }

        private String longitud;
        private String fecha;

        public Articulo(){

        }

        public Articulo(String descripcion, String fecha, String img,
                        String latitud, String longitud, String proveedor,String titulo) {
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.img = img;
            this.latitud = latitud;
            this.longitud = longitud;
            this.proveedor=proveedor;
            this.titulo= titulo;

        }
    }
}