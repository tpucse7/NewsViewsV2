package com.ghorami.rongpencill.newsviews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class DataAdapterNews extends RecyclerView.Adapter<DataAdapterNews.ViewHolder>{
    private ArrayList<AndroidVersiona> android;
    private Context context;
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;
    public String author, title, description, url, urlToImage, publishedAt, content, name, id;
    public String source, articles, totalResults, status;
public String spotq;

    Pattern p = Pattern.compile("(\\d+)\\s+(.*?)s? ago");

    Map<String, Integer> fields = new HashMap<String, Integer>() {{
        put("second", Calendar.SECOND);
        put("minute", Calendar.MINUTE);
        put("hour",   Calendar.HOUR);
        put("day",    Calendar.DATE);
        put("week",   Calendar.WEEK_OF_YEAR);
        put("month",  Calendar.MONTH);
        put("year",   Calendar.YEAR);
    }};

    String[] tests = {
            "3 days ago",
            "1 minute ago",
            "2 years ago"
    };



    public DataAdapterNews(Context context, ArrayList<AndroidVersiona> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
      //  Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/Segan-Light.ttf");

        // final Place place = new PlaceData().placeList().get(position);
        viewHolder.tv_name.setText((android.get(i).getTitle()));
       // viewHolder.tv_name.setTypeface(custom_font);
        viewHolder.tv_version.setText((android.get(i).getDescription()));
       // viewHolder.tv_version.setTypeface(custom_font);

        if(android.get(i).getAuthor()==""||android.get(i).getAuthor()=="null"){
            viewHolder.tvCat.setVisibility(View.GONE);
        }else{
            viewHolder.tvCat.setText((android.get(i).getAuthor()));
        }
       // viewHolder.tvCat.setTypeface(custom_font);
        viewHolder.timestamp.setText((android.get(i).getPublishedAt()));

        if(android.get(i).getUrlToImage().equals ("")){
            viewHolder.img_android.setImageDrawable(context.getResources().getDrawable(R.drawable.news_logo));
        } else {
            Picasso.with(context).load(android.get(i).getUrlToImage()).into(viewHolder.img_android);
        }
        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("author", android.get(i).getAuthor());
                intent.putExtra("title", android.get(i).getTitle());
                intent.putExtra("description", android.get(i).getDescription());
                intent.putExtra("url", android.get(i).getUrl());
                intent.putExtra("urlToImage", android.get(i).getUrlToImage());
                intent.putExtra("publishedAt", android.get(i).getPublishedAt());
                intent.putExtra("content", android.get(i).getContent());
                context.startActivity(intent);

            }
        });
        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, android.get(i).getAuthor(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("author", android.get(i).getAuthor());
                intent.putExtra("title", android.get(i).getTitle());
                intent.putExtra("description", android.get(i).getDescription());
                intent.putExtra("url", android.get(i).getUrl());
                intent.putExtra("urlToImage", android.get(i).getUrlToImage());
                intent.putExtra("publishedAt", android.get(i).getPublishedAt());
                intent.putExtra("content", android.get(i).getContent());
                context.startActivity(intent);            }
        });

     //   private String author, title, description, url, urlToImage, publishedAt, content, name, id;
   //     private String source, articles, totalResults, status;

        author=android.get(i).getAuthor().toString();
        title=android.get(i).getTitle().toString();
        description=android.get(i).getDescription().toString();
        url=android.get(i).getUrl().toString();
        urlToImage=android.get(i).getUrlToImage().toString();
        publishedAt=android.get(i).getPublishedAt().toString();
        content=android.get(i).getContent().toString();
       // name=android.get(i).getName().toString();
     //   id=android.get(i).getId().toString();

    }



    @Override
    public int getItemCount() {

        return android.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tvCat,tv_api_level, tid, timestamp, tname, times;

        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            if(tv_name !=null){
                tv_name.setVisibility(View.VISIBLE);
            } else {
                tv_name.setVisibility(View.GONE);
                Toast toast = Toast.makeText(context, "Sorry No Data Found",
                        Toast.LENGTH_LONG);
                toast.show();
            }

            tname = (TextView)view.findViewById(R.id.tname);
            if(tname !=null){
                tname.setVisibility(View.GONE);
            } else {
                tname.setVisibility(View.GONE);

            }

            tid = (TextView)view.findViewById(R.id.tid);
            if(tid !=null){
                tid.setVisibility(View.GONE);
            } else {
                tid.setVisibility(View.GONE);

            }

          //  times = (TextView) view.findViewById(R.id.times);

            timestamp = (TextView) view.findViewById(R.id.timestamp);
            if(timestamp !=null){
                timestamp.setVisibility(View.VISIBLE);
            } else {
                timestamp.setVisibility(View.GONE);

            }


            tv_version = (TextView)view.findViewById(R.id.tv_version);
            if(tv_version !=null){
                tv_version.setVisibility(View.VISIBLE);
            } else {
                tv_version.setVisibility(View.GONE);

            }
            tvCat = (TextView)view.findViewById(R.id.tvCat);
            if(tvCat !=null){
                tvCat.setVisibility(View.VISIBLE);
            } else {
                tvCat.setVisibility(View.GONE);
            }
            img_android = (ImageView)view.findViewById(R.id.imageview);
            if(img_android !=null){
                img_android.setVisibility(View.VISIBLE);
            } else {
                img_android.setVisibility(View.GONE);
            }

        }







    }

}