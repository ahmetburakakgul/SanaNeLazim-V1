package com.example.snl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.snl.Model.IlanDetaySliderModel;
import com.example.snl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    //İlan Detay Sayfamızda Kullanmak İçin PagerAdapter extend Ediyoruz Yani İlan Resimleri İçin.

    List<IlanDetaySliderModel> ilanDetaySliderModelList;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(List<IlanDetaySliderModel> ilanDetaySliderModelList, Context context) {
        this.ilanDetaySliderModelList = ilanDetaySliderModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ilanDetaySliderModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ilandetayslider_layout,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.sliderIlanDetayResim); //İlan Resimlerimizi Üst Üste Basacağımız İmageviewu Tanımladık.

        Picasso.with(context).load("http://192.168.1.2:80/snldb_files/" + ilanDetaySliderModelList.get(position).getImage()).resize(1050,600).into(imageView); //Veri Tabanımızdaki Kayıtlı Resimleri İmageVİewa Basıyoruz.

        container.addView(view); //viewı containera ekledilk kalıp bu.
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //Bunu yazmamaızın amacı pageAdapter kullandığımız için view mızı destroy etmesin diye içindeki super metodunu silerek slideradepter sayfamızda Override ettik.
    }
}
