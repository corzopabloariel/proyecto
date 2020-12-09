package com.example.proyecto.entity;

import android.content.ContentValues;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.proyecto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
static {
    mDatabase=FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
    Date date = new Date();
    String fechita = dateFormat.format(date);
    String usu = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Articulo publica = new Articulo("Probando1", fechita, "https://kuzudecoletaje.es/wp-content/uploads/2016/06/la_importancia_de_medir.jpg", "41.40338", "41.40338", usu, "1");
    agregarItem(publica);
    Articulo publica2 = new Articulo("Probando2", fechita, "https://kuzudecoletaje.es/wp-content/uploads/2016/06/la_importancia_de_medir.jpg", "41.40338", "41.40338", usu, "2");
    agregarItem(publica);
    Articulo publica3 = new Articulo("Probando3", fechita, "https://kuzudecoletaje.es/wp-content/uploads/2016/06/la_importancia_de_medir.jpg", "41.40338", "41.40338", usu, "3");
    agregarItem(publica);
    agregarItem(publica2);
    agregarItem(publica3);

    // mDatabase.child("publicacion").push();
    DatabaseReference postsRef = mDatabase.child("publicaciones");
    DatabaseReference newPostRef = postsRef.push();
    newPostRef.setValue(publica);
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