package com.example.caronrent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.caronrent.Tourism.Tourism_Data_add;
import com.example.caronrent.Tourism.Tourism_place_show;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    String eem;
    Button editButton;
    LinearLayout settingCall,Payent,historybtn;
    ImageView img_profile_user,back_btn;
    SharedPreferences sharedPreferences;

    TextView txtName, txtEmailUser, txtPassWord, txtMo, txtGend, txtCit, txtDll;
    String emailShare, name, email, pass, mob, gender, city, dl;
    private DatabaseReference usersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        editButton = view.findViewById(R.id.editButton);
        settingCall = view.findViewById(R.id.settingCall);
        img_profile_user = view.findViewById(R.id.showProfilePic);
        txtName = view.findViewById(R.id.txtUname);
        back_btn=view.findViewById(R.id.back_btn);
        txtEmailUser = view.findViewById(R.id.txtEmailUser);
//        txtPassWord = view.findViewById(R.id.txtPassWord);
        txtMo = view.findViewById(R.id.txtMo);
        txtGend = view.findViewById(R.id.txtGend);
        txtCit = view.findViewById(R.id.txtCit);
        txtDll = view.findViewById(R.id.txtDll);
        Payent = view.findViewById(R.id.Payent);
        historybtn = view.findViewById(R.id.historybtn);

        sharedPreferences =getContext().getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");

back_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(requireActivity(), MainFragment.class));
    }
});
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    if (isAdded()) {
                        Glide.with(requireContext())
                                .load(imageUrl)
                                .into(img_profile_user);
                    }


                    name = userSnapshot.child("name").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);

//                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                    city = userSnapshot.child("city").getValue(String.class);
                    dl = userSnapshot.child("aadhaar_card_number").getValue(String.class);
                    gender = userSnapshot.child("gender").getValue(String.class);
                }

                txtName.setText(name);
                txtEmailUser.setText(email);
//                txtPassWord.setText(pass);
                txtMo.setText(mob);
                txtGend.setText(gender);
                txtCit.setText(city);
                txtDll.setText(dl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), EditProfile.class));
            }
        });

        settingCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Settings.class));
            }
        });

        Payent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Tourism_place_show.class));
            }
        });

        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Tourism_Data_add.class));
            }
        });
        return view;
    }
}
