package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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
    int[] images = {R.drawable.dog1,R.drawable.bella1,R.drawable.weds,R.drawable.molly,R.drawable.april,R.drawable.cheeto,R.drawable.ger,R.drawable.chakra,R.drawable.san,R.drawable.lor,R.drawable.vive,R.drawable.neb,R.drawable.snow,R.drawable.bell,R.drawable.jul,R.drawable.randy};
    Random rand = new Random();
    int score = rand.nextInt((16-1)+1)+1;   // gets a random number
    String Database = String.valueOf(score);

    // Firebase
    FirebaseAuth fAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference DR = db.collection("Dog").document(Database);

    // Miscellaneous
    ConstraintLayout constraintLayout;
    ImageView Settings, Report, Pass, Save;
    ImageView tabWindowOutline;
    ViewPager tabWindow;
    TabLayout tabBar;
    TextView Change;
    String Name, Breed, Sex, Age, Description, Personality,
            HistoryMedical, HistoryBehavior, HistoryHome, USER;
    Double Latitude = 33.533480, Longitude = -101.799900;
    boolean showTabs = false;
    float x1,x2,y1,y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Change = findViewById(R.id.placeholder_change);
        constraintLayout = findViewById(R.id.coordinatorLayout6);
        setProfileImage();
        pullPetInfo();
        enableTabView();
        enableButtons();
    }// end void onCreate

    void setProfileImage(){
        ImageView mImageView;
        mImageView = findViewById(R.id.img_profile_pic);
        //sets the picture based on what document is getting pulled from the database
        mImageView.setImageResource(images[score-1]);
    }
    void enableTabView(){
        final Button toggleTabs = findViewById(R.id.btn_toggle_tabs);
        toggleTabs.setText("View more");
        tabWindow = findViewById(R.id.view_pager);
        tabBar = findViewById(R.id.tabs);
        tabWindowOutline = findViewById(R.id.outline_tab_window);

        toggleTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showTabs) {
                    tabWindow.setVisibility(View.VISIBLE);
                    tabBar.setVisibility(View.VISIBLE);
                    tabWindowOutline.setVisibility(View.VISIBLE);
                    Pass.setVisibility(View.INVISIBLE);
                    Save.setVisibility(View.INVISIBLE);
                    toggleTabs.setText("View less");
                    showTabs = true;
                } else
                {
                    tabWindow.setVisibility(View.INVISIBLE);
                    tabBar.setVisibility(View.INVISIBLE);
                    tabWindowOutline.setVisibility(View.INVISIBLE);
                    Pass.setVisibility(View.VISIBLE);
                    Save.setVisibility(View.VISIBLE);
                    toggleTabs.setText("View more");
                    showTabs = false;
                }
            }
        });

    }
    void enableButtons(){
        Settings = findViewById(R.id.settings);
        Settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(activityChangeIntent);
                finish();
            }
        });

        Report = findViewById(R.id.report);
        Report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(activityChangeIntent);
                finish();
            }
        });

        Pass = findViewById(R.id.pass);
        Pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onLeftSwipe();
            }
        });

        Save = findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRightSwipe();
            }
        });
    }
    void onLeftSwipe(){
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    void onRightSwipe(){
        USER = fAuth.getCurrentUser().getEmail().toString();
        Map<String, Object> docData = new HashMap<>();
        docData.put("Name", Name);
        docData.put("Breed", Breed);
        docData.put("Age", Age);
        docData.put("Sex", Sex);
        db.collection(USER).document(Database).set(docData);
        Toast.makeText(MainActivity.this, "Pet added to favorites", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!showTabs){
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    if(x1 <  x2){
                        onRightSwipe();
                    } else if(x1 > x2){
                        onLeftSwipe();
                    }
                    break;
            }//end switch
        }
        return false;
    }// end bool onTouchEvent
    void setBackground(){
        if (Sex.equalsIgnoreCase("Female")){
            constraintLayout.setBackgroundResource(R.drawable.background_profile_female);
        }
        else{
            constraintLayout.setBackgroundResource(R.drawable.background_profile_male);
        }
    }
    void pullPetInfo(){
        fAuth = FirebaseAuth.getInstance();
        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //pulling this info from the database
                Name = value.getString("Name");
                Breed = value.getString("Breed");
                Sex = value.getString("Sex");
                Age = value.getString("Age");
                Description = value.getString("Description");
                Personality = value.getString("Personality");
                HistoryMedical = value.getString("Medical");
                HistoryBehavior = value.getString("Behavior");
                HistoryHome = value.getString("Home");
                //Latitude = value.getDouble("Lat");
                //Longitude = value.getDouble("Long");

                Change.setText("Changed");
            }
        });//end DR addSnapshotListener

        // Don't ask. It takes serious skill to come up with this bad of a work around.
        Change.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    setBackground(); // Red for female, blue for male.

                    // Everything bundled here goes into the General Tab
                    Bundle pet_general = new Bundle();
                    pet_general.putString("name", Name);
                    pet_general.putString("breed", Breed);
                    pet_general.putString("sex", Sex);
                    pet_general.putString("age", Age);
                    pet_general.putString("description", Description);
                    //Todo: Add personality

                    // Everything bundled here goes into the Notes Tab
                    Bundle pet_notes = new Bundle();
                    pet_notes.putString("history_medical", HistoryMedical);
                    pet_notes.putString("history_behavior", HistoryBehavior);
                    pet_notes.putString("history_home", HistoryHome);

                    // Everything bundled here goes into the Map Tab
                    Bundle pet_map = new Bundle();
                    pet_map.putDouble("lat", Latitude);
                    pet_map.putDouble("long", Longitude);

                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this,
                            getSupportFragmentManager(), pet_general, pet_notes, pet_map);
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    TabLayout tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);

                }
            }
        });
    }//end pullPetInfo
}//end MainActivity