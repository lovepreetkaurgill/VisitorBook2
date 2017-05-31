package auribises.com.visitorbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import auribises.com.visitorbook.Activites.Admin1Activity;
import auribises.com.visitorbook.Activites.AdminappointmentActivity;
import auribises.com.visitorbook.Activites.RegisterAdminActivity;
import auribises.com.visitorbook.Activites.RegisterGuardActivity;
import auribises.com.visitorbook.Activites.HomeActivity;
import auribises.com.visitorbook.Activites.Teacher1Activity;
import auribises.com.visitorbook.Activites.RegisterTeacherActivity;
import auribises.com.visitorbook.Activites.Vehicle1Activity;
import auribises.com.visitorbook.Activites.Visitor1Activity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }
      return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register_admin) {
            Intent i = new Intent(MainActivity.this, RegisterAdminActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_register_teacher) {
            Intent i= new Intent(MainActivity.this, RegisterTeacherActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_register_guard) {
            Intent i = new Intent(MainActivity.this, RegisterGuardActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_guard) {
            Intent i= new Intent(MainActivity.this, Visitor1Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_teacher) {
            Intent i= new Intent(MainActivity.this,Teacher1Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_vehicle) {
            Intent i = new Intent(MainActivity.this, Vehicle1Activity.class);
            startActivity(i);

        } else  if (id == R.id.nav_admin1) {
            Intent i= new Intent(MainActivity.this, Admin1Activity.class);
            startActivity(i);

       } else  if (id == R.id.nav_admin) {
            Intent i= new Intent(MainActivity.this, AdminappointmentActivity.class);
            startActivity(i);

       } else if (id == R.id.nav_logout) {
            Intent i= new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
