package siel.ignacio.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import siel.ignacio.popularmovies.R;
import siel.ignacio.popularmovies.Values;
import siel.ignacio.popularmovies.objects.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    @Bind(R.id.movie_title_textview)
    TextView title;

    @Bind(R.id.movie_poster_imageview)
    ImageView poster;

    @Bind(R.id.movie_release_date_textview)
    TextView releaseDate;

    @Bind(R.id.movie_rating_textview)
    TextView rating;

    @Bind(R.id.movie_plot_textview)
    TextView plot;

    Picasso picasso;

    public DetailActivityFragment() {
        picasso = Picasso.with(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        Bundle extras = getActivity().getIntent().getExtras();
        Movie movie = new Movie();
        if(extras != null){
            movie = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("movie"));
        }

        title.setText(movie.getTitle());
        picasso.load(Values.POSTER_PATH_W500.concat(movie.getPosterPath()))
                .placeholder(R.mipmap.poster_placeholder)
                .into(poster);

        releaseDate.setText(movie.getReleaseDate().substring(0, 4));

        rating.setText(movie.getVoteAverage().toString().concat("/10"));
        plot.setText(movie.getOverview());

        return view;
    }
}
