package com.colossussoftware.titanwalking.titanwall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.colossussoftware.titanwalking.titanwall.adapters.WallpapersAdapter;
import com.colossussoftware.titanwalking.titanwall.model.Wallpaper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WallpapersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WallpapersAdapter adapter;
    private List<Wallpaper> wallpaperList;

    private DatabaseReference dbRefWallpapers;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/UbuntuCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("category");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);

        wallpaperList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WallpapersAdapter(this, wallpaperList);
        recyclerView.setAdapter(adapter);
        dbRefWallpapers = FirebaseDatabase.getInstance().getReference("images").child(categoryName);

        dbRefWallpapers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Wallpaper wp = ds.getValue(Wallpaper.class);
                        wallpaperList.add(wp);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
