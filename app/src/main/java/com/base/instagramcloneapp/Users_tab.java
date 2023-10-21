package com.base.instagramcloneapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Users_tab extends Fragment {

    private ListView listVIew;
    private ArrayList<UserDetails> arrayList;

    private UserAdapter mArrayAdapter;

    private TextView loading_mssg;


    public Users_tab(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listVIew = view.findViewById(R.id.listVIew);
        arrayList =new ArrayList<>();
        loading_mssg = view.findViewById(R.id.loading_mssg);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear(); // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDetails user = snapshot.getValue(UserDetails.class); // Assuming User is a custom class representing user details
                    if (user != null && !snapshot.getKey().equals(userId)) {
                        arrayList.add(user);
                    }
                }

                // Create an ArrayAdapter to populate the ListView
                UserAdapter adapter = new UserAdapter(getContext(), R.layout.user_list_item, arrayList);
                listVIew.setAdapter(adapter);
                loading_mssg.animate().alpha(0).setDuration(2000);
                listVIew.setVisibility(view.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        });






        

        return view;
    }
}