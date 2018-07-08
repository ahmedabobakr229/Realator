package com.example.document.realator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button sel , buyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sel = (Button)findViewById(R.id.sell);
        buyi = (Button)findViewById(R.id.buy);


        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SellActivity_Req.class);
                startActivity(i);
            }
        });
        buyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity();
            }
        });
    }
    private void openImageActivity(){
        Intent i = new Intent(MainActivity.this,ImageActivity.class);
        startActivity(i);
    }
}
