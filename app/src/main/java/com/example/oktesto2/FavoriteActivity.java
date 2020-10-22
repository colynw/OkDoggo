package com.example.oktesto2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class FavoriteActivity extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    FirebaseAuth fAuth;

    String USER;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        fAuth = FirebaseAuth.getInstance();

        USER = fAuth.getCurrentUser().getEmail().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int i = 1;



        list = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        list.setAdapter(adapter);




        for(i=1; i<10; i++) {
            String count = String.valueOf(i);
            DocumentReference DR = db.collection(USER).document(count);



            DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.getString("Name")== null){

                    }
                    else {


                        arrayList.add(value.getString("Name"));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }



    }
}