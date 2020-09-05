package com.example.svasthik_gymowner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

public class BookingDetailsActivity extends AppCompatActivity {
    TextView name;
    TextView phone;
    TextView email;
    TextView time;
    TextView dayTextView;
    TextView dateTextView;
    EditText otp;
    Button startSession;


    DatabaseReference userRef;

    DatabaseReference gymRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_booking_details);

        gymRef=FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name=(TextView)findViewById(R.id.profile_name);
        phone=(TextView)findViewById(R.id.profile_phone);
        email=(TextView)findViewById(R.id.profile_email);
        time=(TextView)findViewById(R.id.profile_time);
        dayTextView=(TextView)findViewById(R.id.profile_day);
        dateTextView=(TextView)findViewById(R.id.profile_date);
        otp=(EditText) findViewById(R.id.otp);
        startSession=(Button)findViewById(R.id.start_session);
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


        Date date = new Date();
        date.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("EEE");
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        String day=ft.format(date);
        String date_String=dt.format(date);
        dayTextView.setText(day);
        dateTextView.setText(date_String);

        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String otp_entered_string =otp.getText().toString();
                Log.i("otp",otp_entered_string);
                if(TextUtils.isEmpty(otp_entered_string)){
                    Toast.makeText(BookingDetailsActivity.this, "Enter OTP to continue", Toast.LENGTH_SHORT).show();
                }
                else{

                     if(TextUtils.equals(otp_string,otp_entered_string)){

                        final HashMap<String , Object> map = new HashMap<>();
                        map.put("customerName" , name_string);
                        map.put("customerId" , user_id);
                        map.put("targetGym", targetGym_string);
                        map.put("targetGymId", gymId);
                        map.put("address", address_string);
                        map.put("phone", phone_string);
                        map.put("price", price_string);
                        map.put("slot", time_string);
                         map.put("slotValue", slotValue_string);
                        map.put("email", email_string);
                         map.put("image", image_string);
                        gymRef.child("activeSessions").child(user_id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    userRef.child("activeSession").child(gymId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            deleteBooking(user_id,slotValue_string);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(BookingDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookingDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                     else{
                         Toast.makeText(BookingDetailsActivity.this, "wrong otp", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

    }

    private void deleteBooking(final String user, final String time) {

        gymRef.child("bookingRequest").child(user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                userRef.child("booking").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //transaction
                        gymRef.child("Slots").child(time).child("Count").runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                                long p=0;
                                if (currentData.getValue()== null) {
                                    return Transaction.success(currentData);
                                }
                                else {

                                    p=Long.valueOf(currentData.getValue().toString())-1;
                                    currentData.setValue(p);
                                    return Transaction.success(currentData);

                                }

                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.i("TAG", "Updating count transaction is completed.");
                                startActivity(new Intent(BookingDetailsActivity.this,MainActivity.class));
                                finish();



                                //startActivity(intent);



                            }
                        });


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
                Toast.makeText(BookingDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}