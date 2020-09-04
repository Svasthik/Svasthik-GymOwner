package com.example.svasthik_gymowner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveSessionFragment extends Fragment implements ActiveSessionListAdapter.OnItemClickListener {


    private DatabaseReference gymsRef;
    private ArrayList<ActiveSessionListModelClass> itemlist;
    private ActiveSessionListAdapter mModelAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=  inflater.inflate(R.layout.fragment_active_session, container, false);
        gymsRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        itemlist=new ArrayList<>();


        gymsRef.child("activeSessions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {

                        BookingDetailsCLass info = snap.getValue(BookingDetailsCLass.class);
                        String name = info.getCustomerName();
                        String time = info.getSlot();
                        String id = info.getCustomerId();
                        String phone = info.getPhone();
                        String image=info.getImage();
                        String email = info.getEmail();
                        String targetGym = info.getTargetGym();
                        String address = info.getAddress();
                        String price = info.getPrice();
                        String slotValue = info.getSlotValue();
                        Log.i("value", price);


                        itemlist.add(new ActiveSessionListModelClass(name, id, time, "xxx", phone, "", email, targetGym, address, price,image,slotValue));

//Recycler View Code---
                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.active_session_Recycler);
                        mModelAdapter = new ActiveSessionListAdapter(itemlist);
//Layout Manager Code----
                        recyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getContext());

                        recyclerView.setAdapter(mModelAdapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                        mModelAdapter.setOnItemClickListener(ActiveSessionFragment.this);

                    }
                }
                else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;

    }
    @Override
    public void onItemClick(int position) {
        ActiveSessionListModelClass clickedItem=itemlist.get(position);




        Intent intent=new Intent(getContext(),SessionDetailsActivity.class);
        intent.putExtra("name",clickedItem.getName());
        intent.putExtra("phone",clickedItem.getPhone());
        intent.putExtra("email",clickedItem.getEmail());
        intent.putExtra("otp",clickedItem.getOtp());
        intent.putExtra("time",clickedItem.getTime());
        intent.putExtra("id",clickedItem.getId());
        intent.putExtra("price",clickedItem.getPrice());
        intent.putExtra("targetGym",clickedItem.getTargetGym());
        intent.putExtra("address",clickedItem.getAddress());
        intent.putExtra("image",clickedItem.getImage());
        intent.putExtra("slotValue",clickedItem.getSlotValue());


        startActivity(intent);
    }
}
