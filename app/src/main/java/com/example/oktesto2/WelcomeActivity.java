package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView viewpages, favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewpages = findViewById(R.id.ViewProfile);
        favorites = findViewById(R.id.Favorite);

        viewpages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _Welcome = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(_Welcome);
            }
        });


        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _fav = new Intent(WelcomeActivity.this,FavoriteActivity.class);
                startActivity(_fav);
            }
        });




    }
}