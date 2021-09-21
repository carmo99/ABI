package com.abi.homeactivity.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.informacion.InformacionDiaActivity;
import com.abi.homeactivity.informacion.InformacionLegalActivity;
import com.abi.homeactivity.R;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.popup.PopUpLogOut;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity fa;
    ImageView perfil;
    MapsActivity mapafragment = new MapsActivity(this);
    MensajeFragment fragment_mensaje = new MensajeFragment();
    ContactoFragment fragment_contacto = new ContactoFragment();
    NavigationView nav_view_sidebar;


    AppBarLayout appBarLayout;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    int veces_apretado = 0;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIn = new StringBuilder();
    private ConnectedThread MyConexionBt;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApp.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions( this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1000);

        }
        fa = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        loadFragmentM(mapafragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        appBarLayout = findViewById(R.id.barlayout);
        bottomNavigationView = findViewById(R.id.nav_view);
        perfil = findViewById(R.id.imageViewProfilePrincipal);
        perfil.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        nav_view_sidebar = findViewById(R.id.nav_view_sidebar);

        nav_view_sidebar.setNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId()) {
                case R.id.nav_infoDia:
                    Intent intent1 = new Intent(MainActivity.this, InformacionDiaActivity.class);
                    startActivity(intent1);
                    this.finish();
                    return true;
                case R.id.nav_dispositivoABI:
                    Log.i("Opciones", "ABI");
                    Intent intent4 = new Intent(MainActivity.this, RegistraGadgetActivity.class);
                    startActivity(intent4);
                    this.finish();
                    return true;
                case R.id.nav_infoLegal:
                    Intent intent2 = new Intent(MainActivity.this, InformacionLegalActivity.class);
                    startActivity(intent2);
                    this.finish();
                    return true;
                case R.id.nav_LogOut:
                    Intent intent3 = new Intent(MainActivity.this, PopUpLogOut.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        });

        toolbar();
        //menuHamburguesa.setOnClickListener( this );


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                veces_apretado++;
                if (veces_apretado == 2) {
                    finish();
                }
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        retrofitInit();


        if (SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MAC) != null) {
            boolean espremium = ValidaPremium();
            if (espremium) {
                conectarBluetooth();
            }
            Log.i("dir_mac", SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MAC));
        }



        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest
                        .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);


        }

    }

    private void conectarBluetooth() {
        bluetoothIn = new Handler(){
            public  void handleMessage(android.os.Message msg){
                if (msg.what == handlerState){

                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

    }

    private void VerificarEstadoBT() {
        if (btAdapter == null)
        {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else {
            if (btAdapter.isEnabled()){
            }else{
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1);
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device ) throws IOException
    {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            if(item.getItemId() == R.id.fragment_foto)
            {
                boolean espremium = ValidaPremium();
                if(espremium)
                {
                    Intent i = new Intent(MyApp.getContext(), ActivityFotoDia.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    String mensaje = "No eres PREMIUM_ROL.\n Adquiere tu gadget.";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.35 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
                return true;
            }
            else if(item.getItemId() == R.id.fragment_mensaje)
            {
                boolean espremium = ValidaPremium();
                if(espremium)
                {
                    loadFragmentM(fragment_mensaje);
                    appBarLayout.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    String mensaje = "No eres PREMIUM_ROL.\n Adquiere tu gadget.";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.35 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
                return true;
            }
            else if(item.getItemId() == R.id.fragment_contacto)
            {
                boolean espremium = ValidaPremium();
                if(espremium) {
                    loadFragmentM(fragment_contacto);
                    appBarLayout.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    String mensaje = "No eres PREMIUM_ROL.\n Adquiere tu gadget.";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.35 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
                return true;
            }
            return false;
        }
    };

    private boolean ValidaPremium()
    {
        String rol = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ROL);
        return !rol.equals("USER_ROL");
    }

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    public void loadFragmentM(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    void toolbar(){
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled( true );
            ab.setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent( MainActivity.this, DatosPerfil.class);
        startActivity( i );
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        if ( SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MAC) != null)
        {
            boolean espremium = ValidaPremium();
            if ( espremium )
            {
                BluetoothDevice device = btAdapter.getRemoteDevice( SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MAC) );

                try{
                    btSocket = createBluetoothSocket(device);
                }catch (IOException e){
                    Toast.makeText(getBaseContext(), "La creacion del socket fallo", Toast.LENGTH_SHORT).show();
                }

                try {
                    btSocket.connect();
                }catch (IOException e)
                {
                    try {
                        btSocket.close();
                    }catch (IOException e2)
                    {}
                }
                MyConexionBt = new ConnectedThread(btSocket);
                MyConexionBt.start();
            }
            Log.i("dir_mac", SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MAC) );
        }

    }
    private class ConnectedThread extends Thread
    {
        private BufferedReader mmInStream = null;
        private final OutputStream mmOutStream;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e){
            }

            mmInStream = new BufferedReader( new InputStreamReader(tmpIn, StandardCharsets.UTF_8));
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] byte_in = new byte[50];

            while(true)
            {
                try {
                    String mensaje = mmInStream.readLine();
                    Log.i("abi_boton", mensaje);
                    if ( mensaje.equals("Alert"))
                    {
                        String sms = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MENSAJE) +
                                "\nÚltima información conocida:\nFecha: "+
                                SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FECHA)
                                +"\nUbicación: http://maps.google.com/maps?q="+
                                SharedPreferencesManager.getSomeStringValue(Constantes.PREF_LATITUD)+","+
                                SharedPreferencesManager.getSomeStringValue(Constantes.PREF_LONGITUD)
                                +"\nFoto del día: "+SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_DIA);

                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ESTADO, "EMERGENCIA");
                        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) != null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_1) != "")
                        {
                            enviarMensaje(SharedPreferencesManager.getSomeStringValue(Constantes.TELEFONO_CONT_1),sms);
                        }
                        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) != null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_2) != "")
                        {
                            enviarMensaje(SharedPreferencesManager.getSomeStringValue(Constantes.TELEFONO_CONT_2), sms);
                        }
                        if(SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) != null || SharedPreferencesManager.getSomeStringValue(Constantes.NOMBRE_CONT_3) != "")
                        {
                            enviarMensaje(SharedPreferencesManager.getSomeStringValue(Constantes.TELEFONO_CONT_3), sms);
                        }
                    }
                    //bluetoothIn.obtainMessage(handlerState, ch).sendToTarget();
                }catch (IOException e){
                    break;
                }
            }
        }

        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }catch (IOException e)
            {
                Toast.makeText(getBaseContext(), "La conexion fallo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}