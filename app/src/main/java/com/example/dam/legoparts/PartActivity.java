package com.example.dam.legoparts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PartActivity extends AppCompatActivity {
    TextView name;
    TextView qty;
    TextView firsTime;
    TextView lastTime;
    Button url;
    ImageView image;
    String qtyInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
        name= (TextView) findViewById(R.id.name);
        qty= (TextView) findViewById(R.id.qty);
        firsTime= (TextView) findViewById(R.id.first_time);
        lastTime= (TextView) findViewById(R.id.last_time);
        url= (Button) findViewById(R.id.url);
        image= (ImageView) findViewById(R.id.imageGrande);
        Intent intent= this.getIntent();
        String id=intent.getStringExtra("id");
        qtyInt= intent.getStringExtra("cantidad");
        DescargaPart dp= new DescargaPart(this, id);
        dp.setPartActivity(this);
        dp.execute();
    }

    public void notifyPart(Pieza pieza) {
        name.setText(pieza.getName());
        qty.setText(qtyInt);
        firsTime.setText(pieza.getYearFrom().toString());
        lastTime.setText(pieza.getYearTo().toString());
        url.setText(pieza.getPartUrl());
        Picasso.with(this).load(pieza.getPartImgUrl()).into(image, new Callback() {
            @Override
            public void onSuccess() {}
            @Override
            public void onError() {}
        });
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPart= (String) url.getText().toString();
                Uri uriUrl = Uri.parse(urlPart);
                Intent i = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(i);
            }
        });
    }
}
