package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import okhttp3.internal.cache.DiskLruCache;

public class ProfileActivity extends AppCompatActivity {

    private String receiverUserId="",receiverUserImage= "",receiverUserName="";
    private ImageView background_profile_view;
    private TextView name_profile;
    private Button add_freiend, decline_friend_request;

    private FirebaseAuth mAuth;
    private String senderUserId;
    private String currentState ="new";
    private DatabaseReference friendRequestRef,contactsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth =FirebaseAuth.getInstance();
        senderUserId = mAuth.getCurrentUser().getUid();

        friendRequestRef = FirebaseDatabase.getInstance().getReference().child("Friend Request");
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        receiverUserId =getIntent().getExtras().get("visit_user_id").toString();
        receiverUserImage =getIntent().getExtras().get("profile_image").toString();
        receiverUserName =getIntent().getExtras().get("profile_name").toString();

        background_profile_view = findViewById(R.id.background_profile_view);
        name_profile = findViewById(R.id.name_profile);
        add_freiend = findViewById(R.id.add_friend);
        decline_friend_request = findViewById(R.id.decline_friend_request);


        Picasso.get().load(receiverUserImage).into(background_profile_view);
        name_profile.setText(receiverUserName);

        manageClickEvents();

    }

    private void manageClickEvents() {

        friendRequestRef.child(senderUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(receiverUserId)){
                            String requestType = dataSnapshot.child(receiverUserId).child("request_type").getValue().toString();

                            if(requestType.equals("sent")){
                                currentState = "request_sent";
                                add_freiend.setText("Cancel Friend Request");
                            }
                           else if(requestType.equals("received")){
                                currentState = "request_received";
                                add_freiend.setText("Accept Friend Request");

                                decline_friend_request.setVisibility(View.VISIBLE);
                                decline_friend_request.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CancelFriendRequest();
                                    }
                                });
                            }
                        }
                        else {
                           contactsRef.child(senderUserId)
                                   .addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           if(dataSnapshot.hasChild(receiverUserId)){
                                               currentState = "friends";
                                               add_freiend.setText("Delete Contact");
                                           }else{
                                               currentState = "new";                                           }
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        if(senderUserId.equals(receiverUserId)){

            add_freiend.setVisibility(View.GONE);
        }
        else{
            add_freiend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentState.equals("new")){
                      SendFriendRequest();
                    }
                    if(currentState.equals("request_sent")){
                        CancelFriendRequest();

                    }
                    if(currentState.equals("request_received")){
                          AcceptFriendRequest();
                    }
                    if(currentState.equals("request_sent")){
                         CancelFriendRequest();
                    }

                }
            });
        }
    }

    private void AcceptFriendRequest() {
        contactsRef.child(senderUserId).child(receiverUserId).child("Contact").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            contactsRef.child(receiverUserId).child(senderUserId).child("Contact").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                friendRequestRef.child(senderUserId).child(receiverUserId).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    friendRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful()){
                                                                                        currentState = "friend";
                                                                                        add_freiend.setText("Delete Contact");

                                                                                        decline_friend_request.setVisibility(View.GONE);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void CancelFriendRequest() {
        friendRequestRef.child(senderUserId).child(receiverUserId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            friendRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                             currentState = "new";
                                             add_freiend.setText("Add Friend");
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void SendFriendRequest() {
       friendRequestRef.child(senderUserId).child(receiverUserId).child("request_type").setValue("sent")
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           friendRequestRef.child(receiverUserId).child(senderUserId).child("request_type").setValue("received")
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               currentState = "request_sent";
                                               add_freiend.setText("Cancel Friend Request");
                                               Toast.makeText(ProfileActivity.this, "Friend Request Sent", Toast.LENGTH_SHORT).show();
                                           }

                                       }
                                   });
                       }
                   }
               });
    }
}
