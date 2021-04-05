package com.example.snl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snl.Model.AddImageModel;
import com.example.snl.Model.AdvertisementModel;
import com.example.snl.Model.AdvertisementResultModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class İlanResimFragment extends Fragment {

    String image;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Button btnResimsec, btnResimEkle, buttonExit;
    ImageView imageView;

    Bitmap bitmap;

    ApiInterface apiInterface;

    AdvertisementResultModel advertisementResultModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ilanresim, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        imageView = (ImageView) view.findViewById(R.id.imgViewResim);

        btnResimsec = view.findViewById(R.id.btnResimSec);
        btnResimEkle = view.findViewById(R.id.btnResimEkle);

        btnResimsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();
            }
        });

        btnResimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()==null){
                    Toast.makeText(getActivity().getApplicationContext(),"Lütfen Önce Resim Seçiniz.",Toast.LENGTH_LONG).show();
                }else{
                    uploadImage();
                }
            }
        });

        buttonExit = view.findViewById(R.id.btnGeriDon);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()==null){
                    Toast.makeText(getActivity().getApplicationContext(),"Resim Yüklemeden Geri Gidemezsiniz.",Toast.LENGTH_LONG).show();
                }else{
                   backFirst();
                }
            }
        });
        return view;
    }

    public void backFirst() {
        İlanVerFragment i̇lanVerFragment = new İlanVerFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
        fragmentTransaction.commit();
    }

    //Galeriyi Açar.
    public void resimGoster() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);
    }

    public void uploadImage() {

       Bundle bundle = getArguments();
       String memberId = bundle.getString("uyeid");
       String advertisementId = bundle.getString("ilanid");

        image = imageToString();
        Call<AddImageModel> addImageModelCall = apiInterface.addImage(memberId, advertisementId, image);
        addImageModelCall.enqueue(new Callback<AddImageModel>() {
            @Override
            public void onResponse(Call<AddImageModel> call, Response<AddImageModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getActivity(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddImageModel> call, Throwable t) {
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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), path);
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
