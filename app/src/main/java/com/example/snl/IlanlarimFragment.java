package com.example.snl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snl.Adapters.IlanlarimAdapter;
import com.example.snl.Model.AdvertisementModel;
import com.example.snl.Model.IlanBilgiUpdate;
import com.example.snl.Model.IlanSilModel;
import com.example.snl.Model.IlanlarimModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanlarimFragment extends Fragment {

    ListView listView;
    IlanlarimAdapter ilanlarimAdapter;
    List<IlanlarimModel> ilanlarimModels;

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    String memberId;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ilanlarim, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        sharedPreferences = getActivity().getSharedPreferences("login", 0);
        AdvertisementModel.setMemberId(sharedPreferences.getString("memberId", null));

        listView = view.findViewById(R.id.ilanlarimListView);
        ilanlarimGoruntule();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ilanlarimAlertDialog(getActivity(),ilanlarimModels.get(position).getAdvertisementId());
            }
        });


        return view;
    }

    public void ilanlarimGoruntule(){
        ilanlarimModels = new ArrayList<>();
        Call<List<IlanlarimModel>> request = apiInterface.ilanlarim(AdvertisementModel.getMemberId());
        request.enqueue(new Callback<List<IlanlarimModel>>() {
            @Override
            public void onResponse(Call<List<IlanlarimModel>> call, Response<List<IlanlarimModel>> response) {
                if (response.isSuccessful()){
                    ilanlarimModels = response.body();
                    if (response.body().get(0).isTf()) {
                        ilanlarimAdapter = new IlanlarimAdapter(ilanlarimModels, getActivity().getApplicationContext(), getActivity());
                        listView.setAdapter(ilanlarimAdapter);
                        Toast.makeText(getActivity().getApplicationContext(), response.body().get(0).getSayi() + " İlanınız bulunmaktadır.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), " İlanınız Bulunmamaktadır.", Toast.LENGTH_LONG).show();
                        İlanVerFragment i̇lanVerFragment = new İlanVerFragment();
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<IlanlarimModel>> call, Throwable t) {
                Log.i("test 1","hata : "+t);
            }
        });
    }

    public void ilanlarimAlertDialog(Activity activity, final String advertisementId){
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alertlayout, null);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        Button btnSil = (Button)view.findViewById(R.id.btnSil);
        Button btnDuzenle = (Button)view.findViewById(R.id.btnDuzenle);
        Button btnIptalEt = (Button)view.findViewById(R.id.btnIptalEt);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();

        btnIptalEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sil(advertisementId);
                alertDialog.cancel();
            }
        });

        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IlanBilgiUpdateActivity.class);
            intent.putExtra("advertisementId",advertisementId);
            startActivity(intent);
            }
        });
        alertDialog.show();
    }

    public void sil(String advertisementId){
        Call<IlanSilModel> request = apiInterface.ilansil(advertisementId);
        request.enqueue(new Callback<IlanSilModel>() {
            @Override
            public void onResponse(Call<IlanSilModel> call, Response<IlanSilModel> response) {
                if (response.body().isTf()){
                    ilanlarimGoruntule();
                }
            }

            @Override
            public void onFailure(Call<IlanSilModel> call, Throwable t) {

            }
        });
    }
}
