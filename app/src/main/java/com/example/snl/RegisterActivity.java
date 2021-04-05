package com.example.snl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snl.Model.MemberModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editTextEmail)EditText editTextEmail;
    @BindView(R.id.editTextUsername)EditText editTextUsername;
    @BindView(R.id.editTextPhone)EditText editTextPhone;
    @BindView(R.id.editTextPassword)EditText editTextPassword;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public void registerUser(View v){

        Call<MemberModel> callRegister = apiInterface.registerUser(editTextEmail.getText().toString(),
                editTextUsername.getText().toString(),
                editTextPhone.getText().toString(),
                editTextPassword.getText().toString());

        callRegister.enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                if (response.isSuccessful() && response.body()!=null){
                    MemberModel memberModel = response.body();

                    if (memberModel.isSuccess()){
                        Toast.makeText(RegisterActivity.this,"Kayıt Başarı İle Gerçekleşti",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Kullanıcı Kayıt Edilemedi",Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
                Log.w("MyTag", "requestFailed", t);
                Toast.makeText(RegisterActivity.this,"Hata : Bağlanamadı",Toast.LENGTH_LONG).show();
            }
        });
    }
}