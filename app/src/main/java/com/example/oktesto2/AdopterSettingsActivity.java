package com.example.oktesto2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdopterSettingsActivity extends AppCompatActivity {
    Button Logout, Saved, Search;
    Spinner userSpinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_settings);
        userSpinner = (Spinner)findViewById(R.id.spinner_user_type_adopter);
        adapter = ArrayAdapter.createFromResource(this, R.array.user_types_adopter,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setSelection(0, false);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "User type has been changed to " + adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
                Intent changeType = new Intent(AdopterSettingsActivity.this,ListerOptionsActivity.class);
                startActivity(changeType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Logout = findViewById(R.id.btn_logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });//end Logout OnClickListener

        Saved = findViewById(R.id.btn_view_saved_profiles);
        Saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                finish();

            }
        });//end Saved OnClickListener

        Search = findViewById(R.id.btn_view_search_criteria);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchCriteriaActivity.class));
                finish();

            }
        });//end Search OnClickListener

    }//end void onCreate

}//end SettingsActivity