package com.example.snl.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.snl.IlanDetayFragment;
import com.example.snl.Model.FavoriListeSliderModel;
import com.example.snl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriSliderAdapter extends PagerAdapter {

    List<FavoriListeSliderModel> list;
    Context context;
    LayoutInflater layoutInflater;
    FragmentActivity activity;

    public FavoriSliderAdapter(List<FavoriListeSliderModel> list, Context context, FragmentActivity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //LayoutInflater xml düzenlerinizden birinden yeni View(veya Layout) bir nesne oluşturmak için kullanılır
        View view = layoutInflater.inflate(R.layout.ilandetayslider_layout,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.sliderIlanDetayResim);

        Picasso.with(context).load("http://192.168.1.2:80/snldb_files/" + list.get(position).getImagepath()).resize(1050,600).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getAdvertisementId()!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ilanid", list.get(position).getAdvertisementId().toString()); //Burada ilanid mizi İlan Detay Fragmentına Gönderdik Ve ilgili ilan Bilgilerini Görüntüledik.

                    FragmentManager fragmentManager = activity.getSupportFragmentManager(); //önemli adapter sınıflaroıonda getSupportManagerı çağırmak için kullanılır.
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    IlanDetayFragment ilanDetayFragment = new IlanDetayFragment();
                    ilanDetayFragment.setArguments(bundle); //İlan Id mizi İlanDetay Sayfasına Gönderdik.
                    fragmentTransaction.replace(R.id.container_fragment, ilanDetayFragment);
                    fragmentTransaction.addToBackStack(null); //Navigasyon Butonlarından Geri Dönmemizi Sağlar.
                    fragmentTransaction.commit();  //Geçişin Hemen Yapılmasını Sağlar.
                }
            }
        });

        container.addView(view); //viewı containera ekledilk kalıp bu.
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //Bunu yazmamaızın amacı pageAdapter kullandığımız için view mızı destroy etmesin diye içindeki super metodunu silerek slideradepter sayfamızda Override ettik.
    }
}
