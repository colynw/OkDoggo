package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Intent;

import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.oktesto2.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.WriteResult;


import java.util.ResourceBundle;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    // Locally stored images, randomly selected for now.
    int[] images = {R.drawable.dog1,R.drawable.bella1,R.drawable.weds,R.drawable.molly,R.drawable.april,R.drawable.cheeto};
    Random rand = new Random();
    int score = rand.nextInt((6-1)+1)+1;   // gets a random number
    String Database = String.valueOf(score);

    // Firebase stuff and things
    FirebaseAuth fAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference DR = db.collection("Dog").document(Database);

    // Misc
    ImageView Settings, Report;
    String Name, Breed, Sex, Age, Description, Personality;
    String HistoryMedical, HistoryBehavior, HistoryHome;
    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -----------------------------------------------------------------------------------------
        // Setting the profile image
        // -----------------------------------------------------------------------------------------
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.img_profile_pic);
        //sets the picture based on what document is getting pulled from the database
        mImageView.setImageResource(images[score-1]);

        // -----------------------------------------------------------------------------------------
        // Pulling Information from the Database
        // -----------------------------------------------------------------------------------------
        fAuth = FirebaseAuth.getInstance();

        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //pulling this info from the database
                Name = value.getString("Name");
                Breed = value.getString("Breed");
                Sex = value.getString("Sex");
                Age = value.getString("Age");
            }
        });//end DR addSnapshotListener

        // The addSnapshotListener will always complete after the bundles are made
        // That's a problem because I'm sending the information to the fragments via bundles
        // And we don't have the luxury of waiting, so it will display as null.

        // Everything bundled here goes into the General Tab
        Bundle pet_general = new Bundle();
        pet_general.putString("name", "Kobe"); //Name
        pet_general.putString("breed", Breed);
        pet_general.putString("sex", Sex);
        pet_general.putString("age", Age);
        pet_general.putString("description", Description);

        // Everything bundled here goes into the Notes Tab
        Bundle pet_notes = new Bundle();
        pet_notes.putString("history_medical", "This dog has rabies and is special needs."); //HistoryMedical
        pet_notes.putString("history_behavior", HistoryBehavior);
        pet_notes.putString("history_home", HistoryHome);

        // -----------------------------------------------------------------------------------------
        // Makes profile tabs work, sends database information to tabs in a bundle
        // -----------------------------------------------------------------------------------------
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), pet_general, pet_notes);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //  USER = fAuth.getCurrentUser().getEmail().toString();

        //------------------------------------------------------------------------------------------
        // Clickable On-screen Options
        //------------------------------------------------------------------------------------------
        Settings = findViewById(R.id.settings);
        Settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(activityChangeIntent);
            }
        });

        Report = findViewById(R.id.report);
        Report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }// end void onCreate

// Swipe can be re-enabled after we get button to hide tabs
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {  //swipe event
//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                y1 = event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                y2 = event.getY();
//                if(x1 <  x2){  //swipe left pet is saved
//
//                    Map<String, Object> docData = new HashMap<>();
//                    docData.put("Name", N);
////                    docData.put("Breed", B);
////                    docData.put("Age", A);
////                    docData.put("Sex", S);
//                    db.collection(USER).document(Database).set(docData);
//                    Toast.makeText(MainActivity.this, "Pet added to favorites", Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(i);
//            } else if(x1 > x2){   //swipe right pet is not saved
//
//                Intent i = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//            break;
//        }//end switch
//        return false;
//    }// end bool onTouchEvent
}//end MainActivity
