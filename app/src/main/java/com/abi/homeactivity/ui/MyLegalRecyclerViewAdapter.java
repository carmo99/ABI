package com.abi.homeactivity.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.abi.homeactivity.R;
import com.abi.homeactivity.modelos.Noticia;
import com.bumptech.glide.Glide;

import java.util.List;


public class MyLegalRecyclerViewAdapter extends RecyclerView.Adapter<MyLegalRecyclerViewAdapter.ViewHolder> {

    private final List<Noticia> mValues;
    private final Context context;

    public MyLegalRecyclerViewAdapter(Context contexto, List<Noticia>items) {
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
        holder.textViewTitulo.setText(holder.mItem.getTitulo());
        Glide.with(context).load(holder.mItem.getFoto())
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
        public Noticia mItem;

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