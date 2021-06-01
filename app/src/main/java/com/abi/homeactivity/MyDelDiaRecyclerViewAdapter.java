package com.abi.homeactivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyDelDiaRecyclerViewAdapter extends RecyclerView.Adapter<MyDelDiaRecyclerViewAdapter.ViewHolder> {

    private final List<Informacion> mValues;
    private final Context context;

    public MyDelDiaRecyclerViewAdapter(Context contexto, List<Informacion>items) {
        mValues = items;
        context = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textViewTitulo.setText(holder.mItem.getTituloInformacion());
        Glide.with(context).load(holder.mItem.getUrlFoto())
                .centerCrop()
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewTitulo;
        public final ImageView imagen;
        public Informacion mItem;

        public ViewHolder(View view) {
            super(view);
            textViewTitulo = view.findViewById(R.id.textViewTitulo);
            imagen = view.findViewById(R.id.imageViewinformacion);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewTitulo.getText() + "'";
        }
    }
}