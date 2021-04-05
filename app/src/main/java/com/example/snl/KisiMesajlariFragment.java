package com.example.snl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.snl.Adapters.MesajlarAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class KisiMesajlariFragment extends Fragment {

    List<String> otherIdList;
    String userId;
    SharedPreferences sharedPreferences;

    DatabaseReference reference;
    MesajlarAdapter adapter;

    ListView mesajlarıimListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesajlar, container, false);
        otherIdList = new ArrayList<>();
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("login", 0);
        userId = sharedPreferences.getString("memberId", null);
        reference = FirebaseDatabase.getInstance().getReference();
        adapter = new MesajlarAdapter(otherIdList,userId,getActivity().getApplicationContext(),getActivity());
        mesajlarıimListView = (ListView)view.findViewById(R.id.mesajlarıimListView);
        mesajlarıimListView.setAdapter(adapter);
        listele();
        return view;
    }

    public void listele() {
        reference.child("messages").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("My Tag", snapshot.getKey());
                otherIdList.add(snapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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
