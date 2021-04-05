package com.example.snl;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.snl.Adapters.FavoriIlanlarimListeAdapter;
import com.example.snl.Adapters.IlanlarimAdapter;
import com.example.snl.Model.FavoriListelemeModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriIlanlarimFragment extends Fragment {


    ListView listView;
    ApiInterface apiInterface;
    List<FavoriListelemeModel> list;
    FavoriIlanlarimListeAdapter favoriIlanlarimListeAdapter;

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
        View view = inflater.inflate(R.layout.fragment_favoriilanlar, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sharedPreferences = getActivity().getSharedPreferences("login", 0);
        memberId = sharedPreferences.getString("memberId", null);
        favoriIlanlarGoruntule();

        listView = view.findViewById(R.id.favoriIlanlarimListView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ilanid",list.get(position).getAdvertisementId());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                IlanDetayFragment ilanDetayFragment = new IlanDetayFragment();
                ilanDetayFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_fragment,ilanDetayFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public void favoriIlanlarGoruntule() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Favori İlanlar");
        progressDialog.setMessage("Favori İlanlar Listeleniyor, Lütfen Bekleyin...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<FavoriListelemeModel>> request = apiInterface.setfavoriListe(memberId);
        request.enqueue(new Callback<List<FavoriListelemeModel>>() { //enqueue metodu ile yeni bir callback oluşturuyoruz.
            @Override
            public void onResponse(Call<List<FavoriListelemeModel>> call, Response<List<FavoriListelemeModel>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    if (response.body().get(0).isTf()) {
                        favoriIlanlarimListeAdapter = new FavoriIlanlarimListeAdapter(list, getActivity().getApplicationContext());
                        listView.setAdapter(favoriIlanlarimListeAdapter);
                        Toast.makeText(getActivity().getApplicationContext(), response.body().get(0).getSayi() + " İlanınız bulunmaktadır.", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), " İlanınız Bulunmamaktadır.", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FavoriListelemeModel>> call, Throwable t) {
                Log.i("My Tag","Hata : "+t);
            }
        });
    }
}
