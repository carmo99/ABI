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

public class MyDelDiaRecyclerViewAdapter extends RecyclerView.Adapter<MyDelDiaRecyclerViewAdapter.ViewHolder>
implements View.OnClickListener{

    private final List<Noticia> mValues;
    private final Context context;
    private View.OnClickListener listener;

    public MyDelDiaRecyclerViewAdapter(Context contexto, List<Noticia>items) {
        mValues = items;
        context = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        view.setOnClickListener(this);
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

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
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