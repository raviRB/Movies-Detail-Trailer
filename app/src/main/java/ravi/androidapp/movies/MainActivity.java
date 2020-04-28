package ravi.androidapp.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            isNetworkConnectionAvailable();

    }


        public void checkNetworkConnection() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

        public void isNetworkConnectionAvailable() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();
            if (isConnected) {
                Log.d("Network", "Connected");
            } else {
                checkNetworkConnection();
                Log.d("Network", "Not Connected");
            }

        }

    public void getallmovies(View view) {
        Intent myIntent = new Intent(getApplicationContext(), all_movies.class);
        myIntent.putExtra("Tablename", view.getTag().toString());
        this.startActivity(myIntent);
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        if(item.getItemId() == R.id.favorite){
            startActivity(new Intent(MainActivity.this, favoriteMoviesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //leaving empty to avoid getting back to empty favorite activity
    }

    // Download setup from TMDB Database
/*
    ProgressDialog pd;
    byte[] b1, b2;
    Cursor res,res1;
    int flag2=0 , flag1=0;
    String key;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // genre detail {"genres":[{"id":28,"name":"Action"},{"id":12,"name":"Adventure"},{"id":16,"name":"Animation"},{"id":35,"name":"Comedy"},
        // {"id":80,"name":"Crime"},{"id":99,"name":"Documentary"},{"id":18,"name":"Drama"},{"id":10751,"name":"Family"},{"id":14,"name":"Fantasy"},
        // {"id":36,"name":"History"},{"id":27,"name":"Horror"},{"id":10402,"name":"Music"},{"id":9648,"name":"Mystery"},{"id":10749,"name":"Romance"},
        // {"id":878,"name":"Science Fiction"},{"id":10770,"name":"TV Movie"},{"id":53,"name":"Thriller"},{"id":10752,"name":"War"},{"id":37,"name":"Western"}]}

        // image http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        // getdata();
        // cast and crew  https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=<<api_key>>
        DownloadTask downloadTask = new DownloadTask();
        // web shows
        //downloadTask.execute("https://api.themoviedb.org/3/discover/tv?api_key=16ce61aa6a4e512553d277038224315d" +
          //      "&include_null_first_air_dates=true&language=en-US&sort_by=popularity.desc&page=11");
        //Hindi
        //downloadTask.execute("http://api.themoviedb.org/3/discover/movie?with_original_language=hi&certification_country=IN&sort_by=vote_count.desc" +
           //     "&primary_release_year=2019&page=7&api_key=16ce61aa6a4e512553d277038224315d");
        // Latest
        //downloadTask.execute("https://api.themoviedb.org/3/movie/now_playing?api_key=16ce61aa6a4e512553d277038224315d&language=en-US&page=5");
        //review     https://api.themoviedb.org/3/movie/700/reviews?api_key=16ce61aa6a4e512553d277038224315d&language=en-US&page=1
        // else

        //downloadTask.execute("http://api.themoviedb.org/3/discover/movie?certification_country=US&sort_by=vote_count.desc&primary_release_year=2018&page=2&with_genres=878&api_key=16ce61aa6a4e512553d277038224315d");


    }

    public void getallmovies(View view) {
        Intent myIntent = new Intent(getApplicationContext(), all_movies.class);
        myIntent.putExtra("Tablename", view.getTag().toString());
        this.startActivity(myIntent);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        String id, title, overview, image2, date, key = "", name = "";
        byte[] img, img1;

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            InputStream in;
            InputStreamReader reader;
            int data;

            try {

                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                in = urlConnection.getInputStream();

                reader = new InputStreamReader(in);


                data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }


                JSONObject jObj = new JSONObject(result);


                JSONArray jr = jObj.getJSONArray("results");
                int j;

                for (j = 0; j < jr.length(); j++) {
                    JSONObject jb1 = jr.getJSONObject(j);
                    id = jb1.getString("id");
                    String poster_path = jb1.getString("poster_path");
                    title = jb1.getString("name");
                    Log.i("title",title);
                    //title = jb1.getString("title");
                    overview = jb1.getString("overview");
                    image2 = jb1.getString("backdrop_path");
                    //date = jb1.getString("release_date");
                    date = jb1.getString("first_air_date");
                    String s = jb1.getString("genre_ids");

                    if(s.contains("16") || image2.equals("null")|| poster_path.equals("null") )
                        continue;

                    // crew detail

                    //url = new URL("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=16ce61aa6a4e512553d277038224315d");
                    url = new URL("https://api.themoviedb.org/3/tv/" + id + "/season/1/credits?api_key=16ce61aa6a4e512553d277038224315d&language=en-US");

                    urlConnection = (HttpURLConnection) url.openConnection();

                    in = urlConnection.getInputStream();

                    reader = new InputStreamReader(in);

                    //  Log.i("in", "inside cast");

                    data = reader.read();

                    String cast = "";
                    while (data != -1) {
                        char current = (char) data;

                        cast += current;

                        data = reader.read();
                    }


                    getcast(cast);

                    // get youtube link

                    //url = new URL("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=16ce61aa6a4e512553d277038224315d&language=en-US");
                    url = new URL("https://api.themoviedb.org/3/tv/" + id + "/season/1/videos?api_key=16ce61aa6a4e512553d277038224315d&language=en-US");
                    https://api.themoviedb.org/3/tv/{tv_id}/season/{season_number}/videos?api_key=<<api_key>>&language=en-US
                    urlConnection = (HttpURLConnection) url.openConnection();

                    in = urlConnection.getInputStream();

                    reader = new InputStreamReader(in);

                    //  Log.i("in", "inside cast");

                    data = reader.read();

                    String link = "";
                    while (data != -1) {
                        char current = (char) data;

                        link += current;

                        data = reader.read();
                    }
                    // for reviews

                    getlink(link);

                    // Log.i("cast", cast);

                    // INserting into Firebase Database

                    Movie user = new Movie(id,title,poster_path,image2,date,overview,key,name);
                    Log.i("detail", id + "   " + title + "   " + poster_path + "        " + overview+ "   " + image2 + "   " + date + "   " + key + "   " + name );
                    databaseReference.child("TV Shows").child(id).setValue(user);


                }

                Log.i("done", "finally  ");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        void getlink(String link) {
            key = "";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(link);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonArray.length() > 0) {
                JSONObject object = null;
                try {
                    object = jsonArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    key = object.getString("key");
                } catch (JSONException e) {

                }
            }
            //Log.i("key", key);

        }


        void getcast(String data) {
            name = "";
            JSONObject jObj = null;
            try {
                jObj = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jr = null;
            try {
                jr = jObj.getJSONArray("cast");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jr.length() > 0) {
                int len = 7;

                if (jr.length() < 7)
                    len = jr.length();

                JSONObject jo = null;
                try {
                    jo = jr.getJSONObject(0);
                    name = jo.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int g = 1; g < len; g++) {
                    try {
                        jo = jr.getJSONObject(g);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        name = name + ", " + jo.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Log.i("name", name);
                //Boolean c = db.insetcrew(name);
                // Log.i("crew detail database", String.valueOf(c));
                // Boolean e = db.insertData(id,title,overview,img,name);
                //Log.i("id title and o database", String.valueOf(e));
            }


        }
    }*/
}


