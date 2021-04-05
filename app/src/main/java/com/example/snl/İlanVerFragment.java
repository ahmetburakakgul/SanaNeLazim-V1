package com.example.snl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snl.Model.AdvertisementModel;
import com.example.snl.Model.AdvertisementResultModel;
import com.example.snl.Retrofit.ApiClient;
import com.example.snl.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class İlanVerFragment extends Fragment {


    SharedPreferences sharedPreferences;

    ApiInterface apiInterface;

    EditText editTextİlanBaslik, editTextİlanAciklama, editTextİlanFiyat;
    String[] category = {"Kategori Seçin", "Elektronik", "Giyim", "Ev/Bahçe", "Kişisel Bakım", "Araç Parçaları", "Diğer"};

    String[] state = {"Ürün Durumu Seçin", "Sıfır Ürün", "İkinci El Ürün"};

    Spinner spinnerCategory, spinnerState;
    ArrayAdapter arrayAdapterCategory, arrayAdapterState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ilanver, container, false);


        editTextİlanBaslik = view.findViewById(R.id.editTextİlanBaslik);
        editTextİlanAciklama = view.findViewById(R.id.editTextİlanAciklama);
        editTextİlanFiyat = view.findViewById(R.id.editTextİlanFiyat);

        spinnerCategory = view.findViewById(R.id.spinnerKategori);
        spinnerState = view.findViewById(R.id.spinnerUrunDurumu);

        arrayAdapterCategory = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, category);
        spinnerCategory.setAdapter(arrayAdapterCategory);

        arrayAdapterState = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, state);
        spinnerState.setAdapter(arrayAdapterState);

       // editTextİlanBaslik.setText(AdvertisementModel.getTitle());
       // editTextİlanAciklama.setText(AdvertisementModel.getDescription());
       // editTextİlanFiyat.setText(AdvertisementModel.getPrice());

        sharedPreferences = getActivity().getSharedPreferences("login", 0);
        AdvertisementModel.setMemberId(sharedPreferences.getString("memberId", null));

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Button buttonİleri = view.findViewById(R.id.btnİleri);
        buttonİleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextİlanBaslik.getText().toString().equals("") || editTextİlanAciklama.getText().toString().equals("") || editTextİlanFiyat.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Lütfen İlan İle İlgili Boş Olan Yerleri Doldurunuz!", Toast.LENGTH_LONG).show();
                } else if (spinnerCategory.getSelectedItem().equals("Kategori Seçin") || spinnerState.getSelectedItem().equals("Ürün Durumu Seçin")) {
                    Toast.makeText(getActivity(), "Lütfen Kategori Ve Ürün Durumu Seçiniz", Toast.LENGTH_LONG).show();
                } else {
                    AdvertisementModel.setTitle(editTextİlanBaslik.getText().toString());
                    AdvertisementModel.setDescription(editTextİlanAciklama.getText().toString());
                    AdvertisementModel.setPrice(editTextİlanFiyat.getText().toString());
                    AdvertisementModel.setCategory(spinnerCategory.getSelectedItem().toString());
                    AdvertisementModel.setState((spinnerState.getSelectedItem().toString()));

                    Call<AdvertisementResultModel> advertisementModelCall = apiInterface.advertisement(AdvertisementModel.getMemberId(), AdvertisementModel.getTitle(), AdvertisementModel.getDescription(), AdvertisementModel.getPrice(), AdvertisementModel.getCategory(), AdvertisementModel.getState());
                    advertisementModelCall.enqueue(new Callback<AdvertisementResultModel>() {
                        @Override
                        public void onResponse(Call<AdvertisementResultModel> call, Response<AdvertisementResultModel> response) {
                            if (response.body().isTf()) {
                                Toast.makeText(getActivity(), "İlanınız Oluşturuldu Lütfen İlan Resmini Yayınlayın", Toast.LENGTH_SHORT).show();
                                String memberId = String.valueOf(response.body().getMemberId());
                                String advertisementId = response.body().getAdvertisementId();

                                Bundle bundle = new Bundle();
                                bundle.putString("uyeid",memberId);
                                bundle.putString("ilanid",advertisementId);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                İlanResimFragment i̇lanResimFragment = new İlanResimFragment();
                                i̇lanResimFragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.container_fragment,i̇lanResimFragment);
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<AdvertisementResultModel> call, Throwable t) {

                        }
                    });


                }
            }
        });
        return view;
    }
}
