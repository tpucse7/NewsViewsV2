package com.ghorami.rongpencill.newsviews;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchResult extends AppCompatActivity {

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);

        initInstancesDrawer();

        Intent i = getIntent();

        url = i.getStringExtra("query");

        WebView myWebView = (WebView)findViewById(R.id.wvTest);
        myWebView.getSettings().setJavaScriptEnabled(true);

        String escapedQuery = null;
        try {
            escapedQuery = URLEncoder.encode(url, "UTF-8");
            Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // startActivity(intent);
            myWebView.loadUrl(String.valueOf(uri));
            WebSettings WebSettings = myWebView.getSettings();
            // final Activity Activity = this;
            myWebView.setWebViewClient(new WebViewClient(){
                @SuppressWarnings("deprecation")
                @Override
                public void  onReceivedError(WebView view, int errorCode,String description,String failingUrl){
                    Toast.makeText(SearchResult.this,description,Toast.LENGTH_SHORT).show();
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr){
                    onReceivedError(view, rerr.getErrorCode(),rerr.getDescription().toString(),req.getUrl().toString());
                }

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }


    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

        //  proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));


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


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


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
}
