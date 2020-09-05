package com.example.svasthik_gymowner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private DatabaseReference historyRef;
    private DatabaseReference historyDatabaseRef;

    private ArrayList<HistoryModelClass> itemlist;
    private HistoryAdapter mModelAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=  inflater.inflate(R.layout.fragment_history, container, false);


        historyRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bookingHistory");

        itemlist=new ArrayList<>();

        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot history : dataSnapshot.getChildren()) {
                        fetchHistory(history.getKey(),view);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void fetchHistory(String key, final View view) {
        historyDatabaseRef= FirebaseDatabase.getInstance().getReference().child("history").child(key);
        historyDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String historyID=dataSnapshot.getKey();

                    Log.i("xxx",String.valueOf(dataSnapshot.getValue()));
                    BookingDetailsCLass info=dataSnapshot.getValue(BookingDetailsCLass.class);
                    String customerId=info.getCustomerId();
                    String customerName=info.getCustomerName();
                    String targetGym=info.getTargetGym();
                    String targetGymId=info.getTargetGymId();
                    String address=info.getAddress();
                    String email=info.getEmail();
                    String price=info.getPrice();
                    String slot=info.getSlot();
                    String image=info.getImage();
                    String date=info.getDate();

                    itemlist.add(new HistoryModelClass( address,customerId,customerName,email,price,slot,targetGym,targetGymId,image,date));
//Recycler View Code---
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_Recycler);
                    mModelAdapter = new HistoryAdapter(itemlist);
//Layout Manager Code----
                    mLayoutManager= new LinearLayoutManager(getContext());
                    recyclerView.setAdapter(mModelAdapter);
                    recyclerView.setLayoutManager(mLayoutManager);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}