package com.example.oktesto2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class ProfileTab1 extends Fragment {
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "breed";
    private static final String ARG_PARAM3 = "sex";
    private static final String ARG_PARAM4 = "age";
    private static final String ARG_PARAM5 = "description";

    TextView Name, Breed, Sex, Age, Description;

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    public ProfileTab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profiletab1, container, false);
        Name = (TextView) view.findViewById(R.id.field_name);
        Breed = (TextView) view.findViewById(R.id.field_breed);
        Sex = (TextView) view.findViewById(R.id.field_sex);
        Age = (TextView) view.findViewById(R.id.field_age);
        Description = (TextView) view.findViewById(R.id.field_description);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }

        Name.setText(mParam1);
        Breed.setText(mParam2);
        Sex.setText(mParam3);
        Age.setText(mParam4);
        Description.setText(mParam5);
        return view;
    }
}