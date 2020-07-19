package com.example.groundhopping_mobile;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.groundhopping_mobile.data.LoginDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.login,R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_stadiums, R.id.nav_contests, R.id.nav_teams, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("ON OPTION ITEM SELECTED " + item.getSubMenu());
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_logout:
                System.out.println("LOGOUT!!!!!!!!!!");
                LoginDataSource.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void afterLoginAppBar(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem stadium = menu.findItem(R.id.nav_slideshow);
        MenuItem Contest = menu.findItem(R.id.nav_gallery);
        logout.setVisible(true);
        stadium.setVisible(true);
        Contest.setVisible(true);
        login.setVisible(false);
    }

    public void logout() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem stadium = menu.findItem(R.id.nav_slideshow);
        MenuItem Contest = menu.findItem(R.id.nav_gallery);
        logout.setVisible(false);
        stadium.setVisible(false);
        Contest.setVisible(false);
        login.setVisible(true);
    }
}