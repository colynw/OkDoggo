package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button viewpages, favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewpages = findViewById(R.id.ViewProfile);
        favorites = findViewById(R.id.Favorite);

        viewpages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //takes them to the signup page
                Intent _Welcome = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(_Welcome);
            }
        });


        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //takes them to the signup page
                Intent _fav = new Intent(WelcomeActivity.this,FavoriteActivity.class);
                startActivity(_fav);
            }
        });




    }
}