package com.example.snl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.snl.Model.AddImageModel;
import com.example.snl.Model.IlanResimUpdateModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanResimDuzenlemeActivity extends AppCompatActivity {


    String image;
    private String advertisementId,memberId;

    SharedPreferences sharedPreferences;

    Button btnResimsec, btnResimEkle, buttonAnasayfa;
    ImageView imageView;

    Bitmap bitmap;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_resim_duzenleme);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Bundle intent = getIntent().getExtras();
        advertisementId = intent.getString("advertisementId"); //Fragmenttan buraya keyimizi aldık.

        sharedPreferences = getSharedPreferences("login", 0);
        memberId = sharedPreferences.getString("memberId", null);

        imageView = (ImageView)findViewById(R.id.imgViewResimDuzenle);

        btnResimsec = (Button)findViewById(R.id.btnResimDuzenle);
        btnResimsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();
            }
        });

        btnResimEkle = (Button)findViewById(R.id.btnResimDuzenleEkle);
        btnResimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()==null){
                    Toast.makeText(getApplicationContext(),"Lütfen Resimlerinizi Tekrar Seçiniz.",Toast.LENGTH_LONG).show();
                }else{
                    uploadImage();
                }
            }
        });

        buttonAnasayfa = (Button)findViewById(R.id.btnGeriDonDuzenle);
        buttonAnasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()==null){
                    Toast.makeText(getApplicationContext(),"Lütfen Önce Resim Seçiniz.",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(IlanResimDuzenlemeActivity.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    //Galeriyi Açar.
    public void resimGoster() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);
    }

    public void uploadImage() {
        image = imageToString();
        Call<IlanResimUpdateModel> request = apiInterface.ilanResimUpdate(memberId,advertisementId, image);
        request.enqueue(new Callback<IlanResimUpdateModel>() {
            @Override
            public void onResponse(Call<IlanResimUpdateModel> call, Response<IlanResimUpdateModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getApplicationContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IlanResimUpdateModel> call, Throwable t) {
                Log.i("test 1 ","hata : "+t);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imageToString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imageToString;
    }
}