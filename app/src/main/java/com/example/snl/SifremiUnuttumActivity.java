package com.example.snl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snl.Model.SifremiUnuttumModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SifremiUnuttumActivity extends AppCompatActivity {

    private EditText editTextSifreUnuttum;
    private Button btnSifremiUnuttum;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        editTextSifreUnuttum = (EditText)findViewById(R.id.editTextSifreUnuttum);
        btnSifremiUnuttum = (Button) findViewById(R.id.btnSifremiUnuttum);


        btnSifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(SifremiUnuttumActivity.this);
                progressDialog.setMessage("Lütfen bekleyiniz..");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String email = editTextSifreUnuttum.getText().toString();
                Call<SifremiUnuttumModel> request = apiInterface.sifremiUnuttum(email);
                request.enqueue(new Callback<SifremiUnuttumModel>() {
                    @Override
                    public void onResponse(Call<SifremiUnuttumModel> call, Response<SifremiUnuttumModel> response) {
                        if (response.body().isTf()){
                            Toast.makeText(getApplicationContext(),"Şifreniz Girmiş Olduğunuz E-Mail Adresine Gönderilmiştir.",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SifremiUnuttumActivity.this,LoginActivity.class);
                            startActivity(intent);
                            progressDialog.cancel();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Boş Veya Hatalı Email Girdiniz!",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<SifremiUnuttumModel> call, Throwable t) {
                        Log.i("My Tag","Hata: "+t);
                    }
                });
            }
        });

    }
}