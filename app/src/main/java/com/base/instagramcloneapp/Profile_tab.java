package com.base.instagramcloneapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_tab extends Fragment {

    private EditText profile_name,bio_editext,your_profession,hobbies,fav_sport;
    private Button UpdateInfobtn;
    FirebaseUser firebaseUser;


    private FirebaseAuth auth;

    public Profile_tab(){


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        profile_name = view.findViewById(R.id.profile_name);
        bio_editext = view.findViewById(R.id.bio_editext);
        your_profession = view.findViewById(R.id.your_profession);
        hobbies = view.findViewById(R.id.hobbies);
        fav_sport = view.findViewById(R.id.fav_sport);





        UpdateInfobtn = view.findViewById(R.id.UpdateInfobtn);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        if(userId == null){
            Toast.makeText(getContext(), "user id Null", Toast.LENGTH_SHORT).show();
            profile_name.setText("");
            bio_editext.setText("");
            your_profession.setText("");
            hobbies.setText("");
            fav_sport.setText("");
        }
        else{
            getUserDetails(userId);// writes the given values to the editext

        }




        UpdateInfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String profileName = profile_name.getText().toString();
                String bio = bio_editext.getText().toString();
                String profession = your_profession.getText().toString();
                String userHobbies = hobbies.getText().toString();
                String favoriteSport = fav_sport.getText().toString();

                UserDetails userDetails = new UserDetails(profileName, bio, profession, userHobbies, favoriteSport);


                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                    databaseReference.child(userId).setValue(userDetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Profile data saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle the error in saving data to Firebase
                                    Toast.makeText(getContext(), "Failed to save profile data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // Handle the case where there is no signed-in user
                    Toast.makeText(getContext(), "No user is signed in.", Toast.LENGTH_SHORT).show();
                }


            }
        });


       return view;
    }

    public void getUserDetails(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);

                    if (userDetails != null) {
                        String profileName = userDetails.getProfileName();
                        String yourBio = userDetails.getYourBio();
                        String yourProfession = userDetails.getYourProfession();
                        String yourHobbies = userDetails.getYourHobbies();
                        String favSport = userDetails.getFavSport();

                        profile_name.setText(profileName);
                        bio_editext.setText(yourBio);
                        your_profession.setText(yourProfession);
                        hobbies.setText(yourHobbies);
                        fav_sport.setText(favSport);

                        // Do something with the retrieved data
                    } else {
                        // Handle the case where userDetails is null
                    }
                } else {
                    // Handle the case where data doesn't exist
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during the retrieval
            }
        });
    }
}