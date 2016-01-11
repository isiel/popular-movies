package siel.ignacio.popularmovies.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import siel.ignacio.popularmovies.R;

/**
 * Created by ignacio on 01/02/16.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView poster;
    private ClickListener clickListener;

    public MovieViewHolder(final View container) {
        super(container);
        poster = (ImageView) container.findViewById(R.id.movie_poster);
        container.setOnClickListener(this);
    }

    public interface ClickListener {
        void onClick(View v, int position, boolean isLongClick);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        // If not long clicked, pass last variable as false.
        clickListener.onClick(v, getAdapterPosition(), false);
    }

}
