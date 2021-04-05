package com.example.snl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snl.Model.LoginModel;
import com.example.snl.Model.MemberModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.editTextPassword) EditText editTextPassword;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sharedPreferences = getApplicationContext().getSharedPreferences("login",0);
        if (sharedPreferences.getString("memberEmail",null)!=null&&sharedPreferences.getString("memberId",null)!=null){
            Intent intent = new Intent(LoginActivity.this,HomePage.class);
            startActivity(intent);
            finish();
        }

    }

     public void loginUser(View v){
         Call<LoginModel> memberModelCall = apiInterface.loginuser(editTextEmail.getText().toString(),
                 editTextPassword.getText().toString());

         memberModelCall.enqueue(new Callback<LoginModel>() {
             @Override
             public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                 if (response.isSuccessful()){

                     if (response.body().getEmail()!=null&&response.body().getId()!=null){
                         String memberEmail=response.body().getEmail().toString();
                         String memberId=response.body().getId().toString();
                         sharedPreferences = getApplicationContext().getSharedPreferences("login",0);
                         SharedPreferences.Editor editor = sharedPreferences.edit();
                         editor.putString("memberEmail",memberEmail);
                         editor.putString("memberId",memberId);
                         editor.commit();
                         Intent intent = new Intent(LoginActivity.this,HomePage.class);
                         startActivity(intent);
                         finish();
                         Toast.makeText(LoginActivity.this,"Giriş Başarılı",Toast.LENGTH_LONG).show();
                     }
                     else{
                         Toast.makeText(LoginActivity.this,"Kullanıcı Bulunamadı",Toast.LENGTH_LONG).show();
                     }
                 }
             }

             @Override
             public void onFailure(Call<LoginModel> call, Throwable t) {
                 Toast.makeText(LoginActivity.this,"Hata : Bağlanamadı",Toast.LENGTH_LONG).show();
                 Log.i("My Tag ","Hata : "+t);
             }
         });
     }

     public void registerUser(View v){
         Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
         startActivity(intent);

     }

     public void sifremiUnuttum(View v){
        Intent intent = new Intent(LoginActivity.this,SifremiUnuttumActivity.class);
        startActivity(intent);
        finish();
     }
}