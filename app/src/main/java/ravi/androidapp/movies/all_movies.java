package ravi.androidapp.movies;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class all_movies extends AppCompatActivity{

    RecyclerView recyclerView;
    int i=0;
    String Tablename;
    private DatabaseReference databaseReference;
    Movie obj;
    ArrayList<String> poster = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> backdrop = new ArrayList<>();
    ArrayList<String> overview = new ArrayList<>();
    ArrayList<String> crew = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> key = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_movies_card_view);
        Intent intent = getIntent();
        Tablename = intent.getStringExtra("Tablename");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait !!");
        progressDialog.setTitle("Loading");
        progressDialog.show();

        ValueEventListener postListener = new ValueEventListener() {
            int count=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    count++;
                    obj = d.getValue(Movie.class);
                    assert obj != null;
                    poster.add(obj.getPoster());
                    title.add(obj.getTitle());
                    backdrop.add(obj.getBackdrop());
                    overview.add(obj.getOverview());
                    crew.add(obj.getCrew());
                    date.add(obj.getDate());
                    key.add(obj.getLink());
                }
                progressDialog.dismiss();
                recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                CaptionedImageAdapter adapter = new CaptionedImageAdapter(title,poster,backdrop,overview,crew,date,key,getApplicationContext());
                recyclerView.setAdapter(adapter);
                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

            }
        };



        databaseReference.child(Tablename).addValueEventListener(postListener);

    }


}
