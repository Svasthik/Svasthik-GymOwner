package com.example.svasthik_gymowner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SessionDetailsActivity extends AppCompatActivity {
    TextView name;
    TextView phone;
    TextView email;
    TextView time;
    TextView dateTextView;
    EditText otp;
    Button endSession;


    DatabaseReference userRef;
    DatabaseReference historyRef;
    DatabaseReference gymRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);
        historyRef=FirebaseDatabase.getInstance().getReference().child("history");
        gymRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //historyRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bookingHistory");
        name=(TextView)findViewById(R.id.profile_name);
        phone=(TextView)findViewById(R.id.profile_phone);
        email=(TextView)findViewById(R.id.profile_email);
        time=(TextView)findViewById(R.id.profile_time);
        dateTextView=(TextView)findViewById(R.id.profile_date);
        otp=(EditText) findViewById(R.id.otp);
        endSession=(Button)findViewById(R.id.end_session);
        final String name_string=getIntent().getStringExtra("name");
        final String email_string=getIntent().getStringExtra("email");
        final String phone_string=getIntent().getStringExtra("phone");
        final String otp_string=getIntent().getStringExtra("otp");
        final String time_string=getIntent().getStringExtra("time");
        final String price_string=getIntent().getStringExtra("price");
        final String address_string=getIntent().getStringExtra("address");
        final String targetGym_string=getIntent().getStringExtra("targetGym");
        final String user_id=getIntent().getStringExtra("id");
        final String image_string=getIntent().getStringExtra("image");
        final String slotValue_string=getIntent().getStringExtra("slotValue");

        final String gymId=FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRef=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        name.setText(name_string);
        email.setText(email_string);
        phone.setText(phone_string);
        time.setText(time_string);

        

        endSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                date.getTime();
                SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
                String currentTime=ft.format(date);
                int currentTimeInt=timeInInt(currentTime);
                int valueTimeInt=timeInInt(slotValue_string);


                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String date_string=dt.format(date);
                dateTextView.setText(date_string);

                final String requestID=historyRef.push().getKey();
                final HashMap<String , Object> map = new HashMap<>();
                map.put("customerName" , name_string);
                map.put("customerId" , user_id);
                map.put("targetGym", targetGym_string);
                map.put("targetGymId", gymId);
                map.put("address", address_string);
                map.put("phone", phone_string);
                map.put("price", price_string);
                map.put("slot", time_string);
                map.put("image", image_string);
                map.put("email", email_string);
                map.put("date", date_string);
                map.put("slotValue", slotValue_string);
                if(currentTimeInt-valueTimeInt!=0){
                    gymRef.child("bookingHistory").child(requestID).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                userRef.child("bookingHistory").child(requestID).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            historyRef.child(requestID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    deleteBooking(user_id);
                                                    Toast.makeText(SessionDetailsActivity.this, "ended", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SessionDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SessionDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SessionDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(SessionDetailsActivity.this, "Session can be ended only after 2 hours", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    public int timeInInt(String time) {
        String[] parts = time.split(":");
        String part1 = parts[0];
        String part2 = parts[1];
        String finalTime=part1+part2;
        int timeInt=Integer.parseInt(finalTime);
        return timeInt;
    }
    private void deleteBooking(final String user) {

        gymRef.child("activeSessions").child(user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                userRef.child("activeSession").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //transaction
                        startActivity(new Intent(SessionDetailsActivity.this,MainActivity.class));
                        finish();



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                //Intent intent=new Intent(getApplicationContext(),home.class);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SessionDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}