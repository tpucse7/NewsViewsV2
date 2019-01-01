package com.ghorami.rongpencill.newsviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ListView listview;
    private ArrayList<AndroidVersiona> tours;
    private DataAdapterPicture adapter;
    final Context context = this;
    private Button mbutton;
    int rent = 0;
    EditText e1, e2, e3, mTextEditer;
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    private final static String TAG = Gallery.class.getSimpleName();

    private final static String url = "http://newsapi.org/v2/everything?q=bitcoin&from=2018-12-31&sortBy=publishedAt&apiKey=e7a32d1e679c4529b5ac8ee20e7fa47e";


    // String urlApiTer = "https://newsapi.org/v2/everything?q=bitcoin&from=2018-12-31&sortBy=publishedAt&apiKey=e7a32d1e679c4529b5ac8ee20e7fa47e";
   // private final static String url = "http://sopnobari.com/api/newsApi.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        setListViewAdapter();
        getDataFromInternet();
        instantSDrawer();
    }


    private void getDataFromInternet() {
        new GetJsonFromUrlTask(this, url).execute();
    }

    private void setListViewAdapter() {
        tours = new ArrayList<AndroidVersiona>();

        adapter = new DataAdapterPicture(getApplicationContext(),tours);
        // adapter = new DataAdapter(data);
        recyclerView.setAdapter(adapter);

    }

    public void parseJsonResponse(String result) {
        Log.i(TAG, result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(json.getString("articles"));
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                AndroidVersiona tourk = new AndroidVersiona();
                tourk.setAuthor(jObject.getString("author"));

                tourk.setTitle(jObject.getString("title"));
                tourk.setDescription(jObject.getString("description"));


                tourk.setUrl(jObject.getString("url"));

                tourk.setUrlToImage(jObject.getString("urlToImage"));
                tourk.setPublishedAt(jObject.getString("publishedAt"));
                tourk.setContent(jObject.getString("content"));


                tours.add(tourk);
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class GetJsonFromUrlTask extends AsyncTask<Void, Void, String> {

        private Activity activity;
        private String url;
        private ProgressDialog dialog;
        private final String TAG = Gallery.class.getSimpleName();

        public GetJsonFromUrlTask(Activity activity, String url) {
            super();
            this.activity = activity;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progress dialog
            dialog = new ProgressDialog(activity);
            // Set progress dialog title
            //dialog.setTitle("Please Wait......... ");
            // Set progress dialog message
            dialog.setMessage("Please Wait...");
            dialog.setIndeterminate(false);
            // Show progress dialog
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            // call load JSON from url method
            return loadJSON(this.url).toString();
        }

        @Override
        protected void onPostExecute(String result) {
            ((Gallery) activity).parseJsonResponse(result);
            dialog.dismiss();
            Log.i(TAG, result);
        }

        public JSONObject loadJSON(String url) {
            // Creating JSON Parser instance
            Gallery.GetJsonFromUrlTask.JSONGetter jParser = new Gallery.GetJsonFromUrlTask.JSONGetter();

            // getting JSON string from URL
            JSONObject json = jParser.getJSONFromUrl(url);

            return json;
        }

        private class JSONGetter {

            private InputStream is = null;
            private JSONObject jObj = null;
            private String json = "";

            // constructor
            public JSONGetter() {

            }



            public JSONObject getJSONFromUrl(String url) {

                // Making HTTP request
                try {

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                  //  nameValuePairs.add(new BasicNameValuePair("t_spot", v1));
                   // nameValuePairs.add(new BasicNameValuePair("Lang", v2));
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpPost = new HttpGet(url);
                  //  httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    Log.e("log_tag", "connection success ");
                    //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),
                            8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(json);
                    Log.e("log_tag", "Parsing success ");

                    //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                // return JSON String
                return jObj;

            }
        }
    }

    /**
    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

            proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


  //      CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }

     **/

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
            Intent mainIntent = new Intent(getApplicationContext(), Gallery.class);
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
            Intent mainIntent = new Intent(getApplicationContext(), Gallery.class);
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
            // finish();
            //System.exit(0);

        } else if (id == R.id.share) {
            String v4=("http://rongpencill.com/NewsViews").toString();
            String v1=("#NewsViews").toString();
            String v2=("An app of Diffrent type news").toString();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = v4+"\n"+v1+"\n"+v2+"\n";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, v4+"\n"+v1+"\n"+v2+"\n");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

        } else if (id == R.id.send) {

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
                    .appendQueryParameter("subject", String.valueOf(R.string.version))
                    .appendQueryParameter("body", "Please leave your feedback here.\nPlease do not edit the information below:\n"+v2)
                    .build();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
