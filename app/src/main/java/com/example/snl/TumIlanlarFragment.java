package com.example.snl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snl.Adapters.TumIlanlarAdapter;
import com.example.snl.Model.TumIlanlarModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TumIlanlarFragment extends Fragment {

    ListView listView;
    ApiInterface apiInterface;
    List<TumIlanlarModel> tumIlanlarModels;
    TumIlanlarAdapter tumIlanlarAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tumilanlar, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        listView = view.findViewById(R.id.tumIlanlarListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ilanid",tumIlanlarModels.get(position).getAdvertisementId());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                IlanDetayFragment ilanDetayFragment = new IlanDetayFragment();
                ilanDetayFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_fragment,ilanDetayFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tumIlanlarGoruntule();
        return view;
    }

    public void tumIlanlarGoruntule() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Tüm İlanlar");
        progressDialog.setMessage("Tüm İlanlar Listeleniyor, Lütfen Bekleyin...");
        progressDialog.setCancelable(false);
        progressDialog.show();

       Call<List<TumIlanlarModel>> request = apiInterface.tumIlanlar();
       request.enqueue(new Callback<List<TumIlanlarModel>>() {
           @Override
           public void onResponse(Call<List<TumIlanlarModel>> call, Response<List<TumIlanlarModel>> response) {
               if (response.isSuccessful()){
                   if (response.body().get(0).isTf()){
                        tumIlanlarModels = response.body();
                        tumIlanlarAdapter = new TumIlanlarAdapter(tumIlanlarModels,getActivity().getApplicationContext());
                        listView.setAdapter(tumIlanlarAdapter);
                        Toast.makeText(getActivity().getApplicationContext(), response.body().get(0).getSayi() + " tane ilan listelenmiştir.", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                   }
               }
           }

           @Override
           public void onFailure(Call<List<TumIlanlarModel>> call, Throwable t) {

           }
       });
    }
}
