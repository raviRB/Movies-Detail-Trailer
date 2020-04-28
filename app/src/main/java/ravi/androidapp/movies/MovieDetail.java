package ravi.androidapp.movies;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;


public class MovieDetail extends AppCompatActivity {

    String title, date, poster, id, key;
    ToggleButton favorite_toggle_button;
    MovieDbHelper myMoviesDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.movie_detail);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        myMoviesDb = new MovieDbHelper(this);
        assert actionBar != null;
        actionBar.hide();

            Intent intent = getIntent();

            poster = intent.getStringExtra("poster");
            id = intent.getStringExtra("id");
            final String backdrop = intent.getStringExtra("image1");
            title = intent.getStringExtra("title");
            final String crew = intent.getStringExtra("crew");
            date = intent.getStringExtra("date");
            key = intent.getStringExtra("key");
            final String overview = intent.getStringExtra("overview");

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

        // favorite button ( heart shaped ) to mark a movie as favorite
        favorite_toggle_button = findViewById(R.id.button_favorite);
        final Cursor cursor = myMoviesDb.isFavorite(id);
        cursor.moveToFirst();
        if(cursor.getCount()>0 && cursor.getInt(0)==1)
            favorite_toggle_button.setChecked(true);

        favorite_toggle_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if(isChecked){
                   if(cursor.getCount()==0)
                       myMoviesDb.insertMovie(id,title,poster,backdrop,date,overview,key,crew);
                   else
                       myMoviesDb.updateMovie(id,1);
                   Toast.makeText(MovieDetail.this,"Added to Favorite" ,Toast.LENGTH_SHORT).show();
               }
               else{
                   myMoviesDb.updateMovie(id,0); // 0 - not favorite
                   Toast.makeText(MovieDetail.this,"Removed from Favorite" ,Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    public void trailer(View view) {
        if(key.isEmpty())
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+title+"+trailer")));
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

    public void isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
        }
    }


}
