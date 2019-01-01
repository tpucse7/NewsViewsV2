package com.ghorami.rongpencill.newsviews;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetails extends AppCompatActivity {

       private String author, title, description, url, urlToImage, publishedAt, content, name, id;
        private String source, articles, totalResults, status;

    TextView tvHeadline, tvDate, tvUrl,tvContent, tvDescription, tvAuthor;
    ImageView ivNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initInstancesDrawer();
        ivNews = (ImageView)findViewById(R.id.ivPicture);
        tvHeadline = (TextView)findViewById(R.id.tvHeadline);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvUrl = (TextView)findViewById(R.id.tvUrl);
        tvContent = (TextView)findViewById(R.id.tvContent);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvAuthor = (TextView)findViewById(R.id.tvAuthor);


        Intent i = getIntent();
        author = i.getStringExtra("author");
        title = i.getStringExtra("title");
        description = i.getStringExtra("description");
        url = i.getStringExtra("url");
        urlToImage = i.getStringExtra("urlToImage");
        publishedAt = i.getStringExtra("publishedAt");
        content = i.getStringExtra("content");
       // author = i.getStringExtra("publishedAt");
if(urlToImage!=""){
    Picasso.with(NewsDetails.this).load(urlToImage).into(ivNews);
    ivNews.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            seeFullPic();
        }
    });
}

        tvHeadline.setText(title);
        tvDate.setText(publishedAt);
        tvContent.setText(content);
        tvUrl.setText(url);
        tvDescription.setText(description);
        tvAuthor.setText(author);
tvUrl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(NewsDetails.this, NewsWeb.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
});
    }

    private void seeFullPic() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_pic_dialogue, null);
        TouchImageView prImage = (TouchImageView) dialogView.findViewById(R.id.my_image);

        if(urlToImage.equals ("")){
            prImage.setImageDrawable(getResources().getDrawable(R.drawable.news_logo));
        } else {
            Picasso.with(this).load(urlToImage).into(prImage);
        }

        builder.setView(dialogView)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

       // proPic.setImageDrawable(getResources().getDrawable(R.drawable.user));


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
