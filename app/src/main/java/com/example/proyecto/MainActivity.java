package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyecto.entity.Categorias;
import com.example.proyecto.entity.Publicacion;
import com.example.proyecto.entity.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements FragmentoDetalleCategoria.EscuchaFragmento2 ,FirstFragment.EscuchaFragmento {
private boolean tablet;
        public static String idServicio="1";
        public static String uid="id";
        @Override
            public void alSeleccionarItem(String idArticulo) {
                    idServicio =idArticulo;
                    Usuario.limpiarusuarios();
                    Usuario.Cargarusuarios();
                    fragmentDetalle();

                }

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Categorias.cargarcategorias();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        if(findViewById(R.id.flFragment)!=null){
            tablet=true;
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        fragmentHome();
                        break;
                    case R.id.person:
                        if (mAuth.getCurrentUser() != null)
                            fragmentAccount();
                        else
                            fragmentPerson();
                        break;
                    case R.id.map:
                        Toast.makeText(MainActivity.this, "mapa", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Nada", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }


    private void fragmentHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, FirstFragment.crear())
                //.addToBackStack(null)
                .commit();
    }

    private void fragmentPerson() {
        Fragment fragment = new PersonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fragmentAccount() {
        Fragment fragment = new AccountFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void fragmentDetalle(){
        FragmentoDetalleCategoria fragment = new FragmentoDetalleCategoria();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void publicaciones() {
        Publicacion.cargarPublicaciones(uid);
        Fragment fragment = new PublicacionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.home).setChecked(true);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fragmentHome();
        if (mAuth.getCurrentUser() != null) {
            //startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            //finish();
        }
    }

    @Override
    public void alSeleccionarItem2(String idArticulo) {
        uid=idArticulo;
        Usuario.limpiarusuarios();
        Usuario.Cargarusuarios();
        publicaciones();

    }
}