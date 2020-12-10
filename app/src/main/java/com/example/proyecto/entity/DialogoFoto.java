package com.example.proyecto.entity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.proyecto.altaPublicaciones;

public class DialogoFoto extends DialogFragment {
    static final int REQUEST_TAKE_PHOTO = 1;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Selección")
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int cual) {
                        if (opciones[cual].equals("Tomar Foto")) {

                            /*   PERMISOS PARA CAMARA Y ALMACENAMIENTO*/

                            if (ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {


                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                } else {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            225);
                                }


                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.CAMERA)) {

                                } else {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.CAMERA},
                                            226);
                                }
                            } else {

                                /*FUNCION TOMAR FOTO*/

                                altaPublicaciones.desdecamara=true;
                            }

                            Toast.makeText(getActivity().getApplicationContext(), "Tomar Foto", Toast.LENGTH_SHORT).show();
                        } else {
                            if (opciones[cual].equals("Cargar Imagen")) {
                                altaPublicaciones.desdecamara=false;
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);


                                Toast.makeText(getActivity().getApplicationContext(), "Cargar Imagen", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }
                });

        return builder.create();
    }


}
