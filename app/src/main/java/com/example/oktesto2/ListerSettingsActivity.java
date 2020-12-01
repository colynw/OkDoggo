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

public class ListerSettingsActivity extends AppCompatActivity {
    Button Logout;
    Spinner userSpinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_settings);
        userSpinner = (Spinner)findViewById(R.id.spinner_user_type_lister);
        adapter = ArrayAdapter.createFromResource(this, R.array.user_types_lister,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setSelection(0, false);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "User type has been changed to " + adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
                Intent changeType = new Intent(ListerSettingsActivity.this,AdopterOptionsActivity.class);
                startActivity(changeType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Logout = findViewById(R.id.btn_logout_lister);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });//end Logout OnClickListener

    }//end void onCreate

}//end SettingsActivity