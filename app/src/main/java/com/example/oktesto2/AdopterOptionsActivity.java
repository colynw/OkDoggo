package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdopterOptionsActivity extends AppCompatActivity {

    ImageView viewpages, favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_options);

        viewpages = findViewById(R.id.ViewProfile);
        favorites = findViewById(R.id.Favorite);

        viewpages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _Welcome = new Intent(AdopterOptionsActivity.this,MainActivity.class);
                startActivity(_Welcome);
            }
        });


        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _fav = new Intent(AdopterOptionsActivity.this,FavoriteActivity.class);
                startActivity(_fav);
            }
        });




    }
}