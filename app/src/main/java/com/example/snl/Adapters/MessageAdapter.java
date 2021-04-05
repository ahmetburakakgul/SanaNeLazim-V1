package com.example.snl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snl.Model.MessageModel;
import com.example.snl.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter { //Multiple (çoklu) View Holder’a sahip Custom Adapter oluşturuyoruz.

    List<MessageModel> list;
    boolean state = false;
    static final int user = 5 , other = 8;
    Context context;
    String userId;

    public MessageAdapter(List<MessageModel> list, Context context, String userId) {
        this.list = list;
        this.context = context;
        this.userId = userId;
    }

    //Layout Tanımlaması.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == user){
            view = LayoutInflater.from(context).inflate(R.layout.user,parent,false);  //Layout Tanımlaması Yapılır.
            return new ViewHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.other,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = list.get(position);
        switch (holder.getItemViewType()){
            case user:
            {
                ((ViewHolder)holder).setLe(messageModel);  //Usera Setle
            }
            case other:
            {
                ((ViewHolder)holder).setLe(messageModel); //Othera Setle
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView messageBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (state){
                messageBody = itemView.findViewById(R.id.txtUser); //TextView Tanımlaması Yapılıyor.
            }else{
                messageBody = itemView.findViewById(R.id.txtOther);
            }
        }

        void setLe(MessageModel message){
            messageBody.setText(message.getMesaj());
        } //Mesajlar Textviewa Basılıyor.
    }

    //veritabanındaki from ' a göre layout döndürmesini sağlayan değişkenler.
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(userId)){
            state = true;
            return user;
        }else{
            state = false;
            return other;
        }
    }
}
