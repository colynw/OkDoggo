package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Intent;

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
    int[] images = {R.drawable.dog1,R.drawable.bella1,R.drawable.weds,R.drawable.molly,R.drawable.april,R.drawable.cheeto};
    Random rand = new Random();
    int score = rand.nextInt((6-1)+1)+1;   // gets a random number
    String Database = String.valueOf(score);

    // Firebase
    FirebaseAuth fAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference DR = db.collection("Dog").document(Database);

    // Miscellaneous
    ImageView Settings, Report;
    ViewPager tabWindow;
    TabLayout tabBar;
    TextView pName, pBreed, pSex, pAge, pDescription, pPersonality,
            pHistoryMedical,pHistoryBehavior,pHistoryHome,
            Change;
    String Name, Breed, Sex, Age, Description, Personality,
            HistoryMedical, HistoryBehavior, HistoryHome;
    boolean showTabs = false;
    float x1,x2,y1,y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializePlaceholders();
        setProfileImage();
        pullPetInfo();
        enableTabView();
        enableButtons();
    }// end void onCreate

    void initializePlaceholders(){
        pName = findViewById(R.id.placeholder_name);
        pBreed = findViewById(R.id.placeholder_breed);
        pSex = findViewById(R.id.placeholder_sex);
        pAge = findViewById(R.id.placeholder_age);
        pDescription = findViewById(R.id.placeholder_description);
        pPersonality = findViewById(R.id.placeholder_personality_traits);
        pHistoryMedical = findViewById(R.id.placeholder_history_medical);
        pHistoryBehavior = findViewById(R.id.placeholder_history_behavior);
        pHistoryHome = findViewById(R.id.placeholder_history_home);
        Change = findViewById(R.id.placeholder_change);
    }
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

        toggleTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showTabs) {
                    tabWindow.setVisibility(View.VISIBLE);
                    tabBar.setVisibility(View.VISIBLE);
                    toggleTabs.setText("View less");
                    showTabs = true;
                } else
                {
                    tabWindow.setVisibility(View.INVISIBLE);
                    tabBar.setVisibility(View.INVISIBLE);
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
            }
        });

        Report = findViewById(R.id.report);
        Report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }
    void pullPetInfo(){
        // This is honest to god the worst work around I've ever done for anything ever.
        // Shield your eyes and don't even think about trying to figure out how it works.
        fAuth = FirebaseAuth.getInstance();
        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //pulling this info from the database
                pName.setText(value.getString("Name"));
                pBreed.setText(value.getString("Breed"));
                pSex.setText(value.getString("Sex"));
                pAge.setText(value.getString("Age"));
                pDescription.setText(value.getString("Description"));
                pPersonality.setText(value.getString("Personality"));
                pHistoryMedical.setText(value.getString("Medical"));
                pHistoryBehavior.setText(value.getString("Behavior"));
                pHistoryHome.setText(value.getString("Home"));
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
                    Name = pName.getText().toString();
                    Breed = pBreed.getText().toString();
                    Sex = pSex.getText().toString();
                    Age = pAge.getText().toString();
                    Description = pDescription.getText().toString();
                    Personality = pPersonality.getText().toString();
                    HistoryMedical = pHistoryMedical.getText().toString();
                    HistoryBehavior = pHistoryBehavior.getText().toString();
                    HistoryHome = pHistoryHome.getText().toString();

                    // Everything bundled here goes into the General Tab
                    Bundle pet_general = new Bundle();
                    pet_general.putString("name", Name); //Name
                    pet_general.putString("breed", Breed);
                    pet_general.putString("sex", Sex);
                    pet_general.putString("age", Age);
                    pet_general.putString("description", Description);
                    //Todo: Add personality

                    // Everything bundled here goes into the Notes Tab
                    Bundle pet_notes = new Bundle();
                    pet_notes.putString("history_medical", HistoryMedical); //HistoryMedical
                    pet_notes.putString("history_behavior", HistoryBehavior);
                    pet_notes.putString("history_home", HistoryHome);

                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager(), pet_general, pet_notes);
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    TabLayout tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);
                }
            }
        });
    }

    @Override
    //----------------------------------------------------------------------------------------------
    // Swipe Left/Right Event
    //----------------------------------------------------------------------------------------------
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
                    if(x1 <  x2){  //swipe left pet is saved

                        Map<String, Object> docData = new HashMap<>();
                        docData.put("Name", Name);
                        docData.put("Breed", Breed);
                        docData.put("Age", Age);
                        docData.put("Sex", Sex);
                        db.collection("USER").document(Database).set(docData);
                        Toast.makeText(MainActivity.this, "Pet added to favorites", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    } else if(x1 > x2){   //swipe right pet is not saved
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    break;
            }//end switch
        }

        return false;
    }// end bool onTouchEvent

}//end MainActivity