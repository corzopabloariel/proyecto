package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    private EditText editTextName, editTextEmail, editTextPass;
    private String name, email, pass;
    private Button btnRegistrar, btnAcceder;

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
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
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPass = (EditText) view.findViewById(R.id.editTextPass);
        btnRegistrar = (Button) view.findViewById(R.id.btnRegistrar);
        btnAcceder = (Button) view.findViewById(R.id.btnAcceder);

        getActivity().findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FirstFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flFragment, fragment, fragment.getTag())
                        .commit();
            }
        });
        return view;
    }

    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("name", name);
                    map.put("email", email);
                    map.put("pass", pass);

                    String id = mAuth.getCurrentUser().getUid();
                    mDataBase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task_u) {
                            if (task_u.isSuccessful()) {
                                startActivity(new Intent(getActivity(), ProfileActivity.class));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}