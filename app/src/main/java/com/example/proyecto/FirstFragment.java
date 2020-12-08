package com.example.proyecto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.adapters.AdaptadorArticulos;
import com.example.proyecto.adapters.AdaptadorServicio;
import com.example.proyecto.entity.Categorias;
import com.example.proyecto.entity.Publicacion;

public class FirstFragment extends Fragment implements AdaptadorServicio.OnItemClickListener {

    private EscuchaFragmento escucha;

    public FirstFragment() {

    }

    public static FirstFragment crear() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Manejo de argumentos
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        View recyclerView = v.findViewById(R.id.recycleSe);

        //assert recyclerView != null;
        prepararLista((RecyclerView) recyclerView);

        return v;
    }


    private void prepararLista(@NonNull RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setAdapter(new AdaptadorServicio(Categorias.ITEMS, this));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EscuchaFragmento) {
            escucha = (EscuchaFragmento) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debes implementar EscuchaFragmento");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        escucha = null;
    }

    @Override
    public void onClick(AdaptadorServicio.ViewHolder viewHolder, String idArticulo) {

    }

    public interface EscuchaFragmento {
        void alSeleccionarItem(String idArticulo);
    }
}
