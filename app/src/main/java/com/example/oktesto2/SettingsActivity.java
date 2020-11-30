package com.example.oktesto2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    Button Logout, Saved, Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
//
//        EditInfo = findViewById(R.id.btn_view_user_info);
//        EditInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),EditInfoActivity.class));
//                finish();
//            }
//        });//end EditInfo OnClickListener


    }//end void onCreate

}//end SettingsActivity