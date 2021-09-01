package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.ui.MainActivity;

import java.util.Set;

public class VincularABIActivity extends AppCompatActivity {
    private static final String TAG = "DispositivosVinculados";

    ListView IdLista;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter mPairedDevicesArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_a_b_i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        VerificarEstadoBT();
        mPairedDevicesArrayAdapter = new ArrayAdapter(this, R.layout.dispositivos_encontrados);
        IdLista = findViewById(R.id.idListaDispositivos);
        IdLista.setAdapter(mPairedDevicesArrayAdapter);
        IdLista.setOnItemClickListener(mDeviceClickListener);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairDevices = mBtAdapter.getBondedDevices();

        if ( pairDevices.size() > 0)
        {
            for (BluetoothDevice device: pairDevices){
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress() );
            }
        }

    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String info = ((TextView) view).getText().toString();
            String address = info.substring( info.length() - 17);

            finishAffinity();

            Intent intend = new Intent(VincularABIActivity.this, MainActivity.class);
            //intend.putExtra( EXTRA_DEVICE_ADDRESS, address);
            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_MAC, address);
            startActivity( intend );
        }
    };

    private void VerificarEstadoBT() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if ( mBtAdapter == null)
        {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_LONG);
        }else
        {
            if (mBtAdapter.isEnabled())
            {
                Log.d(TAG, "...Bluetooth Activado...");
            }else
            {
                Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult( enableBtIntent, 1 );
            }
        }
    }
}