package ravi.androidapp.movies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class favoriteMoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> poster = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> backdrop = new ArrayList<>();
    ArrayList<String> overview = new ArrayList<>();
    ArrayList<String> crew = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> key = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    MovieDbHelper movieDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_movies_card_view);

        movieDbHelper = new MovieDbHelper(this);
        Cursor cursor = movieDbHelper.getAllMovies();
        cursor.moveToFirst();

        //Log.i(" ",DatabaseUtils.dumpCursorToString(cursor));
        int flag=0;
        for(int i=0;i<cursor.getCount();i++){
            if(cursor.getInt(8)==0){
                cursor.moveToNext();
                continue;
            }
            id.add(cursor.getString(0));
            title.add(cursor.getString(1));
            poster.add(cursor.getString(2));
            backdrop.add(cursor.getString(3));
            date.add(cursor.getString(4));
            overview.add(cursor.getString(5));
            key.add(cursor.getString(6));
            crew.add(cursor.getString(7));
            flag++;
            cursor.moveToNext();
        }

        if(flag==0){
            Toast.makeText(this,"Please add a movie to Favorite. ",Toast.LENGTH_LONG).show();
            cursor.close();
            startActivity(new Intent(favoriteMoviesActivity.this, MainActivity.class));
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        CaptionedImageAdapter adapter = new CaptionedImageAdapter(id,title,poster,backdrop,overview,crew,date,key,getApplicationContext());
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        };

    }
