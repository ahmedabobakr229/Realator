package com.example.document.realator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

     RecyclerView mrecyclerView;
     ImageAdapter mAdapter;
     ProgressBar mprogresscicle;
     DatabaseReference mdatabaseRef;
     List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mrecyclerView = findViewById(R.id.recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mprogresscicle = findViewById(R.id.progress_circle);

        mUploads=new ArrayList<>();

        mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    Upload upload = postsnapshot.getValue(Upload.class);
                    mUploads.add(upload);

                }
                mAdapter = new ImageAdapter(ImageActivity.this , mUploads);
                mrecyclerView.setAdapter(mAdapter);
                mprogresscicle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ImageActivity.this , databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mprogresscicle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
