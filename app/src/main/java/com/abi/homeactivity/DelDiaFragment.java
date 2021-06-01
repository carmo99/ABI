package com.abi.homeactivity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DelDiaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;

    List<Informacion> informacionList;

    MyDelDiaRecyclerViewAdapter adapterInformacion;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DelDiaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DelDiaFragment newInstance(int columnCount) {
        DelDiaFragment fragment = new DelDiaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            informacionList = new ArrayList<>();

            informacionList.add(new Informacion("Todo lo que debes saber del mes Pride", "https://img.chilango.com/2019/06/fiestas-pride-2019-1.jpg"));
            informacionList.add(new Informacion("Las zonas más peligrosas de la CDMX", "https://www.aquien.mx/wp-content/uploads/2020/03/zonaspeligrosas-ssc.jpg"));
            informacionList.add(new Informacion("Incidencia delictiva en la CDMX", "https://cdn-3.expansion.mx/c1/95/5fb13e8847ee9155b5463735cea9/cuartoscuro-719192-digital.jpeg"));
            informacionList.add(new Informacion("Semáforo epidemiológico CDMX", "https://imagenes.milenio.com/yCy_3qwOEpVYdSzrx_W8dMzFA0Q=/958x596/https://www.milenio.com/uploads/media/2021/02/12/cdmx-regresa-semaforo-naranja-covid.jpg"));

            adapterInformacion = new MyDelDiaRecyclerViewAdapter(getActivity(),informacionList);
            recyclerView.setAdapter(adapterInformacion);
        }
        return view;
    }
}