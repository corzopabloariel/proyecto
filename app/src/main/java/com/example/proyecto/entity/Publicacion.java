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

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
        Date date = new Date();
        String fechita=dateFormat.format(date);
        ContentValues values= new ContentValues();
        // Añadir elementos de ejemplo
        agregarItem(new Articulo(generarId(),
                "10 Plantillas Para Determinar Tus Necesidades Calóricas",
                "Descarga nuestras plantillas para Microsoft Excel que te permitirán, calcular exactamente...", fechita, "https://firebasestorage.googleapis.com/v0/b/proyecto-app-1954e.appspot.com/o/publicaciones%2Ftubing-2672187_640.jpg?alt=media&token=157da151-c2b9-46ed-944f-11d3ebc10a16"));
        agregarItem(new Articulo(generarId(),
                "¿Qué Tan Malo Es Consumir Chocolate?",
                "Si aún no has podido resolver tus dudas sobre comer chocolate, entonces este artículo es para ti...",
                "11 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo2.jpg"));
        agregarItem(new Articulo(generarId(),
                "Guía Para Identificar Alimentos Con Trigo Perjudiciales",
                "Aprende a distinguir aquellos alimentos que están hechos de trigo mal procesado...",
                "12 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo3.jpg"));
        agregarItem(new Articulo(generarId(),
                "Descubre Qué Harían 10 Minutos De Ejercicio Diario En Tu Cuerpo",
                "Entra y descubre los beneficios de realizar una rutina de 10 minutos los 7 días...",
                "13 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo4.jpg"));
        agregarItem(new Articulo(generarId(),
                "Aumentando Las Defensas Del Cuerpo Con Frutos Rojos",
                "Los frutos rojos frecuentemente son ignorados por la mayoría de las personas, pero hoy te mostraremos...",
                "14 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo5.jpg"));
        agregarItem(new Articulo(generarId(),
                "5 Recetas Bajas En Grasa Para El Almuerzo",
                "Variar las recetas a la hora de alimentarse permite obtener distintos beneficios dependiendo del alimento, ...",
                "15 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo6.jpg"));
        agregarItem(new Articulo(generarId(),
                "Combina Cardio + Fuerza Para Obtener Verdaderos Resultados",
                "Muchos creen que solo realizar ejercicios de cardio los hará perder de peso y...",
                "16 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo7.jpg"));
        agregarItem(new Articulo(generarId(),
                "Planificador Semanal Para Optimizar Tu Dieta",
                "Descarga nuestro nueva plantilla para prácticar buenos hábitos alimenticios. Se trata de...",
                "17 de Enero", "https://www.develou.com/wp-content/uploads/2016/01/articulo8.jpg"));
        agregarItem(new Articulo(generarId(),
                "Las 30 Razones De Por Qué Superman Come Cacahuates",
                "Los frutos secos han sido desde la antiguedad la principal fuente de proteínas para muchas civilizaciones...",
                "18 de Enero", "https://firebasestorage.googleapis.com/v0/b/proyecto-app-1954e.appspot.com/o/iconos%2Fpintura.png?alt=media&token=d343d2d7-f2cb-473f-967d-20ce8fa2b329"));

    }

    @NonNull
    private static String generarId() {
        return UUID.randomUUID().toString();
    }


    private static void agregarItem(Articulo item) {
        ITEMS.add(item);
        MAPA_ITEMS.put(item.id, item);
    }


    /**
     * Un 'articulo' representa la estructura general de cada tip de salud
     */
    public static class Articulo {

        public final String id;

        public final String titulo;

        public final String descripcion;

       // public final String latitud;
     //   public final String longitud;
        public final String fecha;

        public final String urlMiniatura;

        public Articulo(String id, String titulo, String descripcion,
                        String fecha, String urlMiniatura) {
            this.id = id;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.urlMiniatura = urlMiniatura;
        }
    }
}