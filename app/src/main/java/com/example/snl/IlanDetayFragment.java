package com.example.snl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.snl.Adapters.SliderAdapter;
import com.example.snl.Model.FavoriIslemModel;
import com.example.snl.Model.FavoriModel;
import com.example.snl.Model.IlanDetayModel;
import com.example.snl.Model.IlanDetaySliderModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.snl.Model.AdvertisementModel.setTitle;

public class IlanDetayFragment extends Fragment {

    private TextView lblIlanBaslikDetay,lblIlanDetayFiyat,lblIlanDetayKategori,lblIlanDetayDurunu,lblIlanDetayAciklama;
    private Button btnIlanDetayMesajlasma,btnIlanDetayFavoriEkle;
    private ViewPager ilanDetaySlider; //Slider İçin.
    String advertisementId;
    ApiInterface apiInterface;

    List<IlanDetaySliderModel> list;
    SliderAdapter sliderAdapter;

    CircleIndicator circleIndicator;//Sayfalama İçin

    SharedPreferences sharedPreferences;
    String memberId, otherId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ilandetay, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Bundle bundle = getArguments(); //Önceki Göndermiş Olduğumuz Fragmenttaki SetArgument Dediğimiz Verileri Get Argument İle aldık.
        advertisementId = bundle.getString("ilanid");

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("login",0);
        memberId = sharedPreferences.getString("memberId",null);


        lblIlanBaslikDetay = (TextView)view.findViewById(R.id.lblIlanBaslikDetay);
        lblIlanDetayAciklama = (TextView)view.findViewById(R.id.lblIlanDetayAciklama);
        lblIlanDetayFiyat = (TextView)view.findViewById(R.id.lblIlanDetayFiyat);
        lblIlanDetayKategori = (TextView)view.findViewById(R.id.lblIlanDetayKategori);
        lblIlanDetayDurunu = (TextView)view.findViewById(R.id.lblIlanDetayDurunu);

        ilanDetaySlider = (ViewPager)view.findViewById(R.id.ilanDetaySlider);
        circleIndicator = (CircleIndicator)view.findViewById(R.id.sliderCircle);

        btnIlanDetayFavoriEkle = (Button)view.findViewById(R.id.btnIlanDetayFavoriEkle);
        btnIlanDetayMesajlasma = (Button)view.findViewById(R.id.btnIlanDetayMesajlasma);

        getIlanDetay();
        getImage();
        getText();
        action();

        return view;
    }

    public void getIlanDetay(){
        Call<IlanDetayModel> request = apiInterface.ilanDetay(advertisementId);
        request.enqueue(new Callback<IlanDetayModel>() {
            @Override
            public void onResponse(Call<IlanDetayModel> call, Response<IlanDetayModel> response) {
                lblIlanBaslikDetay.setText(response.body().getTitle());
                lblIlanDetayAciklama.setText(response.body().getDescription());
                lblIlanDetayFiyat.setText(response.body().getPrice());
                lblIlanDetayKategori.setText(response.body().getCategory());
                lblIlanDetayDurunu.setText(response.body().getState());

                otherId = response.body().getMemberId();
            }

            @Override
            public void onFailure(Call<IlanDetayModel> call, Throwable t) {

            }
        });
    }

    public void getImage(){
        Call<List<IlanDetaySliderModel>> request = apiInterface.ilanDetaySlider(advertisementId);
        request.enqueue(new Callback<List<IlanDetaySliderModel>>() {
            @Override
            public void onResponse(Call<List<IlanDetaySliderModel>> call, Response<List<IlanDetaySliderModel>> response) {
                list = response.body();
                sliderAdapter = new SliderAdapter(list,getActivity().getApplicationContext());
                ilanDetaySlider.setAdapter(sliderAdapter);
                circleIndicator.setViewPager(ilanDetaySlider);
                circleIndicator.bringToFront();
            }

            @Override
            public void onFailure(Call<List<IlanDetaySliderModel>> call, Throwable t) {

            }
        });
    }

    public void getText(){
        Call<FavoriModel> request = apiInterface.getButtonText(memberId,advertisementId);
        request.enqueue(new Callback<FavoriModel>() {
            @Override
            public void onResponse(Call<FavoriModel> call, Response<FavoriModel> response) {
                if (response.body().isTf()){
                    btnIlanDetayFavoriEkle.setText(response.body().getText());
                }else{
                    btnIlanDetayFavoriEkle.setText(response.body().getText());
                }
            }

            @Override
            public void onFailure(Call<FavoriModel> call, Throwable t) {
                Log.d("Hata Tag", "Hata:"+t);
            }
        });
    }

    public void action(){
        btnIlanDetayFavoriEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<FavoriIslemModel> request = apiInterface.favoriIslemler(memberId,advertisementId);
                request.enqueue(new Callback<FavoriIslemModel>() {
                    @Override
                    public void onResponse(Call<FavoriIslemModel> call, Response<FavoriIslemModel> response) {
                        if (response.body().isTf()){
                            Toast.makeText(getActivity(),response.body().getText(),Toast.LENGTH_LONG).show();
                            getText();
                        }else{
                            Toast.makeText(getActivity(),response.body().getText(),Toast.LENGTH_LONG).show();
                            getText();
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoriIslemModel> call, Throwable t) {

                    }
                });
            }
        });

        btnIlanDetayMesajlasma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otherId.equals(memberId)){
                    Toast.makeText(getActivity(),"Kendi İlanınıza Mesaj Gönderemezsiniz",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getActivity(),ActivityChat.class);
                    OtherId.setOtherId(otherId);
                    startActivity(intent);
                }
            }
        });

    }


}
