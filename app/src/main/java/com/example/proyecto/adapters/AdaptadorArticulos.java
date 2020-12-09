package com.example.proyecto.adapters;
import com.example.proyecto.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.entity.Publicacion;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} que alimenta la lista con
 * instancias {@link Publicacion.Articulo}
 */
public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ViewHolder> {

    private final List<Publicacion.Articulo> valores;

    public AdaptadorArticulos(List<Publicacion.Articulo> items,
                              OnItemClickListener escuchaClicksExterna) {
        valores = items;
        this.escuchaClicksExterna = escuchaClicksExterna;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_articulos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = valores.get(position);
        holder.viewTitulo.setText(valores.get(position).getTitulo());
        holder.viewResumen.setText(valores.get(position).getDescripcion());
        holder.viewFecha.setText(valores.get(position).getFecha());
        Glide.with(holder.itemView.getContext())
                .load(holder.item.getImg())
                .thumbnail(0.1f)
                .centerCrop()
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


    private String obtenerIdArticulo(int posicion) {
        if (posicion != RecyclerView.NO_POSITION) {
            return valores.get(posicion).getId();
        } else {
            return null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView viewTitulo;
        public final TextView viewResumen;
        public final TextView viewFecha;
        public final ImageView viewMiniatura;

        public Publicacion.Articulo item;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            viewTitulo = (TextView) view.findViewById(R.id.titulo);
            viewResumen = (TextView) view.findViewById(R.id.resumen);
            viewFecha = (TextView) view.findViewById(R.id.fecha);
            viewMiniatura = (ImageView) view.findViewById(R.id.miniatura);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escuchaClicksExterna.onClick(this, obtenerIdArticulo(getAdapterPosition()));
        }
    }


    public interface OnItemClickListener {
        public void onClick(ViewHolder viewHolder, String idArticulo);
    }

    private OnItemClickListener escuchaClicksExterna;
}