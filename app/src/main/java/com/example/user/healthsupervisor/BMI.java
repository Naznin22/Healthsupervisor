package com.example.user.healthsupervisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        final Button button_calc = (Button) findViewById(R.id.button_calc);
        final EditText field_weight = (EditText) findViewById(R.id.editWeight);
        final EditText field_height = (EditText) findViewById(R.id.editHeight);
        final TextView view_result = (TextView) findViewById(R.id.view_result);
        final TextView view_msg = (TextView) findViewById(R.id.view_msg);

        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double weight;
                double height;
                double bmi;
                String msg = "";

                if(field_height.getText().toString().equals("") || field_weight.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"No Valid Values!", Toast.LENGTH_SHORT);
                }else {
                    weight = Double.parseDouble(field_weight.getText().toString());
                    height = Double.parseDouble(field_height.getText().toString());

                    bmi = height * height;
                    bmi = weight / bmi;

                    view_result.setText(String.valueOf(bmi));
                    if (bmi < 18.5) {
                        msg = "Underweight :(";
                    } else if (bmi > 18.5 && bmi < 25) {
                        msg = "Normal :)";
                    } else {
                        msg = "Overweight :(";
                    }
                    view_msg.setText(msg);
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        getMenuInflater().inflate(R.menu.bmi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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


        if (id == R.id.nav_camera) {
            Intent c= new Intent(BMI.this,Mymeds.class);
            startActivity(c);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent g = new Intent(BMI.this,HealthChart.class);
            startActivity(g);

        } else if (id == R.id.nav_slideshow) {
            Intent s = new Intent(BMI.this,Exer.class);
            startActivity(s);

        } else if (id == R.id.nav_manage) {
            Intent m = new Intent(BMI.this,Contacts.class);
            startActivity(m);

        }
        else if (id == R.id.nav_about) {
            Intent a = new Intent(BMI.this,About.class);
            startActivity(a);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
