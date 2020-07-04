package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImagesActivity<Uploads> extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    // private static final int STORAGE_PERMISSION_CODE = 10001;
    //private ListView myview
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private DatabaseReference mdatabaseRef;
    private StorageReference mRef;
    private List<Upload> mUploads;
    private Button mDownload;
    public ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        mRecyclerView = findViewById(R.id.recycler_view);
        imageView = findViewById(R.id.image_view_upload);
        //mDownload = findViewById(R.id.btn_download);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();

        mAdapter=new ImageAdapter(ImagesActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mStorage=FirebaseStorage.getInstance();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDBListener=mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Upload upload=snapshot.getValue(Upload.class);
                    upload.setKey(snapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public void onItemClick(int position) {



    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectItem=mUploads.get(position);
        final String selectedKey=selectItem.getKey();

        StorageReference imageRef=mStorage.getReferenceFromUrl(selectItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mdatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ImagesActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDownload(int position) {
        Upload selectItem=mUploads.get(position);
        final String selectedKey=selectItem.getKey();
        StorageReference imageRef=mStorage.getReferenceFromUrl(selectItem.getImageUrl());
        imageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    private static final String TAG = "";

                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG,"onSuccess:download url:"+uri.toString());
                        Toast.makeText(ImagesActivity.this, "URL received", Toast.LENGTH_SHORT).show();

                        downloadFile(uri);
                    }

                    private void downloadFile(Uri uri) {
                        File file = new File(Environment.getExternalStorageDirectory(),"image.jpg");
                        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(uri)
                                .setTitle("File Download")
                                .setDescription("Download started")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                                .setDestinationUri(Uri.fromFile(file));
                        //.setDestinationInExternalFilesDir()

                        downloadManager.enqueue(request);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            private static final String TAG = "";

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"onFailure: Error:"+e.getMessage());
                Toast.makeText(ImagesActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdatabaseRef.removeEventListener(mDBListener);
    }
}
