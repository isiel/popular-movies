package siel.ignacio.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import siel.ignacio.popularmovies.BuildConfig;
import siel.ignacio.popularmovies.MoviesAPI;
import siel.ignacio.popularmovies.R;
import siel.ignacio.popularmovies.Values;
import siel.ignacio.popularmovies.activities.DetailActivity;
import siel.ignacio.popularmovies.objects.Movie;
import siel.ignacio.popularmovies.objects.Result;
import siel.ignacio.popularmovies.viewholders.MovieViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Retrofit mRetrofit;

    ArrayList<Movie> movies = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = prefs.getString(getString(R.string.pref_sort_results_key),
                getString(R.string.pref_sort_results_default));
        getMovies(sortBy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public class GridAdapter extends RecyclerView.Adapter<MovieViewHolder> {

        Picasso picasso;

        public GridAdapter(){
            picasso = Picasso.with(getActivity());
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_view_holder, parent, false);
            MovieViewHolder viewHolder = new MovieViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {

            final Movie movie = movies.get(position);

            picasso.load(Values.POSTER_PATH_W185.concat(movie.getPosterPath()))
                    .into(holder.poster);

            holder.setClickListener(new MovieViewHolder.ClickListener() {
                @Override
                public void onClick(View v, int position, boolean isLongClick) {
                    //TODO details on movie
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movie", Parcels.wrap(movie));
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private void buildRetrofit(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public void getMovies(String sortBy){
        buildRetrofit();
        MoviesAPI moviesAPI = mRetrofit.create(MoviesAPI.class);
        Call<Result> call;
        if(sortBy.equals(getString(R.string.pref_sort_results_popular))){
            call = moviesAPI.getPopularMovies(BuildConfig.MOVIE_DATABASE_API_KEY, 1);
        }else{
            call = moviesAPI.getHighestRatedMovies(BuildConfig.MOVIE_DATABASE_API_KEY, 1);
        }
        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Response<Result> response, Retrofit retrofit) {
                //TODO put the movies
                movies = response.body().getResults();
                if(mAdapter != null){
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int halfSpace;
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }

            outRect.top = space / 2;
            outRect.bottom = space / 2;
            outRect.left = space / 2;
            outRect.right = space / 2;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

    }

}
