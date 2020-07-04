package com.example.chatapp;

import android.Manifest;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoChat extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    String API_KEY = "46719562";
    String SESSION_ID = "1_MX40NjcxOTU2Mn5-MTU5MjIzNTU4NjIzOH5MSmRhUHlIamg1VEtqeGY4R2ZIcWhBTkp-fg";
    String TOKEN = "T1==cGFydG5lcl9pZD00NjcxOTU2MiZzaWc9YjA2M2FkNzM4OTM3MGYxZjBkMThhNzc2MzgyOTYzMThhZjA0NmI1ZDpzZXNzaW9uX2lkPTFfTVg0ME5qY3hPVFUyTW41LU1UVTVNakl6TlRVNE5qSXpPSDVNU21SaFVIbElhbWcxVkV0cWVHWTRSMlpJY1doQlRrcC1mZyZjcmVhdGVfdGltZT0xNTkyMjM1NjY1Jm5vbmNlPTAuODE4MTI5MDI3MzYxNjAyNiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTkyMjM5MzM2JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    String LOG_TAG = VideoChat.class.getSimpleName();
    final int RC_SETTINGS = 123;


    private Session session;
    private FrameLayout PublisherContainer;
    private FrameLayout SubscriberContainer;
    private Publisher publisher;
    private Subscriber subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_chat);

        requestPermission();
        PublisherContainer = (FrameLayout)findViewById(R.id.publisher_container);
        SubscriberContainer = (FrameLayout)findViewById(R.id.subscriber_container);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @AfterPermissionGranted(RC_SETTINGS)
    private void requestPermission() {
        String[] perm = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perm)){
            session = new Session.Builder(this,API_KEY,SESSION_ID).build();
            session.setSessionListener(this);
            session.connect(TOKEN);
        }
        else {
            EasyPermissions.requestPermissions(this,"This app needs to access your camera and mic",RC_SETTINGS,perm);

        }

    }

    @Override
    public void onConnected(Session session) {
        publisher= new Publisher.Builder(this).build();
        publisher.setPublisherListener(this);

        PublisherContainer.addView(publisher.getView());
        session.publish(publisher);
}

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

        if (subscriber == null)
        {
            subscriber=new Subscriber.Builder(this,stream).build();
            session.subscribe(subscriber);
            SubscriberContainer.addView(subscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        if (subscriber!=null)
        {
            subscriber = null;
            SubscriberContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

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
}
