package com.example.proyecto.entity;

import android.content.ContentValues;

import androidx.annotation.NonNull;

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
    public static final Map<String, Articulo> MAPA_ITEMS = new HashMap<String, Articulo>();

////////////////////////////////////////////////////
        SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
        Date date = new Date();
        String fechita=dateFormat.format(date);
        ///////////////////////////////////////////////////////
    //enzooooooo asi podesp oner la fecha actual en que se creo la publicacion ////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////
    ///para el valor del proveedor tenes que tener una referencia al a base de Firebase como en la clase personafragment
    ///y poner lo siguiente mAuth.getCurrentUser().getUid() esto te da el id del usuario que esta logueado, el que va a subir la publi////


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