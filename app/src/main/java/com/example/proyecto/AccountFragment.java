package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.entity.DialogoIdioma;
import com.example.proyecto.entity.Publicacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment  {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnSalir, btnDatos, btnPublicaciones, btnAlta;
    private TextView txtBienvenida;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtBienvenida = (TextView) view.findViewById(R.id.txtBienvenida);
        btnSalir = (Button) view.findViewById(R.id.btnSalir);
        btnDatos = (Button) view.findViewById(R.id.btnDatos);
        btnAlta = (Button) view.findViewById(R.id.btnAlta);
        btnPublicaciones = (Button) view.findViewById(R.id.btnPublicaciones);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicaciones();
            }
        });
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    DialogoIdioma dialogo = new DialogoIdioma();
                    dialogo.show(fragmentManager, "tagAlerta");
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altaPublicaciones();
            }
        });
        getUserInfo();
        return view;
    }

    private void getUserInfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    txtBienvenida.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    private void altaPublicaciones() {
        Intent intent = new Intent(getActivity(), altaPublicaciones.class);
        startActivity(intent);
    }

    private void publicaciones() {
        Publicacion.cargarPublicaciones(mAuth.getUid());
        Fragment fragment = new PublicacionFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}