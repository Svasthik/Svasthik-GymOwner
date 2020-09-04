package com.example.svasthik_gymowner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class HomeFragment extends Fragment implements BookingListAdapter.OnItemClickListener{

    private DatabaseReference gymsRef;
    private ArrayList<BookingListModelClass> itemlist;
    private BookingListAdapter mModelAdapter;
    private LinearLayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=  inflater.inflate(R.layout.fragment_home, container, false);
        gymsRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        itemlist=new ArrayList<>();

        gymsRef.child("bookingRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot snap:snapshot.getChildren()) {
                    //Log.i("value", String.valueOf(snapshot.getValue()));
                    BookingDetailsCLass info = snap.getValue(BookingDetailsCLass.class);
                    String name = info.getCustomerName();
                    String time=info.getSlot();
                    String id = info.getCustomerId();
                    String phone=info.getPhone();
                    String otp=info.getOtp();
                    String email=info.getEmail();
                    String targetGym=info.getTargetGym();
                    String address=info.getAddress();
                    String price=info.getPrice();
                    String image=info.getImage();
                    String slotValue=info.getSlotValue();

                    itemlist.add(new BookingListModelClass(name,id,time,"xxx",phone,otp,email,targetGym,address,price,image,slotValue));

//Recycler View Code---
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bookingListRecycler);
                    mModelAdapter = new BookingListAdapter(itemlist);
//Layout Manager Code----
                    recyclerView.setHasFixedSize(true);
                    mLayoutManager= new LinearLayoutManager(getContext());

                    recyclerView.setAdapter(mModelAdapter);
                    recyclerView.setLayoutManager(mLayoutManager);
                    mModelAdapter.setOnItemClickListener(HomeFragment.this);
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
        BookingListModelClass clickedItem=itemlist.get(position);


        Log.i("click",clickedItem.getName());

        Intent intent=new Intent(getContext(),BookingDetailsActivity.class);
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
