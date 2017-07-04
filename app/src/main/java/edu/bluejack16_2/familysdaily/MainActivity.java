package edu.bluejack16_2.familysdaily;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fillViewPagerAndTab();
    }

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.ViewPagerMain);
        tabLayout = (TabLayout) findViewById(R.id.TabLayoutMain);
    }

    private void fillViewPagerAndTab(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Timeline", new TimelineFragment());
        viewPagerAdapter.addFragment("Member", new MemberFragment());
        viewPager.setAdapter(viewPagerAdapter);;
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
