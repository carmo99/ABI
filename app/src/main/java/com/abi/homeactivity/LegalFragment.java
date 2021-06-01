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
public class LegalFragment extends Fragment {
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
        public LegalFragment() {
        }

        // TODO: Customize parameter initialization
        @SuppressWarnings("unused")
        public static com.abi.homeactivity.DelDiaFragment newInstance(int columnCount) {
            com.abi.homeactivity.DelDiaFragment fragment = new com.abi.homeactivity.DelDiaFragment();
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

                informacionList.add(new Informacion("¿Cómo denunciar un robo?", "https://www.eleconomista.com.mx/__export/1576085766329/sites/eleconomista/img/2019/12/11/robo-delito-mexico.jpg_554688468.jpg"));
                informacionList.add(new Informacion("¿Cómo denunciar un secuestro?", "https://imagenes.milenio.com/fkaH7duA88o1sn284wZVug-1QgA=/958x596/https://www.milenio.com/uploads/media/2019/08/09/secuestro-especial_0_4_678_422.jpeg"));
                informacionList.add(new Informacion("¿Cómo denunciar asalto?", "https://img.unocero.com/2019/07/delito-grave-robo-de-celular.jpg"));
                informacionList.add(new Informacion("¿Cómo denunciar feminicidio?", "https://www.gob.mx/cms/uploads/article/main_image/26656/feminicidio-2.jpg"));

                adapterInformacion = new MyDelDiaRecyclerViewAdapter(getActivity(),informacionList);
                recyclerView.setAdapter(adapterInformacion);
            }
            return view;
        }
    }