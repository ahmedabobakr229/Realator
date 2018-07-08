package com.example.document.realator;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SellActivity_Req extends AppCompatActivity {

    TextView  price , mobile , city , data;
    ImageView img;
    EditText ent_price , ent_mobile , ent_city , ent_data;
    Button addimg , upload_img , den_om_lista;
    private Uri mimageuri;
    private static final int PICK_IMAGE_REQUEST=1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    ProgressBar mprogressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell__req);


        mprogressbar = (ProgressBar)findViewById(R.id.progress);
        addimg = (Button) findViewById(R.id.head);
        upload_img = (Button)findViewById(R.id.upload);
       // price = (TextView)findViewById(R.id.pric);
        // mobile = (TextView)findViewById(R.id.phone);
        data = (TextView)findViewById(R.id.etdeatails);
        //city = (TextView)findViewById(R.id.etcity);
        img = (ImageView)findViewById(R.id.img);
        //ent_price = (EditText)findViewById(R.id.enprice);
        //ent_city = (EditText)findViewById(R.id.city);
        //ent_mobile = (EditText)findViewById(R.id.phone_num);
        ent_data = (EditText)findViewById(R.id.details);

        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");



        //set on click listener


        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
    }
        private void openfilechooser(){
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i,PICK_IMAGE_REQUEST);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData()!= null){
                mimageuri = data.getData();

            Picasso.with(this).load(mimageuri).into(img);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile(){

        if (mimageuri != null){
            StorageReference filerefrence = storageReference.child(System.currentTimeMillis()
            + "." + getFileExtension(mimageuri));

             filerefrence.putFile(mimageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressbar.setProgress(0);
                                }
                            },500);

                            Toast.makeText(SellActivity_Req.this, "تم التحميل بنجاح", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(ent_data.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadid = databaseReference.push().getKey();
                            databaseReference.child(uploadid).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SellActivity_Req.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mprogressbar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "لم يتم اختيار ملف ", Toast.LENGTH_SHORT).show();
        }
    }
}
