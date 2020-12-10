package com.example.proyecto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.proyecto.entity.DialogoFoto;
import com.example.proyecto.entity.DialogoIdioma;
import com.example.proyecto.entity.Publicacion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class altaPublicaciones extends AppCompatActivity {

    //Referencia a la base de datos
    FirebaseStorage storage;
    FirebaseDatabase mDatabase ;
    StorageReference mStorageRef;

    //Recursos obtenidos de los permisos
    Uri photoURI;
    private String latid,longt,direct;
    private String currentPhotoPath = "";
    private LocationManager ubicacion;

    //Recursos de la actividad
    private Button bseleccion, bguardar;
    private EditText titulo;
    private EditText descripcion;
    private ImageView imagen;
    public static boolean desdecamara=false;

    final String url="test";
    final int REQUEST_CODE_GALLERY = 999;
    final int COD_FOTO = 10;
    final int PERMISSIONS_REQUEST_CAMERA = 9;
    static final int REQUEST_TAKE_PHOTO = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_publicaciones);
        //Referencia a una instancia firebase
        mDatabase=FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        imagen = (ImageView) findViewById(R.id.IMCamara);
        titulo = (EditText) findViewById(R.id.ETtitulo);
        descripcion=(EditText)findViewById(R.id.ETDescrip);
        bseleccion = (Button) findViewById(R.id.BTSImagen);
        bguardar = (Button) findViewById(R.id.BTGuardar);
        bseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogoFoto dialogo = new DialogoFoto();
                        dialogo.show(fragmentManager, "tagAlerta");

                if (desdecamara==true) {
                    TomarFoto();
                    desdecamara=false;
                    dialogo.dismiss();

                }
                    }

        });
        //GEOLOCALIZACION

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        // ONCLICK GUARDAR
        bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regPublicacion();
            }
        });

    }

    private void regPublicacion() {
        try {
            Publicacion.Articulo publica;
            String usu = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String titul=titulo.getText().toString();
            String descrip=descripcion.getText().toString();
            String latitud=latid;
            String longitud=longt;
            String img=photoURI.toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
            Date date = new Date();
            File localFile = File.createTempFile("images", "jpg");
            String fecha=dateFormat.format(date);
            publica=new Publicacion.Articulo(descrip,fecha,img,latitud,longitud,usu,titul);
            // mDatabase.child("publicacion").push();

            DatabaseReference postsRef = mDatabase.getReference().child("publicacion");
            DatabaseReference newPostRef = postsRef.push();
            newPostRef.setValue(publica);
            Toast.makeText(getApplicationContext(), "AGREGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            titulo.setText("");
            descripcion.setText("");
            imagen.setImageResource(R.mipmap.ic_launcher);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BACKUP" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "AGREGAANDO IMAGEN", Toast.LENGTH_SHORT).show();
            }
            return;

        } else if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String LOG_TAG = "mal";
                //El permiso se acepto.
                Log.d(LOG_TAG, "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();

            } else {

                //El permiso NO fue aceptado.

            }
            return;
        }else if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public  void TomarFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.
                resolveActivity(getPackageManager()) != null) {


            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Mi foro");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Foto tomada en " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagen.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                imagen.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dato = baos.toByteArray();
                Log.d("aa",mDatabase.getReference().child("publicacione").toString());
                Log.d("aaaa",photoURI.toString());

                StorageReference publicaRef = mStorageRef.child("images/publicacion"+ photoURI.toString()+".jpg");
              UploadTask uploadTask=publicaRef.putBytes(dato);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        photoURI = uri;
                                        //createNewPost(imageUrl);
                                    }
                                });
                            }
                        }
                    }
                });
                publicaRef.putBytes(dato);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 5) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //GEOLOCALIZACION

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direct=DirCalle.getAddressLine(0);
                    latid=String.valueOf(loc.getLatitude());
                    longt=String.valueOf(loc.getLongitude());

                   /* mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    /*    mensaje1.setText("Localización agregada");
        mensaje2.setText("");*/
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        altaPublicaciones altaM;

        public altaPublicaciones getMainActivity() {
            return altaM;
        }

        public void setMainActivity(altaPublicaciones altaM) {
            this.altaM = altaM;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();

            this.altaM.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            /*  mensaje1.setText("GPS Desactivado");*/
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            /*   mensaje1.setText("GPS Activado");*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
