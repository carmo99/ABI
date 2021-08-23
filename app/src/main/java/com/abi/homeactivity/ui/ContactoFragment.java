package com.abi.homeactivity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpBorrarContacto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponseObtenerContactos;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactoFragment extends Fragment implements View.OnClickListener
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    ImageButton atras_contacto;
    Button nuevo_contacto;
    TextView nom_cont_1, nom_cont_2, nom_cont_3;
    ImageView ima_cont_1, ima_cont_2, ima_cont_3, puntos_cont_1, puntos_cont_2, puntos_cont_3;


    public ContactoFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactoFragment newInstance(String param1, String param2) {
        ContactoFragment fragment = new ContactoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_contacto, container, false);
        referenciar();
        eventos();
        return vista;
    }

    private void eventos()
    {
        atras_contacto.setOnClickListener(this);
        nuevo_contacto.setOnClickListener(this);
        puntos_cont_1.setOnClickListener(this);
        puntos_cont_2.setOnClickListener(this);
        puntos_cont_3.setOnClickListener(this);
    }

    private void referenciar()
    {
        atras_contacto = (ImageButton) vista.findViewById(R.id.imageButton_atras_contactos);
        nuevo_contacto = (Button)vista.findViewById(R.id.button_nuevo_contacto);
        nom_cont_1 = (TextView)vista.findViewById(R.id.textView_contacto_1);
        nom_cont_2 = (TextView)vista.findViewById(R.id.textView_contacto_2);
        nom_cont_3 = (TextView)vista.findViewById(R.id.textView_contacto_3);
        ima_cont_1 = (ImageView)vista.findViewById(R.id.icono_contacto_1);
        ima_cont_2 = (ImageView)vista.findViewById(R.id.icono_contacto_2);
        ima_cont_3 = (ImageView)vista.findViewById(R.id.icono_contacto_3);
        puntos_cont_1 = (ImageView)vista.findViewById(R.id.more_contacto_1);
        puntos_cont_2 = (ImageView)vista.findViewById(R.id.more_contacto_2);
        puntos_cont_3 = (ImageView)vista.findViewById(R.id.more_contacto_3);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton_atras_contactos)
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else if(view.getId() == R.id.button_nuevo_contacto)
        {
            Intent intent = new Intent(getActivity(), RegistroContactosActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else if(view.getId() == R.id.more_contacto_1)
        {
            if(!(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) == ""))
            {
                Bundle parametros = new Bundle();
                parametros.putString("Contacto", "contactoEmergencia1");
                Intent intent = new Intent(getActivity(), PopUpBorrarContacto.class);
                intent.putExtras(parametros);
                startActivity(intent);
            }
        }
        else if(view.getId() == R.id.more_contacto_2)
        {
            if(!(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) == ""))
            {
                Bundle parametros = new Bundle();
                parametros.putString("Contacto", "contactoEmergencia2");
                Intent intent = new Intent(getActivity(), PopUpBorrarContacto.class);
                intent.putExtras(parametros);
                startActivity(intent);
            }
        }
        else if(view.getId() == R.id.more_contacto_3)
        {
            if(!(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) == ""))
            {
                Bundle parametros = new Bundle();
                parametros.putString("Contacto", "contactoEmergencia3");
                Intent intent = new Intent(getActivity(), PopUpBorrarContacto.class);
                intent.putExtras(parametros);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) == "")
        {
            nom_cont_1.setText("Nombre de Contacto 1");
            ima_cont_1.setImageResource(R.drawable.ic_profile);
        }
        else
        {
            nom_cont_1.setText(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1));
            Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.FOTO_CONT_1))
                    .into(ima_cont_1);
        }
        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) == "")
        {
            nom_cont_2.setText("Nombre de Contacto 2");
            ima_cont_2.setImageResource(R.drawable.ic_profile);
        }
        else
        {
            nom_cont_2.setText(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2));
            Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.FOTO_CONT_2))
                    .into(ima_cont_2);
        }
        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) == null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) == "")
        {
            nom_cont_3.setText("Nombre de Contacto 3");
            ima_cont_3.setImageResource(R.drawable.ic_profile);
        }
        else
        {
            nom_cont_3.setText(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3));
            Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.FOTO_CONT_3))
                    .into(ima_cont_3);
        }

    }
}