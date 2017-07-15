package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.User;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton fabMember, fabNote, fabSchedule;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView tvUserName;
    ImageView ivUserImage, ivHeaderBG;
    FirebaseAuth firebaseAuth;
    User currUser;
    View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fillViewPagerAndTab();
        onFABClicked();
        onNavigationClicked();
        loadCurrentUserData();
    }

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.ViewPagerMain);
        tabLayout = (TabLayout) findViewById(R.id.TabLayoutMain);
        fabMember = (FloatingActionButton) findViewById(R.id.fab_member);
        fabNote = (FloatingActionButton) findViewById(R.id.fab_note);
        fabSchedule = (FloatingActionButton) findViewById(R.id.fab_schedule);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.ToolbarMain);
        navHeader = navigationView.getHeaderView(0);
        tvUserName = (TextView) navHeader.findViewById(R.id.user_name);
        ivUserImage = (ImageView) navHeader.findViewById(R.id.user_img);
        ivHeaderBG = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void loadCurrentUserData(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("Users");
        mDB.orderByChild("email").equalTo(firebaseUser.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    currUser = child.getValue(User.class);
                }
                loadNavHeader(currUser.getfName() + " " + currUser.getlName(), currUser.getPpUrl());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fillViewPagerAndTab() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Timeline", new TimelineFragment());
        viewPagerAdapter.addFragment("Member", new MemberFragment());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadNavHeader(String name, String userImgUrl){
        tvUserName.setText(name);

        Glide.with(this).load(R.drawable.nav_header_bg)
                .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivHeaderBG);
        Glide.with(this).load(userImgUrl)
                .crossFade().thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivUserImage);
    }

    private void onNavigationClicked(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        //apa
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        //apa
                        break;
                    case R.id.nav_logout:
                        //apa
                        break;
                    case R.id.nav_about_us:
                        //apa
                        Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void onFABClicked(){
        fabMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), CreateJoinGroupActivity.class);
                startActivity(intent);
            }
        });
        fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), AddNotesActivity.class); //belum di buat
                startActivity(intent);
            }
        });
        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), AddScheduleActivity.class); //belum di buat
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
