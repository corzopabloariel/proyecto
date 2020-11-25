package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessFragment extends Fragment {

    private EditText editTextEmail, editTextPass;
    private String email, pass;
    private Button btnAcceder;

    FirebaseAuth mAuth;

    public AccessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccessFragment newInstance(String param1, String param2) {
        AccessFragment fragment = new AccessFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_access, container, false);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPass = (EditText) view.findViewById(R.id.editTextPass);

        btnAcceder = (Button) view.findViewById(R.id.btnAcceder);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                pass = editTextPass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    accederUsuario();
                } else {
                    Toast.makeText(getActivity(), "Debe completar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void accederUsuario() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}