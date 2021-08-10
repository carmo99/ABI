package com.abi.homeactivity.informacion;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.modelos.Noticia;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.response.ResponseClasificacion;
import com.abi.homeactivity.ui.MyDelDiaRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LegalFragment extends Fragment {
        // TODO: Customize parameter argument names
        private static final String ARG_COLUMN_COUNT = "column-count";
        // TODO: Customize parameters
        private int mColumnCount = 1;
        RecyclerView recyclerView;

        ABIClient abiClient;
        ABIService abiService;

        List<Noticia> informacionList;

        MyDelDiaRecyclerViewAdapter adapterInformacion;


        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */
        public LegalFragment() {
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
            retrofitInit();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_item_list2, container, false);

            // Set the adapter
            if (view instanceof RecyclerView)
            {
                Context context = view.getContext();
                recyclerView = (RecyclerView) view;
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                retrofitInit();
                cargarInfo();
            }
            return view;
        }

    private void retrofitInit()
    {
        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();
    }

    private void cargarInfo()
    {
        Call<ResponseClasificacion> call = abiService.responseClasificacionLegal();
        call.enqueue(new Callback<ResponseClasificacion>() {
            @Override
            public void onResponse(Call<ResponseClasificacion> call, Response<ResponseClasificacion> response) {
                if(response.isSuccessful())
                {
                    informacionList = response.body().getNoticias();
                    adapterInformacion = new MyDelDiaRecyclerViewAdapter(getActivity(),informacionList);
                    recyclerView.setAdapter(adapterInformacion);
                }
                else
                {
                    Toast.makeText(MyApp.getContext(), "Algo falló", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClasificacion> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Problemas de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }
}