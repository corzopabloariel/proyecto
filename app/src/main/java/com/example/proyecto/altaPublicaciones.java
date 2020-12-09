package com.example.proyecto;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.proyecto.entity.Publicacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class altaPublicaciones extends Fragment {
    private Uri photoURI;
    private String latid, longt, direct;
    private Button bseleccion, bguardar;
    private EditText estado;
    private ImageView imagen;
    private String imgstring="prueba";
    final int REQUEST_CODE_GALLERY = 999;
    final int COD_FOTO = 10;
    private String currentPhotoPath = "";
    final int PERMISSIONS_REQUEST_CAMERA = 9;
    private LocationManager ubicacion;
    static final int REQUEST_TAKE_PHOTO = 1;
    private DatabaseReference mDatabase;

    public altaPublicaciones() {
        // Required empty public constructor
    }


    public static altaPublicaciones newInstance(String param1, String param2) {
        altaPublicaciones fragment = new altaPublicaciones();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alta_publicaciones, container, false);
        imagen = (ImageView) view.findViewById(R.id.IMCamara);
        estado = (EditText) view.findViewById(R.id.ETEstado);
        bseleccion = (Button) view.findViewById(R.id.BTSImagen);
        bguardar = (Button) view.findViewById(R.id.BTGuardar);
        bseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImagen();
            }

            private void CargarImagen() {
                final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
                final AlertDialog.Builder alertopciones = new AlertDialog.Builder(getActivity());
                alertopciones.setTitle("Seleccione una Opción");
                alertopciones.setItems(opciones, (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Tomar Foto")) {

                            /*   PERMISOS PARA CAMARA Y ALMACENAMIENTO*/

                            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
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

                               TomarFoto();
                            }

                            Toast.makeText(getActivity().getApplicationContext(), "Tomar Foto", Toast.LENGTH_SHORT).show();
                        } else {
                            if (opciones[which].equals("Cargar Imagen")) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);


                                Toast.makeText(getActivity().getApplicationContext(), "Cargar Imagen", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }
                }


                ));
                alertopciones.show();
            }


        });
        //GEOLOCALIZACION

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
           // locationStart();
        }

        // ONCLICK GUARDAR
        bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regMomento();
            }
        });






        return view;
    }
    private void regPublicacion() {
        try {

            //FECHA Y USUARIO SE INSERTAN EN DAO
            Publicacion.Articulo publica;
            //OBTENGO SHARED PREFERENCE DEL USUARIO GUARDADO
            String usu = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String titulo=estado.getText().toString();
            String latitud=latid;
            String longitud=longt;
            String img=imgstring;
            SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
            Date date = new Date();
            String fecha=dateFormat.format(date);
            String desc="descripcion";
            publica=new Publicacion.Articulo(desc,fecha,img,latitud,longitud,usu,titulo);
           // mDatabase.child("publicacion").push();
            DatabaseReference postsRef = mDatabase.child("publicaciones");
            DatabaseReference newPostRef = postsRef.push();
            newPostRef.setValue(publica);
            Toast.makeText(getActivity().getApplicationContext(), "AGREGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            estado.setText("");
            imagen.setImageResource(R.mipmap.ic_launcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void TomarFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.
                resolveActivity(getActivity().getPackageManager()) != null) {


            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI =getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                 /*photoURI = FileProvider.getUriForFile(altaMomentos.this,
                     ,
                        photoFile);*/
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intent.putExtra("return-data", true);
                getActivity().startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream =getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            CameraActivity activity = (CameraActivity)getActivity();

            // Show the full sized image.
            setFullImageFromFilePath(activity.getCurrentPhotoPath(), imagen);

        } else if (requestCode == 5) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BACKUP" + timeStamp + "_";
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
        private void setFullImageFromFilePath(String imagePath, ImageView imageView) {
            // Get the dimensions of the View
            int targetW = imagen.getWidth();
            int targetH = imagen.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            imagen.setImageBitmap(bitmap);
        }

    protected void addPhotoToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        CameraActivity activity = (CameraActivity)getActivity();
        File f = new File(activity.getCurrentPhotoPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.getActivity().sendBroadcast(mediaScanIntent);
    }
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
        });}}*/

