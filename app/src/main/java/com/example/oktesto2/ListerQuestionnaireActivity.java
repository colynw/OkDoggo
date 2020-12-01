package com.example.oktesto2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ListerQuestionnaireActivity extends AppCompatActivity {
Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_questionnaire);
        submit = findViewById(R.id.submit_lister_questionnaire);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(ListerQuestionnaireActivity.this, ListerOptionsActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }
}