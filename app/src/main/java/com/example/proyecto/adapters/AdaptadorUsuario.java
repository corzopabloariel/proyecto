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
import com.example.proyecto.entity.User;

import java.util.List;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHolder> {
    private final List<User> valores;

    public AdaptadorUsuario(List<User> items,
                             AdaptadorUsuario.OnItemClickListener escuchaClicksExterna) {
        valores = items;
        this.escuchaClicksExterna = escuchaClicksExterna;
    }

    @Override
    public AdaptadorUsuario.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdaptadorUsuario.ViewHolder holder, int position) {
        holder.item = valores.get(position);
        holder.viewTitulo.setText(valores.get(position).get_name());
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
            return valores.get(posicion).get_id();
        } else {
            return null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView viewTitulo;
        public User item;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            viewTitulo = (TextView) view.findViewById(R.id.tNombre);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaClicksExterna.onClick(this, obtenerIdCategoria(getAdapterPosition()));
        }
    }


    public interface OnItemClickListener {
        public void onClick(AdaptadorUsuario.ViewHolder viewHolder, String idArticulo);
    }

    private AdaptadorUsuario.OnItemClickListener escuchaClicksExterna;
}





