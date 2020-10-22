package com.example.groupprojectcmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //this code is to show my intended first page to the user when the app in turn on
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new com.example.groupprojectcmi.homeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);





        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.groupprojectcmi.homeFragment()).commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.groupprojectcmi.profileFragment()).commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                break;
            case R.id.nav_payment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.groupprojectcmi.paymentFragment()).commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                break;
            case R.id.nav_booking:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.groupprojectcmi.mybookingFragment()).commit();
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut(); ///Logout from the firebase
                startActivity(new Intent(getApplicationContext(), com.example.groupprojectcmi.loginUI.class));
        }
        return true;
    }




    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}