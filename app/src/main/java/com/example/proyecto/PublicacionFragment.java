package com.example.proyecto;

import android.os.Bundle;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.adapters.AdaptadorArticulos;
import com.example.proyecto.entity.Publicacion;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PublicacionFragment extends Fragment implements AdaptadorArticulos.OnItemClickListener {

    private EscuchaFragmento escucha;

    public PublicacionFragment() {

    }

    public static PublicacionFragment crear() {
        return new PublicacionFragment();
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
        recyclerView.setAdapter(new AdaptadorArticulos(Publicacion.ITEMS, this));
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
    public void onClick(AdaptadorArticulos.ViewHolder viewHolder, String idArticulo) {

    }

    public interface EscuchaFragmento {
        void alSeleccionarItem(String idArticulo);
    }
}
