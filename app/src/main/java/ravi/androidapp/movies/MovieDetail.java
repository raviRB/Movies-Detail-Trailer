package ravi.androidapp.movies;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetail extends AppCompatActivity {

    String key;
    String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.movie_detail);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

            Intent intent = getIntent();

            String backdrop = intent.getStringExtra("image1");
            title = intent.getStringExtra("title");
            String crew = intent.getStringExtra("crew");
            String date = intent.getStringExtra("date");
            key = intent.getStringExtra("key");
            String overview = intent.getStringExtra("overview");

            ImageView imageView1 = (ImageView) findViewById(R.id.imageview2);

        Picasso.with(this).load(String.valueOf("http://image.tmdb.org/t/p/w185/"+backdrop)).into(imageView1);
            TextView textView = (TextView) findViewById(R.id.overview);
            TextView textView1 = (TextView) findViewById(R.id.crew);
            TextView titleview = (TextView) findViewById(R.id.title);
            TextView dateview = (TextView) findViewById(R.id.date);
            textView.setText(overview);
            titleview.setText(title);
            textView1.setText(crew);
            dateview.setText(date);
        isNetworkConnectionAvailable();

    }

    public void trailer(View view) {
        if(key.isEmpty())
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+title)));
        else
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+key)));
    }

    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isNetworkConnectionAvailable();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }


}
