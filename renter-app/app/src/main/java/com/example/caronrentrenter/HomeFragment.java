package com.example.caronrentrenter;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Adapter.CatAdapter;
import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.Adapter.RenterAdapter;
import com.example.caronrentrenter.Live_location.Location_info;
import com.example.caronrentrenter.Safety_feature.Safety_feature_show;
import com.example.caronrentrenter.Tourism.Tourism_place_show;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout cat_sports, cat_wedding, cat_tour, cat_all;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
     String emailShare, name, mob,eem;
    private ViewPager2 viewPager;
    private ImageAdapter imageAdapter;
    private Handler handler;
    private Runnable runnable;
    BottomNavigationView bottomNavigationView;
    private ImageView imgProfile,Location,imgtata,imgmahindra,imgsuzuki,imgskoda,imgvols,Safety;
    private TextView txtName;

    private ItemAdapter adapter;
    private RenterAdapter adapter1;
    private CatAdapter adapter2;
    SearchView editTextText;

    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private final DatabaseReference databaseReference_High1 = FirebaseDatabase.getInstance().getReference("Users");
    private final DatabaseReference databaseReference_High2 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private DatabaseReference usersRef;

    private RecyclerView recyclerViewPopular, recyclerViewNew;
            //viewCat;

    private FloatingActionButton fab;
    private ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayout1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        cat_sports = view.findViewById(R.id.cat_sports);
//        cat_wedding = view.findViewById(R.id.cat_wedding);
//        cat_tour = view.findViewById(R.id.cat_tour);
//        cat_all = view.findViewById(R.id.cat_all);

        sharedPreferences =getContext().getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");
        imgProfile = view.findViewById(R.id.imagProfile);
        imgtata = view.findViewById(R.id.imgtata);
        imgmahindra = view.findViewById(R.id.imgmahindra);
        imgsuzuki = view.findViewById(R.id.imgsuzuki);
        imgskoda = view.findViewById(R.id.imgskoda);
        imgvols = view.findViewById(R.id.imgvolks);
        Location = view.findViewById(R.id.Location);
        Safety = view.findViewById(R.id.Safety);
        editTextText = view.findViewById(R.id.editTextText);

//        bottomNavigationView.findViewById(R.id.bottomNavView);

       // Location = view.findViewById(R.id.Location);
        txtName = view.findViewById(R.id.txtName);


        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.banner3);
        imageList.add(R.drawable.banner1);
        imageList.add(R.drawable.banner5);

        viewPager = view.findViewById(R.id.viewPagerImages);
        imageAdapter = new ImageAdapter(requireActivity(), imageList);
        viewPager.setAdapter(imageAdapter);

        // Initialize Handler
        handler = new Handler(Looper.getMainLooper());

        // Start automatic sliding
        startAutoSlide();


        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isAdded() && getActivity() != null) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                        if (imageUrl != null) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .into(imgProfile);
                        }

                        name = userSnapshot.child("name").getValue(String.class);
                        mob = userSnapshot.child("mobile").getValue(String.class);
                    }
                    Location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(requireActivity(), Location_info.class);
                            Intent intent = new Intent(requireActivity(), Tourism_place_show.class);
//                            intent.putExtra("mob",mob);
                            startActivity(intent);
                        }
                    });


                    Safety.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(requireActivity(), Safety_feature_show.class));
                        }
                    });

                    txtName.setText(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        editTextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompassFragment compassFragment = new CompassFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, compassFragment);
//                bottomNavigationView.setSelectedItemId(R.id.navCompass);
                transaction.addToBackStack(null);  // Optional: Add this transaction to the back stack
                transaction.commit();
            }
        });

//        Location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireActivity(), bookingHistory.class);
////                Toast.makeText(requireActivity(), mob, Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//        });

        imgProfile = view.findViewById(R.id.imagProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Profile_Ui.class);
                startActivity(intent);
            }
        });

        recyclerViewPopular = view.findViewById(R.id.viewPopular);
//        recyclerViewNew = view.findViewById(R.id.viewNew);
      //  viewCat = view.findViewById(R.id.viewCat);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

//        recyclerViewNew.setHasFixedSize(true);
//        recyclerViewNew.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

//        viewCat.setHasFixedSize(true);
//        viewCat.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
//
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
//        shimmerFrameLayout1 = view.findViewById(R.id.shimmer_1);


        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<DataClass> dataList = new ArrayList<>();
                ArrayList<DataClass> dataList = new ArrayList<>();

                shimmerFrameLayout.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    DataClass dataClass = dataSnapshot4.getValue(DataClass.class);
                                    dataList.add(dataClass);
                                }
                            }
                        }
                    }
                }

                adapter = new ItemAdapter(getContext(), dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerViewPopular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        databaseReference_High1.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<ReadWriteUserDetails> dataList1 = new ArrayList<>();
//
//                shimmerFrameLayout1.startShimmer();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    if (!dataSnapshot.getKey().equals(mob)) {
//                        ReadWriteUserDetails dataClass = dataSnapshot.getValue(ReadWriteUserDetails.class);
//                        databaseReference_High1.child(dataSnapshot.getKey()).setValue(dataClass);
//                        dataList1.add(dataClass);
//                    }
//                }
//
//                adapter1 = new RenterAdapter(getContext(), dataList1);
//                recyclerViewNew.setAdapter(adapter1);
//                adapter1.notifyDataSetChanged();
//
//                shimmerFrameLayout1.stopShimmer();
//                shimmerFrameLayout1.setVisibility(View.GONE);
//                recyclerViewNew.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//

        databaseReference_High2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataClass1> dataList3 = new ArrayList<>();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
//                                    if (!dataSnapshot.getKey().equals("mob")) {
                                    DataClass1 dataClass = dataSnapshot4.getValue(DataClass1.class);
                                    dataList3.add(dataClass);
                                    }
//                                }
                            }
                        }
                    }
                }

                adapter2 = new CatAdapter(getContext(), dataList3);
//                viewCat.setAdapter(adapter2);
//                viewCat.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//
//        fab = view.findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireActivity(), Car_item_add.class);
//                startActivity(intent);
//            }
//        });

        imgtata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Tata";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });
        imgmahindra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Mahindra";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });
        imgsuzuki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Maruti";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });
        imgskoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Skoda";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });
        imgvols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Car_Menu.class);
                String path = "Bmw";
                intent.putExtra("car", path);
                startActivity(intent);
            }
        });


//
//        cat_wedding.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireActivity(), Car_Menu.class);
//                String path = "Wedding";
//                intent.putExtra("car", path);
//                startActivity(intent);
//            }
//        });
//
//        cat_tour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireActivity(), Car_Menu.class);
//                String path = "Tour";
//                intent.putExtra("car", path);
//                startActivity(intent);
//            }
//        });
//
//        cat_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireActivity(), Car_Menu.class);
//                String path = "General";
//                intent.putExtra("car", path);
//                startActivity(intent);
//            }
//        });
//
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

