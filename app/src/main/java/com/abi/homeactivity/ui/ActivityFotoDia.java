package com.abi.homeactivity.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.ui.MainActivity;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFotoDia extends AppCompatActivity implements View.OnClickListener, PermissionListener {

    ImageButton atras_foto;
    Button botonSubir;
    ImageView fotoDia;
    TextView arriba_1, arriba_2, mensaje_subido;

    MutableLiveData<String> fotoNueva;
    PermissionListener allpermissionsListener;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_foto);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(MyApp.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
        fotoNueva = new MutableLiveData<>();
        atras_foto = findViewById(R.id.imageButton_atras_foto);
        botonSubir = findViewById(R.id.button_foto_dia);
        fotoDia = findViewById(R.id.imagenDia);
        arriba_1 = findViewById(R.id.textView_arriba_1);
        arriba_2 = findViewById(R.id.textView_arriba_2);
        mensaje_subido = findViewById(R.id.textViewMensajeSubido);

        botonSubir.setOnClickListener(this);
        atras_foto.setOnClickListener(this);

        if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_DIA) == null || SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_DIA) == "")
        {
            fotoDia.setImageResource(R.drawable.ic_upload);
            arriba_1.setText(R.string.texto_descripcion_foto_1);
            arriba_2.setText(R.string.texto_descripcion_foto_2);
            mensaje_subido.setText(R.string.texto_descripcion_abajo);
        }
        else
        {
            arriba_1.setText(R.string.texto_descripcion_foto_1_subida);
            arriba_2.setText(R.string.texto_descripcion_foto_2_subida);
            mensaje_subido.setText(R.string.texto_descripcion_abajo_subida);
            Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_DIA))
                    .into(fotoDia);
        }
        retrofitInit();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_foto_dia)
        {
            Log.i("MECO", "Hola");
            checkPermissions();
        }
        else if(view.getId() == R.id.imageButton_atras_foto)
        {
            Intent intent = new Intent(MyApp.getContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void retrofitInit()
    {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    private void checkPermissions()
    {
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(this)
                        .withTitle("Permisos")
                        .withMessage("Los permisos solicitados son necesarios para seleccionar una foto.")
                        .withButtonText("Aceptar")
                        .withIcon(R.mipmap.ic_launcher)
                        .build();

        allpermissionsListener = new CompositePermissionListener(
                (PermissionListener) this,
                dialogOnDeniedPermissionListener
        );

        Dexter.withContext(MyApp.getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(allpermissionsListener)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == Constantes.FOTO_SELECCIONADA) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData(); // content://gallery/photos/..
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imagenSeleccionada,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        // "filename" = filePathColumn[0]
                        int imagenIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String fotoPath = cursor.getString(imagenIndex);
                        SubirFoto(fotoPath);
                        cursor.close();
                    }
                }
            }
        }
    }

    private void SubirFoto(String url)
    {
        File file = new File(url);
        RequestBody descriptionBody = RequestBody.create(MultipartBody.FORM, "Alguna descripci??n");
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("archivo", file.getName(), imageBody);
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        Call<ResponseLogIn> call = authABIService.cambiarFotoDia(imagePart, descriptionBody);
        call.enqueue(new Callback<ResponseLogIn>() {
            @Override
            public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                PopUpCargando.fa.finish();
                if(response.isSuccessful())
                {
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_FOTO_DIA, response.body().getUsuario().getFotoDia());
                    arriba_1.setText(R.string.texto_descripcion_foto_1_subida);
                    arriba_2.setText(R.string.texto_descripcion_foto_2_subida);
                    mensaje_subido.setText(R.string.texto_descripcion_abajo_subida);
                    Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_DIA))
                            .into(fotoDia);
                    String mensaje = "??Tu foto del d??a ha sido actualizada!";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
                else
                {
                    try {
                        String respuesta [] = response.errorBody().string().split("\"");
                        Bundle parametros = new Bundle();
                        int caracteres_totales = respuesta[3].length();
                        caracteres_totales = caracteres_totales/21;
                        float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                        parametros.putString("Mensaje", respuesta[3]);
                        parametros.putFloat("Espacio", espacio_total);
                        Intent i = new Intent(getApplicationContext(), PopUpError.class);
                        i.putExtras(parametros);
                        startActivity(i);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogIn> call, Throwable t)
            {
                PopUpCargando.fa.finish();
                String mensaje = "Error en la conexi??n, intentalo nuevamente";
                Bundle parametros = new Bundle();
                int caracteres_totales = mensaje.length();
                caracteres_totales = caracteres_totales/21;
                float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                parametros.putFloat("Espacio", espacio_total);
                parametros.putString("Mensaje", mensaje);
                Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                i.putExtras(parametros);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        Intent seleccionarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(seleccionarFoto, Constantes.FOTO_SELECCIONADA);
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

    }
}