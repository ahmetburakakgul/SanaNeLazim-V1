package com.example.snl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.snl.Model.AccountModel;
import com.example.snl.Model.AccountUpdateModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    EditText txtuserName,txtEmail,txtPhone,txtPassword;
    Button btnGuncelle;

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    String memberId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class); //İnterfacimizde Kullanacağımız ApiClientimızı Çağırdık.Yani Retrofiti Çağırdık.

        txtuserName = (EditText)view.findViewById(R.id.txtuserName);
        txtEmail = (EditText)view.findViewById(R.id.txtEmail);
        txtPhone = (EditText)view.findViewById(R.id.txtPhone);
        txtPassword = (EditText)view.findViewById(R.id.txtPassword);

        sharedPreferences = getActivity().getSharedPreferences("login", 0);
        memberId = sharedPreferences.getString("memberId", null);

        getUser(memberId);

        btnGuncelle = (Button)view.findViewById(R.id.btnGuncelle);
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtuserName.getText().equals("")&&txtEmail.getText().equals("")&&txtPhone.getText().equals("")&&txtPassword.getText().equals("")){
                    Toast.makeText(getActivity(),"Eksik Veya Yanlış Bilgi Girmediğinizden Emin Olun",Toast.LENGTH_LONG).show();
                }else{
                    getUpdate(memberId,txtuserName.getText().toString(),txtPassword.getText().toString(),txtEmail.getText().toString(),txtPhone.getText().toString());
                }
            }
        });
        return view;
    }

    public void getUser(String memberId){
        Call<AccountModel> request = apiInterface.account(memberId);
        request.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.isSuccessful()){
                    txtuserName.setText(response.body().getUsername());
                    txtEmail.setText(response.body().getEmail());
                    txtPhone.setText(response.body().getPhone());
                    txtPassword.setText(response.body().getPassword());
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Log.i("My Tag : ","Hata : "+t);
            }
        });
    }

    public void getUpdate(String memberId, String username, String password, String email, String phone){
        Call<AccountUpdateModel> request = apiInterface.accountUpdate(memberId,username,password,email,phone);
        request.enqueue(new Callback<AccountUpdateModel>() {
            @Override
            public void onResponse(Call<AccountUpdateModel> call, Response<AccountUpdateModel> response) {
                if (response.body().isTf()){
                    Toast.makeText(getActivity(),"Bilgileriniz Başarıyla Güncellendi",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Sunucuya Bağlanılamadı",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccountUpdateModel> call, Throwable t) {

            }
        });
    }
}
