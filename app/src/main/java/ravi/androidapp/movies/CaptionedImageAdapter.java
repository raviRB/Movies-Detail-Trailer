package ravi.androidapp.movies;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder>{

    String tablename;
    private Context c;
    private DatabaseReference databaseReference;
    Movie obj;
    private ArrayList<String> poster = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> backdrop = new ArrayList<>();
    private ArrayList<String> overview = new ArrayList<>();
    private ArrayList<String> crew = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> key = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();


    CaptionedImageAdapter(ArrayList<String> id, ArrayList<String> title, ArrayList<String> poster, ArrayList<String> backdrop, ArrayList<String> overview, ArrayList<String> crew, ArrayList<String> date, ArrayList<String> key, Context applicationContext) {
        this.title= title;
        this.poster = poster;
        this.c = applicationContext;
        this.backdrop= backdrop;
        this.overview = overview;
        this.crew= crew;
        this.date = date;
        this.key = key;
        this.id = id;
    }

    @NonNull
    @Override
    public CaptionedImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(CaptionedImageAdapter.ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.first_image);
        TextView textView = (TextView)cardView.findViewById(R.id.first_text);
        textView.setText(title.get(position));
        Picasso.with(c).load(String.valueOf("http://image.tmdb.org/t/p/w185/"+poster.get(position))).into(imageView);
        imageView.setContentDescription("No image Available");


       cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(),MovieDetail.class);

                intent.putExtra("id", id.get(position));
                intent.putExtra("poster", poster.get(position));
                intent.putExtra("title", title.get(position));
                intent.putExtra("crew", crew.get(position));
                intent.putExtra("image1", backdrop.get(position));
                intent.putExtra("date", date.get(position));
                intent.putExtra("key", key.get(position));
                intent.putExtra("overview", overview.get(position));
                cardView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        //Log.i("size", String.valueOf(title.size()));
        return title.size();
    }

    class ViewHolder  extends  RecyclerView.ViewHolder{

        private CardView cardView;

        ViewHolder(CardView v){
            super(v);
            cardView = v;

        }
    }




}

