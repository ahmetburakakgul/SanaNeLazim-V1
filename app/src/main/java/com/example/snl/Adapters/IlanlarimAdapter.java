package com.example.snl.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snl.Model.IlanlarimModel;
import com.example.snl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IlanlarimAdapter extends BaseAdapter {

    List<IlanlarimModel> ilanlarimModelList;
    Context context;
    Activity activity;
    String memberId,advertisementId;

    public IlanlarimAdapter(List<IlanlarimModel> ilanlarimModelList, Context context, Activity activity) {
        this.ilanlarimModelList = ilanlarimModelList;   //Constructorda Değişkenlerimizi Tanımladık.
        this.context = context;
        this.activity = activity; //Fragmenttan Gelen Activiteti Burada Tanımladık.
    }

    @Override
    public int getCount() {
        return ilanlarimModelList.size();
    } //Listemizin Bouyutu.

    @Override
    public Object getItem(int position) {
        return ilanlarimModelList.get(position);
    } //Position ile belirttiğimiz Listenin Satırlarındaki Veriyi Döndürdük.

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.ilanlarim_layout,parent,false);
        ImageView imgIlanResim;
        TextView lblIlanBaslik,lblIlanAciklama,lblIlanFiyat;
        imgIlanResim = (ImageView)convertView.findViewById(R.id.imgIlanlarimIlanResim);
        lblIlanBaslik = (TextView)convertView.findViewById(R.id.lblIlanBaslik);
        lblIlanAciklama = (TextView)convertView.findViewById(R.id.lblIlanAciklama);
        lblIlanFiyat = (TextView)convertView.findViewById(R.id.lblIlanFiyat);

        lblIlanBaslik.setText(ilanlarimModelList.get(position).getTitle());
        lblIlanAciklama.setText(ilanlarimModelList.get(position).getDescription());
        lblIlanFiyat.setText(ilanlarimModelList.get(position).getPrice());

        advertisementId = ilanlarimModelList.get(position).getAdvertisementId(); //Listemizin İlgili Satırından Dönen İlan İd ve Üye İdmizi aldık.
        memberId = ilanlarimModelList.get(position).getMemberId();




        Picasso.with(context).load("http://192.168.1.2:80/snldb_files/" + ilanlarimModelList.get(position).getImage()).resize(100,100).into(imgIlanResim);
        //Log.wtf("Testing valid URL", "|"+ "http://192.168.1.2:80/snldb_files/"+ilanlarimModelList.get(position).getImage()+"|");

        return convertView;
    }
}
