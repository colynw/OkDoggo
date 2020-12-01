package com.example.oktesto2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewPetProfileActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    ImageView back;
    TextView pName, pBreed;
    Spinner sexSpinner, monthSpinner, yearSpinner, sizeSpinner;
    EditText pDescription, pMedical, pBehavior, pHome;
    Button submit;

    private ImageView profilePic;
    public Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dogRef = db.collection("Dog");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet_profile);

        pName = findViewById(R.id.field_name_c);
        pBreed = findViewById(R.id.field_breed_c);
        pDescription = findViewById(R.id.field_description_c);
        pMedical = findViewById(R.id.field_history_medical_c);
        pBehavior = findViewById(R.id.field_history_behavior_c);
        pHome = findViewById(R.id.field_history_home_c);

        sexSpinner = (Spinner) findViewById(R.id.spinner_sex);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_gender, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        monthSpinner = (Spinner) findViewById(R.id.spinner_months);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_age_months, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        yearSpinner = (Spinner) findViewById(R.id.spinner_years);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_age_years, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

        sizeSpinner = (Spinner) findViewById(R.id.spinner_size);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_size, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic = findViewById(R.id.UploadPic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        back = findViewById(R.id.back_create_listing);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListerOptionsActivity.class));
                finish();
            }
        });
        submit = findViewById(R.id.submit_new_profile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profile_img_key = uploadPicture();
                addDog(profile_img_key);

            }
        });
    }

    private void addDog(String img_key) {
        String Name = pName.getText().toString();
        String Breed = pBreed.getText().toString();
        String Sex = sexSpinner.getSelectedItem().toString();
        String Age = yearSpinner.getSelectedItem().toString() + " year(s) + "
                + monthSpinner.getSelectedItem().toString() + " months old";
        String Size = sizeSpinner.getSelectedItem().toString();
        String Description = pDescription.getText().toString();
        String Medical = pMedical.getText().toString();
        String Behavior = pBehavior.getText().toString();
        String Home = pHome.getText().toString();

        Map<String, Object> dog = new HashMap<>();
        dog.put("Image Key", img_key);
        dog.put("Name",Name);
        dog.put("Breed",Breed);
        dog.put("Sex",Sex);
        dog.put("Age",Age);
        dog.put("Size",Size);
        dog.put("Description",Description);
        dog.put("Medical",Medical);
        dog.put("Behavior",Behavior);
        dog.put("Home",Home);
        dog.put("Lat", 0.0);
        dog.put("Long", 0.0);

        db.collection("Dog").document("Dog ID Goes Here").set(dog);
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(Main)
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                }


    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
        
    }

    private String uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + ((int) progressPercent + "%"));
                    }
                });
        return randomKey;
    }
}