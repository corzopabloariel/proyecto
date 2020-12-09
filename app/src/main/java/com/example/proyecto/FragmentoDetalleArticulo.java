package com.example.proyecto;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.proyecto.entity.Categorias;

/**
 * Fragmento que representa el panel del detalle de un artículo.
 */
public class FragmentoDetalleArticulo extends Fragment {

    // EXTRA
    public static final String ID_CATEGORIA = "extra.idCategoria";

    // Categoria al cual está ligado la UI
    private Categorias.Categoria itemDetallado;

    public FragmentoDetalleArticulo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ID_CATEGORIA)) {
            // Cargar modelo según el identificador
            itemDetallado = Categorias.MAPA_ITEMS.get(getArguments().getString(ID_CATEGORIA));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_detalle_categoria, container, false);

        if (itemDetallado != null) {
            // Toolbar en master-detail
            ((TextView) v.findViewById(R.id.titulo)).setText(itemDetallado.getNombre());
            /////////////////////////////////////////////////////
            ///////////////cargarfragmento/////////////////////////////////////
            ///////////////////////////////////////////////////
            Glide.with(this)
                    .load(itemDetallado.getImgToolbar())
                    .into((ImageView) v.findViewById(R.id.imagen));
        }

        return v;
    }
}