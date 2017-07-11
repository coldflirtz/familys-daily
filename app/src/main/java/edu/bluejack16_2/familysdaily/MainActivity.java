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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton fabMember, fabNote, fabSchedule;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fillViewPagerAndTab();
        onFABClicked();
        onNavigationClicked();
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void fillViewPagerAndTab() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Timeline", new TimelineFragment());
        viewPagerAdapter.addFragment("Member", new MemberFragment());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //belum di buat
                startActivity(intent);
            }
        });
        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //belum di buat
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
