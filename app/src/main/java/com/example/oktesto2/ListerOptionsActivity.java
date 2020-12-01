package com.example.oktesto2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ListerOptionsActivity extends AppCompatActivity {
    ImageView listerSettings, CreateListing, ViewListed, ViewMeetups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_options);

        listerSettings = findViewById(R.id.lister_settings);
        listerSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(ListerOptionsActivity.this,ListerSettingsActivity.class);
                startActivity(settings);
                finish();
            }
        });

        CreateListing = findViewById(R.id.btn_new_listing);
        CreateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent petListing = new Intent(ListerOptionsActivity.this,NewPetProfileActivity.class);
                startActivity(petListing);
                finish();
            }
        });


//        ViewListed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent listedPets = new Intent(ListerOptionsActivity.this,?Activity.class);
//                startActivity(listedPets);
//                finish();
//            }
//        });

//        ViewMeetups.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent petMeetups = new Intent(ListerOptionsActivity.this,?Activity.class);
//                startActivity(petMeetups);
//                finish();
//            }
//        });




    }
}