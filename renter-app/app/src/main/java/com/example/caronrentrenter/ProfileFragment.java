package com.example.caronrentrenter;

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
import com.example.caronrentrenter.BookHistory.bookingHistory;
import com.example.caronrentrenter.Verification.verify_your_self;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    Button editButton;
    LinearLayout settingCall, historybtn, Verification;
    ImageView img_profile_user, back_btn;
    String eem;
    SharedPreferences sharedPreferences;
    TextView txtName, txtEmailUser, txtPassWord, txtMo, txtGend, txtCit, txtDll;
    String emailShare, name, email, pass, mob, gender, city, dl;

    private DatabaseReference usersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        historybtn = view.findViewById(R.id.historybtn);
        editButton = view.findViewById(R.id.editButton);
        Verification = view.findViewById(R.id.Verification);
        settingCall = view.findViewById(R.id.settingCall);
        img_profile_user = view.findViewById(R.id.showProfilePic);
        txtName = view.findViewById(R.id.txtUname);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, requireActivity().MODE_PRIVATE);
        txtEmailUser = view.findViewById(R.id.txtEmailUser);
        //txtPassWord = view.findViewById(R.id.txtPassWord);
        txtMo = view.findViewById(R.id.txtMo);
        txtGend = view.findViewById(R.id.txtGend);
        txtCit = view.findViewById(R.id.txtCit);
        txtDll = view.findViewById(R.id.txtDll);
        back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireActivity(), MainFragment.class);
                startActivity(i);
            }
        });

        sharedPreferences = getContext().getSharedPreferences("email_1", MODE_PRIVATE);

        eem = sharedPreferences.getString("email", "AAA");

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isAdded()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                        if (imageUrl != null) {
                            Glide.with(requireActivity()).load(imageUrl).into(img_profile_user);
                        }

                        name = userSnapshot.child("name").getValue(String.class);
                        email = userSnapshot.child("email").getValue(String.class);

                        pass = userSnapshot.child("pass").getValue(String.class);
                        mob = userSnapshot.child("mobile").getValue(String.class);
                        city = userSnapshot.child("city").getValue(String.class);
                        dl = userSnapshot.child("dll").getValue(String.class);
                        gender = userSnapshot.child("gender").getValue(String.class);
                    }

                    txtName.setText(name);
                    txtEmailUser.setText(email);
                    //txtPassWord.setText(pass);
                    txtMo.setText(mob);
                    txtGend.setText(gender);
                    txtCit.setText(city);
                    txtDll.setText(dl);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), EditProfile.class));
            }
        });
//        editbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(requireActivity(), EditProfile.class));
//            }
//        });
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), bookingHistory.class));
            }
        });

        settingCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Settings.class));
            }
        });


        Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Compass.class));
            }
        });
    }
}
