package com.example.proyecto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.adapters.AdaptadorServicio;
import com.example.proyecto.adapters.AdaptadorUsuario;
import com.example.proyecto.entity.Categorias;
import com.example.proyecto.entity.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoDetalleCategoria extends Fragment implements AdaptadorUsuario.OnItemClickListener {
    // EXTRA
    public static final String ID_ARTICULO = "extra.idArticulo";
    private EscuchaFragmento escucha;

    private Categorias.Categoria itemDetallado;
    public FragmentoDetalleCategoria() {
        // Required empty public constructor
    }

    public static FragmentoDetalleCategoria newInstance(String param1, String param2) {
        FragmentoDetalleCategoria fragment = new FragmentoDetalleCategoria();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmen_public, container, false);

        View recyclerView = v.findViewById(R.id.recycleSen);

        //assert recyclerView != null;
        prepararLista((RecyclerView) recyclerView);

        return v;
    }


    private void prepararLista(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new AdaptadorUsuario(Usuario.ITEMS, this));
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
    public void onClick(AdaptadorUsuario.ViewHolder viewHolder, String idArticulo) {

    }
    public interface EscuchaFragmento {
        void alSeleccionarItem(String idArticulo);
    }
}