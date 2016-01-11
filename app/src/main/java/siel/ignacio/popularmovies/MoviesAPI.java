package siel.ignacio.popularmovies;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import siel.ignacio.popularmovies.objects.Result;

/**
 * Created by ignacio on 01/02/16.
 */
public interface MoviesAPI {

    @Headers("Accept:application/json")
    @GET("/3/movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @Headers("Accept:application/json")
    @GET("/3/movie/top_rated")
    Call<Result> getHighestRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

}
