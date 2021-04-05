package com.example.snl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snl.Adapters.MessageAdapter;
import com.example.snl.Model.MessageModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityChat extends AppCompatActivity {

    String userId,otherId,userTable,otherTable,key;
    SharedPreferences sharedPreferences;
    DatabaseReference reference;

    Button btnGonder;
    EditText txtMesaj;

    List<MessageModel> list;
    MessageAdapter messageAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tanimla();
        action();
        load();
    }

    public void tanimla(){
        txtMesaj = (EditText)findViewById(R.id.txtMesaj);
        btnGonder = (Button)findViewById(R.id.btnGonder);

        otherId = OtherId.getOtherId();
        sharedPreferences = getApplicationContext().getSharedPreferences("login",0);
        userId = sharedPreferences.getString("memberId",null);
        reference = FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<>();
        messageAdapter = new MessageAdapter(list,getApplicationContext(),userId);
        recyclerView = (RecyclerView)findViewById(R.id.mesajListe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageAdapter);

    }

    public void action(){
        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMesaj.getText().toString().equals("")){
                    Toast.makeText(ActivityChat.this,"Boş Mesaj Atamazsınız",Toast.LENGTH_LONG).show();
                }
                else{
                    sendMessage(txtMesaj.getText().toString(),userId,otherId);
                }
            }
        });
    }

    public void sendMessage(String messageBody, String usrId, String othId){
        userTable = "messages/"+userId+"/"+otherId;
        otherTable = "messages/"+otherId+"/"+userId;
        key = reference.child("messages").child(userTable).child(otherTable).push().getKey();

        Log.i("Keyim",key);
        Map map = send(messageBody,usrId,othId);
        Map map2 = new HashMap();

        map2.put(userTable+"/"+key,map);
        map2.put(otherTable+"/"+key,map);

        txtMesaj.setText("");
        reference.updateChildren(map2,new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference ){

            }
        });
    }

    public Map send(String messageBody, String userId, String otherId){
        Map message = new HashMap();
        message.put("mesaj",messageBody);
        message.put("from",userId);
        message.put("to",otherId);
        return message;
    }

    public void load(){

        reference.child("messages").child(userId).child(otherId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageModel messageModel = snapshot.getValue(MessageModel.class);
                list.add(messageModel);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageModel messageModel = snapshot.getValue(MessageModel.class);
                list.add(messageModel);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                MessageModel messageModel = snapshot.getValue(MessageModel.class);
                list.add(messageModel);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
//onChildAdded() metodu; dinlediğimiz kısma yeni bir veri eklendiğinde,
//onChildChanged() metodu; dinlediğimiz kısımda bir değişiklik yapıldığında,
//onChildMoved() metodu; dinlediğimiz kısımda bir taşıma işlemi yapıldığında,
//onChildRemoved() metodu; dinlediğimiz kısımda bir silme işlemi yapıldığında,
//onCancelled() metodu da; dinlediğimiz kısımda yapmaya çalıştığımız herhangi bir işlemin çeşitli sebeplerden dolayı (Örneğin Security & Rules ile belirtilen güvenlik kuralları nedeniyle) sunucu tarafından iptal edildiği zaman harekete geçmektedir.