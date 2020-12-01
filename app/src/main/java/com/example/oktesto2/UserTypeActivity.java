package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserTypeActivity extends AppCompatActivity {
    ImageView Adopter, Lister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        Adopter = findViewById(R.id.btn_adopter);
        Lister = findViewById(R.id.btn_lister);

        Adopter = findViewById(R.id.btn_adopter);
        Adopter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(UserTypeActivity.this, AdopterQuestionnaireActivity.class);
                startActivity(activityChangeIntent);
            }
        });

        Lister = findViewById(R.id.btn_lister);
        Lister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(UserTypeActivity.this, ListerQuestionnaireActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }
}