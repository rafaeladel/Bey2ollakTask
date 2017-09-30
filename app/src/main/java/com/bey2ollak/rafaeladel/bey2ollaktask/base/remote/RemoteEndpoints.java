package com.bey2ollak.rafaeladel.bey2ollaktask.base.remote;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

public interface RemoteEndpoints {
    @GET("places")
    Single<PlacesResponse> getPlaces(@Query("page") int page, @Query("size") int perPage);
}
