package com.example.proyecto;

import android.Manifest;
import android.app.Activity;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class altaPublicaciones extends Activity {
    private Uri photoURI;
    private String latid,longt,direct;
    private Button bseleccion, bguardar;
    private EditText estado;
    private ImageView imagen;
    final int REQUEST_CODE_GALLERY = 999;
    final int COD_FOTO = 10;
    private String currentPhotoPath = "";
    final int PERMISSIONS_REQUEST_CAMERA = 9;
    private LocationManager ubicacion;
    static final int REQUEST_TAKE_PHOTO = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alta_publicaciones, container, false);
        imagen = (ImageView) findViewById(R.id.IMCamara);
        estado = (EditText) findViewById(R.id.ETEstado);
        bseleccion = (Button) findViewById(R.id.BTSImagen);
        bguardar = (Button) findViewById(R.id.BTGuardar);
        bseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImagen();
            }

            private void CargarImagen() {
            }
        });}*/}

