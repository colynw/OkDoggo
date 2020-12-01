package com.example.oktesto2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ResourceBundle;
import javax.annotation.Nullable;
public class FavoriteActivity extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore db;

    String USER;
    int i;



    EditText PET_NAME,BREED;
   Random rand = new Random();
    int score = rand.nextInt((16-1)+1)+1;

    String count = String.valueOf(score);


    Button Date,Next_Pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        PET_NAME=findViewById(R.id.Dog);
        BREED=findViewById(R.id.Breed);



        int[] images = {R.drawable.dog1,R.drawable.bella1,R.drawable.weds,R.drawable.molly,R.drawable.april,R.drawable.cheeto,R.drawable.ger,R.drawable.chakra,R.drawable.san,R.drawable.lor,R.drawable.vive,R.drawable.neb,R.drawable.snow,R.drawable.bell,R.drawable.jul,R.drawable.randy};


       db = FirebaseFirestore.getInstance();



        fAuth = FirebaseAuth.getInstance();
        USER = fAuth.getCurrentUser().getEmail().toString();



        for(i = 0; i < 20; i++) {


            DocumentReference DR = db.collection(USER).document(count);


            DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                    if (value.getString("Name") != null) {


                        PET_NAME.setText(value.getString("Name"));
                        BREED.setText(value.getString("Breed"));
                        //if(score > 16){
                          //  score = 0;
                      //  }
                        i = 20;


                    } else {



                        score = rand.nextInt((i-1)+1)+1;

                       count = String.valueOf(score);




                       }


                }
            });

        }
        ImageView mImageView;
        mImageView = (ImageView)findViewById(R.id.pic);
//set resource for imageview
        mImageView.setImageResource(images[score-1]);




        Next_Pet = findViewById(R.id.Next_Pet);
        Next_Pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoriteActivity.this, FavoriteActivity.class);
                startActivity(i);
            }
        });


        Date = findViewById(R.id.Date);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("Meetup", true);
                db.collection(USER).document(count).set(docData, SetOptions.merge());
                Toast.makeText(FavoriteActivity.this, "Metting Request Sent Succesfully", Toast.LENGTH_SHORT).show();

            }
        });


        }
    }
