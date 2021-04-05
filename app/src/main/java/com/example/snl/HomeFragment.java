package com.example.snl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.snl.Adapters.FavoriSliderAdapter;
import com.example.snl.Model.FavoriListeSliderModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    ViewPager favoriIlanSlider;
    CircleIndicator favoriIlanCircle;

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    String memberId;

    FavoriSliderAdapter favoriSliderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("login",0);
        memberId = sharedPreferences.getString("memberId",null);

        favoriIlanSlider = (ViewPager)view.findViewById(R.id.favoriIlanSlider);
        favoriIlanCircle = (CircleIndicator)view.findViewById(R.id.favoriIlanCircle);

        getSlider();

        return view;
    }

    public void getSlider(){
        Call<List<FavoriListeSliderModel>> request = apiInterface.setListeSlider(memberId);
        request.enqueue(new Callback<List<FavoriListeSliderModel>>() {
            @Override
            public void onResponse(Call<List<FavoriListeSliderModel>> call, Response<List<FavoriListeSliderModel>> response) {
                if (response.body().get(0).isTf()){
                    if (response.body().size()>0){
                        favoriSliderAdapter = new FavoriSliderAdapter(response.body(),getActivity().getApplicationContext(),getActivity());
                        favoriIlanSlider.setAdapter(favoriSliderAdapter);
                        favoriIlanCircle.setViewPager(favoriIlanSlider);
                        favoriIlanCircle.bringToFront();
                    }
                }
                else{
                    favoriSliderAdapter = new FavoriSliderAdapter(response.body(),getActivity().getApplicationContext(),getActivity());
                    favoriIlanSlider.setAdapter(favoriSliderAdapter);
                    favoriIlanCircle.setViewPager(favoriIlanSlider);
                    favoriIlanCircle.bringToFront();
                }
            }

            @Override
            public void onFailure(Call<List<FavoriListeSliderModel>> call, Throwable t) {
                Log.i("my tag","hata : "+t);
            }
        });

    }
}
