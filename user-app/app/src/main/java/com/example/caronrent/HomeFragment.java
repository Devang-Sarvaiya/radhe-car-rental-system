package com.example.caronrent;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.caronrent.Adapter.ItemAdapter;
import com.example.caronrent.Add_car.C_ItemAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, isUnAv;

    private DatabaseReference usersRef;
    TextView txtUname, txt_unav;
    ImageView img_Profile, isVeri;

    Button button2;
    private C_ItemAdapter adapter;
    String dateString = "24/02/2024";
    String dateString1 = "26/02/2024";
    Date date, date1;
    String dd1, dd2, dd, mob;
    String eem;



    private ViewPager2 viewPager;
    private ImageAdapter imageAdapter;
    private Handler handler;
    private Runnable runnable;

    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private RecyclerView recyclerViewPopular;
    private DatabaseReference databaseReference;
    final private DatabaseReference databaseReference_Car = FirebaseDatabase.getInstance().getReference("Admin").child("date");

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI elements
//        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//        emailShare = sharedPreferences.getString(KEY_NAME, null);


        sharedPreferences = getContext().getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD" + eem);
        txtUname = view.findViewById(R.id.txtUname);
        txt_unav = view.findViewById(R.id.txt_unav);
        img_Profile = view.findViewById(R.id.img_Profile);
        isVeri = view.findViewById(R.id.isVerified);
        recyclerViewPopular = view.findViewById(R.id.viewPopular);



        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.banner3);
        imageList.add(R.drawable.banner2);
        imageList.add(R.drawable.banner5);

        viewPager = view.findViewById(R.id.viewPagerImages);
        imageAdapter = new ImageAdapter(requireActivity(), imageList);
        viewPager.setAdapter(imageAdapter);

        // Initialize Handler
        handler = new Handler(Looper.getMainLooper());

        // Start automatic sliding
        startAutoSlide();

//        button2 = view.findViewById(R.id.button2);


//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin");
//                db.removeValue();
//
//            }
//        });

        // Set up click listener for the profile image
        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Profile_Ui.class);
                startActivity(intent);
            }
        });

        recyclerViewPopular.setHasFixedSize(true);

        if (isAdded()) {
            recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        }

//        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
//


        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isAdded() && getActivity() != null) {

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                        Glide.with(requireActivity()).load(imageUrl).into(img_Profile);
                        name = userSnapshot.child("name").getValue(String.class);
                        mob = userSnapshot.child("mobile").getValue(String.class);
                        isVer = userSnapshot.child("isVerified").getValue(String.class);
                        isUnAv = userSnapshot.child("isUnAvailable").getValue(String.class);
                    }

//                if (isVer.equals("true")) {
//                    isVeri.setVisibility(View.VISIBLE);
//                } else {
//                    isVeri.setVisibility(View.GONE);
//                }

                    if (isUnAv.equals("true")) {
                        txt_unav.setText("Unavailable");
                    } else {
                        txt_unav.setText("Available");
                    }

                    txt_unav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(requireActivity(), statusChanged.class));
                        }
                    });

//                txt_unav.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (isUnAv.equals("false")) {
//
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//                            builder.setTitle("Status Alert")
//                                    .setMessage("Are you want to set your status as Unavailable ?")
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
////                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("isUnAvailable");
//                                            db2.setValue("true");
//                                            txt_unav.setText("Unavailable");
//
//                                            Toast.makeText(requireActivity(), "Your status Successfully updated", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                            builder.setCancelable(false);
//
//                            // Create and show the dialog
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//
//
//                        } else {
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//                            builder.setTitle("Status Alert")
//                                    .setMessage("Are you want to set your status as Available ?")
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
////                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("isUnAvailable");
//                                            db2.setValue("false");
//                                            txt_unav.setText("Available");
//                                            Toast.makeText(requireActivity(), "Your status Successfully updated", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                            builder.setCancelable(false);
//
//                            // Create and show the dialog
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//
//                        }
//                    }
//
//
//                });


                    databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ArrayList<ImageModel1> dataList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                            for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                                ImageModel1 dataClass = dataSnapshot4.getValue(ImageModel1.class);
                                                dataList.add(dataClass);
                                            }
                                        }
                                    }
                                }
                            }
                            adapter = new C_ItemAdapter(getContext(), dataList);
                            recyclerViewPopular.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error, if any.
                        }
                    });


                    txtUname.setText(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        return view;
    }
    private void startAutoSlide() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                // Calculate the next item to display
                int nextItem = viewPager.getCurrentItem() + 1;
                if (nextItem >= imageAdapter.getItemCount()) {
                    nextItem = 0; // Wrap around if reached the last item
                }

                // Set the next item in ViewPager
                viewPager.setCurrentItem(nextItem, true);

                // Schedule the next slide after a delay
                handler.postDelayed(this, 3000); // Change delay as needed (here 3000ms = 3 seconds)
            }
        };

        // Schedule the first slide
        handler.postDelayed(runnable, 3000); // Change delay as needed (here 3000ms = 3 seconds)
    }

    // Stop automatic sliding when the activity is paused
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private Context context;
        private List<Integer> imageList;

        public ImageAdapter(Context context, List<Integer> imageList) {
            this.context = context;
            this.imageList = imageList;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image1, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            int imageId = imageList.get(position);
            holder.imageView.setImageResource(imageId);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public static class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}



