package com.example.snl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snl.Model.FavoriListelemeModel;
import com.example.snl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriIlanlarimListeAdapter extends BaseAdapter {

    List<FavoriListelemeModel> list; //Modelimizi List Biçiminde Tanımlıyoruz Çünkü Favorilerim Fragmenta Liste Şeklinde Gösterdiğimiz İçin.
    Context context;               //Context ise uygulama ortamı için genel bilgileri, durumları vs tutan ve uygulamaya özgü kaynaklara ve classlara erişmeyi sağlayan arayüz diyebiliriz.
                                  // Genellikle uygulamanın diğer kısımlarındaki verilere ulaşmak için kullanılır.

    public FavoriIlanlarimListeAdapter(List<FavoriListelemeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    } //Listemizin Boyutunu Döndürdük.

    @Override
    public Object getItem(int position) {
        return list.get(position);
    } //Position ile belirttiğimiz Listenin Satırlarındaki Veriyi Döndürdük.

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.favoriilanlar_layout,parent,false); //LayoutInflaterxml düzenlerinizden birinden yeni View(veya Layout) bir nesne oluşturmak için kullanılır.

        ImageView imgFavori;
        TextView lblFavoriBaslik,lblFavoriAcıklama,lblFavoriFiyat;

        imgFavori = (ImageView)convertView.findViewById(R.id.imgFavori);
        lblFavoriBaslik = (TextView)convertView.findViewById(R.id.lblFavoriBaslik);
        lblFavoriAcıklama = (TextView)convertView.findViewById(R.id.lblFavoriAcıklama);
        lblFavoriFiyat = (TextView)convertView.findViewById(R.id.lblFavoriFiyat);

        lblFavoriBaslik.setText(list.get(position).getTitle());          //
        lblFavoriAcıklama.setText(list.get(position).getDescription());  // Burada get position ile aldığımız yani liste şeklinde tuttuğumuz modelimizin satırlarındaki verileri gerekli yerlere set ettik.
        lblFavoriFiyat.setText(list.get(position).getPrice());           //





        Picasso.with(context).load("http://192.168.1.2:80/snldb_files/" + list.get(position).getImage()).resize(100,100).into(imgFavori); //Burada Da Gelen Resimlerin Urli ile set ettik.
        //Log.wtf("Testing valid URL", "|"+ "http://192.168.1.2:80/snldb_files/"+ilanlarimModelList.get(position).getImage()+"|");

        return convertView;
    }
}
