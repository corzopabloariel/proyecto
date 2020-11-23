package com.example.proyecto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    private EditText editTextName, editTextEmail, editTextPass;
    private String name, email, pass;
    private Button btnRegistrar, btnAcceder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        editTextName = (EditText) getActivity().findViewById(R.id.editTextName);
        editTextEmail = (EditText) getActivity().findViewById(R.id.editTextEmail);
        editTextPass = (EditText) getActivity().findViewById(R.id.editTextPass);

        btnRegistrar = (Button) getActivity().findViewById(R.id.btnRegistrar);
        btnAcceder = (Button) getActivity().findViewById(R.id.btnAcceder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);
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
                                //startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                //finish();
                            } else {
                                //Toast.makeText(MainActivity.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    //Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}