package com.example.snl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String navHeaderText;
    TextView navHeaderTextView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;

    SharedPreferences.Editor editor;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sharedPreferences = getApplicationContext().getSharedPreferences("login", 0);
        navHeaderText = sharedPreferences.getString("memberEmail", null);

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);

        setTitle("Anasayfa");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new HomeFragment());
        fragmentTransaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        View headerView = navigationView.getHeaderView(0);
        navHeaderTextView = (TextView) headerView.findViewById(R.id.lblHeaderText);
        navHeaderTextView.setText(navHeaderText);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        // menuden tıklama yapıldığında
        int id = item.getItemId();

        if (id == R.id.nav_hompage) {
            setTitle("Anasayfa");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_account) {
            setTitle("Hesap Bilgilerim");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new AccountFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_ilanlarim) {
            setTitle("Kişisel İlanlarım");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new IlanlarimFragment());
            fragmentTransaction.commit();
        }else if (id==R.id.nav_message){
            setTitle("Mesajlarım");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new KisiMesajlariFragment());
            fragmentTransaction.commit();
        }else if (id == R.id.nav_ilanver) {
            setTitle("İlan Ver");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new İlanVerFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_favorites) {
            setTitle("Favori İlanlar");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FavoriIlanlarimFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_allilan) {
            setTitle("Tüm İlanlar");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new TumIlanlarFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_logout) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(HomePage.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

}