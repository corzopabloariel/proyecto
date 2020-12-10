package com.example.proyecto.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proyecto.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
    public static final List<User> ITEMS = new ArrayList<User>();
    public static User deprueba=new User("Usuario de prueba","deprueba@gmail.com","pass");
    public static final Map<String, User> MAPA_ITEMS = new HashMap<String, User>();
    static {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference mDatabase;
        mDatabase= FirebaseDatabase.getInstance().getReference();
        agregarItem(deprueba);
        FirebaseDatabase.getInstance().getReference().child("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("tag", "Children" + dataSnapshot.getKey());

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            boolean tiene = snapshot.hasChild("servicios");
                            boolean esservicio = snapshot.child("servicios").hasChild(MainActivity.idServicio);
                            if (tiene & esservicio) {
                                String nombre = (String) snapshot.child("nombre").getValue();
                                String email = (String) snapshot.child("email").getValue();
                                String id=snapshot.getKey();
                                User user= new User(nombre,email,"pass");
                                user.set_id(id);
                                agregarItem(user);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });}
        private static void agregarItem(User item) {
        ITEMS.add(item);
         MAPA_ITEMS.put(item.get_id(), item);
                    }
}
