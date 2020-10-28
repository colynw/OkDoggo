package com.example.oktesto2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.oktesto2.ProfileTab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab3 extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;


    int count = 0;
    int count2 = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.oktesto2.ProfileTab3 newInstance(String param1, String param2) {
        com.example.oktesto2.ProfileTab3 fragment = new com.example.oktesto2.ProfileTab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }




            count = 1;



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);

        return inflater.inflate(R.layout.fragment_profiletab3, container, false);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng LAS = new LatLng(33.533480, -101.799900);
        mMap.addMarker(new MarkerOptions().position(LAS).title("Lubbock Animal Shelter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LAS, 15));
    }


    // thought i needed this. keeping it just incase
   // @Override
    //public void onDestroyView() {
      //  super.onDestroyView();
       // MapFragment mapFragment = (MapFragment) getActivity()
             //   .getFragmentManager().findFragmentById(R.id.map);
       // if (mapFragment != null)
            //getActivity().getFragmentManager().beginTransaction()
             //       .remove(mapFragment).commit();
   // }


}
