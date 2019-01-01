package com.ghorami.rongpencill.newsviews;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvFeedback, tvShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvFeedback = (TextView)findViewById(R.id.tvfeedback);
        tvShare = (TextView)findViewById(R.id.tvshare);

        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appShare();
            }
        });

        instantSDrawer();

    }

    private void instantSDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

     //   proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }



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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //noinspection RestrictedApi
                searchViewAndroidActionBar.clearFocus();
                Intent intent = new Intent(getApplicationContext(),
                        SearchResult.class);

                intent.putExtra("query", query);
                // intent.putExtra("Lang", "EN");
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent mainIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(mainIntent);
            return true;
        }
        if (id == R.id.action_exit) {
            finishAffinity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent mainIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(mainIntent);
            // Handle the camera action
        } else if (id == R.id.about) {
            Intent mainIntent = new Intent(getApplicationContext(), About.class);
           startActivity(mainIntent);

        } else if (id == R.id.gallery) {
            Intent mainIntent = new Intent(getApplicationContext(), Gallery.class);
            startActivity(mainIntent);

        } else if (id == R.id.exit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }

        } else if (id == R.id.share) {
            appShare();
        } else if (id == R.id.send) {

            sendFeedback();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void appShare() {

        String v4=("http://rongpencill.com/NewsViews").toString();
        String v1=("#NewsViews").toString();
        String v2=("An app of Diffrent type news").toString();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, v4+"\n"+v1+"\n"+v2+"\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

    }

    private void sendFeedback() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        Log.e("MyActivity", "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease
        );
        String v2=("MyActivity"+ "manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease).toString();

        String[] TO = {"md.abutalha67@gmail.com"};
        Uri uri = Uri.parse("mailto:md.abutalha67@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", String.valueOf("News Views App"))
                .appendQueryParameter("body", "Please leave your feedback here.\nPlease do not edit the information below:\n"+v2)
                .build();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
