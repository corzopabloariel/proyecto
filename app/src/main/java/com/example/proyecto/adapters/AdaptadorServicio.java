package com.example.proyecto.adapters;
import com.example.proyecto.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.entity.Categorias;
import com.example.proyecto.entity.Publicacion;

import java.util.List;

public class AdaptadorServicio extends RecyclerView.Adapter<AdaptadorServicio.ViewHolder> {
    private final List<Categorias.Categoria> valores;

    public AdaptadorServicio(List<Categorias.Categoria> items,
                             AdaptadorServicio.OnItemClickListener escuchaClicksExterna) {
        valores = items;
        this.escuchaClicksExterna = escuchaClicksExterna;
    }

    @Override
    public AdaptadorServicio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_categorias, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdaptadorServicio.ViewHolder holder, int position) {
        holder.item = valores.get(position);
        holder.viewTitulo.setText(valores.get(position).getNombre());
        Glide.with(holder.itemView.getContext())
                .load(holder.item.getIcono512())
                .into(holder.viewMiniatura);

    }

    @Override
    public int getItemCount() {
        if (valores != null) {
            return valores.size() > 0 ? valores.size() : 0;
        } else {
            return 0;
        }
    }

    private String obtenerIdCategoria(int posicion) {
        if (posicion != RecyclerView.NO_POSITION) {
            return valores.get(posicion).getId();
        } else {
            return null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView viewTitulo;
        public final ImageView viewMiniatura;

        public Categorias.Categoria item;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            viewTitulo = (TextView) view.findViewById(R.id.ctextView);
            viewMiniatura = (ImageView) view.findViewById(R.id.cimgView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaClicksExterna.onClick(this, obtenerIdCategoria(getAdapterPosition()));
        }
    }


    public interface OnItemClickListener {
        public void onClick(AdaptadorServicio.ViewHolder viewHolder, String idArticulo);
    }

    private AdaptadorServicio.OnItemClickListener escuchaClicksExterna;
}





