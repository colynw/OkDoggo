package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdopterQuestionnaireActivity extends AppCompatActivity {
Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_questionnaire);
        submit = findViewById(R.id.submit_adopter_questionnaire);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(AdopterQuestionnaireActivity.this, AdopterOptionsActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }
}