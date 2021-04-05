package com.example.snl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snl.Model.IlanBilgiUpdate;
import com.example.snl.Model.IlanDetayModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanBilgiUpdateActivity extends AppCompatActivity {

    private EditText txtIlanDuzenlemeTitle,txtIlanDuzenlemeDescription,txtIlanDuzenlemePrice;
    private Button btnIlanUpdate;
    ApiInterface apiInterface;
    private String advertisementId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_bilgi_update);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        txtIlanDuzenlemeTitle = (EditText)findViewById(R.id.txtIlanDuzenlemeTitle);
        txtIlanDuzenlemeDescription = (EditText)findViewById(R.id.txtIlanDuzenlemeDescription);
        txtIlanDuzenlemePrice = (EditText)findViewById(R.id.txtIlanDuzenlemePrice);

        btnIlanUpdate = (Button)findViewById(R.id.btnIlanUpdate);

        Bundle intent = getIntent().getExtras();
        advertisementId = intent.getString("advertisementId"); //Fragmenttan buraya keyimizi aldık.
        getIlanBilgi(advertisementId);

        btnIlanUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtIlanDuzenlemeTitle.getText().toString().equals("")||txtIlanDuzenlemeDescription.getText().toString().equals("")||txtIlanDuzenlemePrice.getText().toString().equals("")){
                    Toast.makeText(IlanBilgiUpdateActivity.this,"Eksik Veya Yanlış Bilgi Girmediğinizden Emin Olun",Toast.LENGTH_LONG).show();
                }else{
                    getIlanUpdate(advertisementId,txtIlanDuzenlemeTitle.getText().toString(),txtIlanDuzenlemeDescription.getText().toString(),txtIlanDuzenlemePrice.getText().toString());
                    Intent i = new Intent(IlanBilgiUpdateActivity.this,IlanResimDuzenlemeActivity.class);
                    i.putExtra("advertisementId",advertisementId);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    public void getIlanBilgi(String advertisementId){
        Call<IlanDetayModel> request=apiInterface.ilanDetay(advertisementId);
        request.enqueue(new Callback<IlanDetayModel>() {
            @Override
            public void onResponse(Call<IlanDetayModel> call, Response<IlanDetayModel> response) {
                if (response.isSuccessful()){
                    txtIlanDuzenlemeTitle.setText(response.body().getTitle());
                    txtIlanDuzenlemeDescription.setText(response.body().getDescription());
                    txtIlanDuzenlemePrice.setText(response.body().getPrice());
                }
            }

            @Override
            public void onFailure(Call<IlanDetayModel> call, Throwable t) {

            }
        });
    }

    public void getIlanUpdate(String advertisementId,String title, String description, String price){
        Call<IlanBilgiUpdate> request = apiInterface.ilanUpdate(advertisementId, title, description, price);
        request.enqueue(new Callback<IlanBilgiUpdate>() {
            @Override
            public void onResponse(Call<IlanBilgiUpdate> call, Response<IlanBilgiUpdate> response) {
                if (response.body().isTf()){
                    Toast.makeText(IlanBilgiUpdateActivity.this,"Bilgileriniz Başarıyla Güncellendi",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(IlanBilgiUpdateActivity.this,"Sunucuya Bağlanamadı",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IlanBilgiUpdate> call, Throwable t) {

            }
        });
    }
}