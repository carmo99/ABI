package com.abi.homeactivity.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.data.PerfilViewModel;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestPerfil;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponsetPerfil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import static com.google.gson.reflect.TypeToken.get;

public class DatosPerfil extends AppCompatActivity implements View.OnClickListener, PermissionListener{

    Button cambiar_contrasenia, cambiar_foto, actualizar_datos;
    EditText nombre, correo, telefono;
    ImageButton atras;
    AuthABIService authABIService;
    AuthABIClient authABIClient;

    ImageView fotoPerfil;
    MutableLiveData<String> fotoNueva;
    PermissionListener allpermissionsListener;


    private PerfilViewModel perfilViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_perfil);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed()
            {
                Intent intent = new Intent(DatosPerfil.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
        fotoNueva = new MutableLiveData<>();

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        fotoPerfil = findViewById(R.id.foto_perfil_datos);
        Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_PERFIL))
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(fotoPerfil);
        cambiar_contrasenia = findViewById(R.id.buttonCambiarContrasenia);
        atras = findViewById(R.id.imageButtonDatosPerfilToMainActivity);
        cambiar_foto = findViewById(R.id.buttonCambiarFoto);
        nombre = findViewById(R.id.editTextTextNombreUsuario);
        correo = findViewById(R.id.editTextCorreoUsuario);
        telefono = findViewById(R.id.editTextTelefonoUsuario);
        actualizar_datos = findViewById(R.id.buttonActualizarDatos);

        retrofitInit();

        cambiar_contrasenia.setOnClickListener(this);
        cambiar_foto.setOnClickListener(this);
        atras.setOnClickListener(this);
        actualizar_datos.setOnClickListener(this);

        perfilViewModel.perfilUsuario.observe(this, new Observer<ResponsetPerfil>() {
            @Override
            public void onChanged(ResponsetPerfil responsetPerfil) {
                nombre.setText(responsetPerfil.getNombre());
                correo.setText(responsetPerfil.getCorreo());
                telefono.setText(responsetPerfil.getTelefono());
            }
        });

    }
    private void retrofitInit()
    {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    public void SubirFoto(String url)
    {
        File file = new File(url);
        RequestBody descriptionBody = RequestBody.create(MultipartBody.FORM, "Alguna descripción");
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("archivo", file.getName(), imageBody);
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        Call<ResponseLogIn> call = authABIService.cambiarFotoPerfil(imagePart, descriptionBody);
        call.enqueue(new Callback<ResponseLogIn>() {
            @Override
            public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                PopUpCargando.fa.finish();
                if(response.isSuccessful())
                {
                    String mensaje = "¡Tu foto de perfil ha sido actualizada!";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FOTO_PERFIL, response.body().getUsuario().getFotoPerfil());
                    Glide.with(MyApp.getContext()).load(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FOTO_PERFIL))
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(fotoPerfil);
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
                String mensaje = "Error en la conexión, intentalo nuevamente";
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
    public void onClick(View view) {
        switch( view.getId() ){
            case R.id.buttonCambiarContrasenia:
                Intent intent1 = new Intent(DatosPerfil.this, ActivityContrasenia.class);
                startActivity( intent1 );
                this.finish();
                break;
            case R.id.imageButtonDatosPerfilToMainActivity:
                Intent intent2 = new Intent(DatosPerfil.this, MainActivity.class);
                startActivity( intent2 );
                this.finish();
                break;
            case R.id.buttonActualizarDatos:
                Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
                startActivity(iC);
                RequestPerfil requestPerfil = new RequestPerfil(nombre.getText().toString(),correo.getText().toString(), telefono.getText().toString());
                Call<ResponseLogIn> call = authABIService.actualizarPerfil( requestPerfil );
                call.enqueue(new Callback<ResponseLogIn>() {
                    @Override
                    public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                        PopUpCargando.fa.finish();
                        if ( response.isSuccessful()){

                            String mensaje = "¡Datos actualizados exitosamente!";
                            Bundle parametros = new Bundle();
                            int caracteres_totales = mensaje.length();
                            caracteres_totales = caracteres_totales/21;
                            float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                            parametros.putFloat("Espacio", espacio_total);
                            parametros.putString("Mensaje", mensaje);
                            Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                            i.putExtras(parametros);
                            startActivity(i);
                        }else{
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
                    public void onFailure(Call<ResponseLogIn> call, Throwable t) {
                        PopUpCargando.fa.finish();
                        String mensaje = "Error en la conexión, intentalo nuevamente";
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
            case R.id.buttonCambiarFoto:
                checkPermissions();
                break;
                default:
                break;
        }

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