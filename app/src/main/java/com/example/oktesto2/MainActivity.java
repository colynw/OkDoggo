package com.example.oktesto2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import java.util.ResourceBundle;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {




    EditText PET_NAME,BREED,AGE, SEX;
    float x1,x2,y1,y2;




    Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PET_NAME=findViewById(R.id.Dog);
        BREED=findViewById(R.id.Breed);
        AGE=findViewById(R.id.Age);
        SEX=findViewById(R.id.Sex);


        //6 sets of pet data is currently in the database need to add the picture to the drawable.
        int[] images = {R.drawable.dog1,R.drawable.bella,R.drawable.weds,R.drawable.molly,R.drawable.april,R.drawable.cheeto};  //Pictures currently not in the database. was having a huge issue with them
        Random rand = new Random(); // setting up random so it shows a random pet every time someone swipes. currently some pets repeat but once we have enough pets it wont be a issue
        int score = rand.nextInt((6-1)+1)+1;   // gets a random number 
        String Database = String.valueOf(score);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("Dog").document(Database);
        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {



                //pulling this info from the database
                PET_NAME.setText(value.getString("Name"));
                BREED.setText(value.getString("Breed"));
                AGE.setText(value.getString("Age"));
                SEX.setText(value.getString("Sex"));




            }
        });


        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.pic);




    //sets the picture based on what document is getting pulled from the database
        mImageView.setImageResource(images[score-1]);





        Logout = findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });








    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {  //swipe event
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(x1 <  x2){  //swipe left
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }else if(x1 > x2){  //swipe right. should push the current pet data into the data base so we can just pull it when we have everything set up
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
}
