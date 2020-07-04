package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.events.Subscriber;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoChatActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {
   private String API_KEY = "46719562";
    private String SESSION_ID = "1_MX40NjcxOTU2Mn5-MTU5MzYxNzY2ODQwOH5sbzJONFBQV3F2YkJESytQWnI4R2lOQlN-fg";
    private  String TOKEN = "T1==cGFydG5lcl9pZD00NjcxOTU2MiZzaWc9YTliNzRlM2MxZjAxMmU4YzBjY2U1YWExZWQzZDdlMjA3ZDJmZWZlOTpzZXNzaW9uX2lkPTFfTVg0ME5qY3hPVFUyTW41LU1UVTVNell4TnpZMk9EUXdPSDVzYnpKT05GQlFWM0YyWWtKRVN5dFFXbkk0UjJsT1FsTi1mZyZjcmVhdGVfdGltZT0xNTkzNjE3Nzg4Jm5vbmNlPTAuMDI1NzkyOTg5ODU4Mjc0OTQmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU5NjIwOTg2MSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static final String LOG_TAG = VideoChatActivity.class.getSimpleName();
    private static final int RC_VIDEO_APP_PERM = 124;

    private FrameLayout mPublisherViewController;
    private FrameLayout mSubscriberViewController;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    //private MediaPlayer mediaPlayer;

    private ImageView closeVideoChatBtn;
    private DatabaseReference userRef;
    private  String userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("User");

        closeVideoChatBtn =findViewById(R.id.close_video_chat_btn);
        closeVideoChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(userId).hasChild("Ringing")){

                            userRef.child(userId).child("Ringing").removeValue();

                            if(mPublisher!=null){
                                mPublisher.destroy();
                            }
                            if(mSubscriber!=null){

                                mSubscriber.destroy();
                            }


                            startActivity(new Intent(VideoChatActivity.this,VideoChatApp.class));
                            finish();
                        }
                        if(dataSnapshot.child(userId).hasChild("Calling")){
                            userRef.child(userId).child("Calling").removeValue();

                            if(mPublisher!=null){
                                mPublisher.destroy();
                            }
                            if(mSubscriber!=null){

                                mSubscriber.destroy();
                            }
                            startActivity(new Intent(VideoChatActivity.this,VideoChatApp.class));
                            finish();
                        }
                        else{
                            if(mPublisher!=null){
                                mPublisher.destroy();
                            }
                            if(mSubscriber!=null){

                                mSubscriber.destroy();
                            }
                            startActivity(new Intent(VideoChatActivity.this,VideoChatApp.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,VideoChatActivity.this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermission() {
        String[] perm = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perm)){
            mPublisherViewController = findViewById(R.id.publisher_container);
            mSubscriberViewController = findViewById(R.id.subscriber_container);

            mSession = new Session.Builder(this,API_KEY,SESSION_ID).build();
            mSession.setSessionListener(VideoChatActivity.this);
            mSession.connect(TOKEN);

        }
        else {
            EasyPermissions.requestPermissions(this,"This app needs to access your camera and mic",RC_VIDEO_APP_PERM,perm);

        }

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");
        mPublisher= new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(VideoChatActivity.this);

        mPublisherViewController.addView(mPublisher.getView());
        if(mPublisher.getView()instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Stream Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null)
        {
            mSubscriber=new Subscriber.Builder(this,stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewController.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
        if (mSubscriber!=null){

            mSubscriber = null;
            mSubscriberViewController.removeAllViews();
        }

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.i(LOG_TAG, "Stream Error");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
