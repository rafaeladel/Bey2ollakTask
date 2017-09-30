package com.bey2ollak.rafaeladel.bey2ollaktask.home.model;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.remote.PlacesResponse;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.remote.RemoteWebClient;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.PlacesModelCallbacks;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public class HomeModel implements HomeModelInterface{
    private RemoteWebClient remoteWebClient;
    private GeoDataClient geoDataClient;
    public HomeModel(RemoteWebClient remoteWebClient) {
        this.remoteWebClient = remoteWebClient;
    }

    @Override
    public void loadSavedPlaces(int page, int perPage, final PlacesModelCallbacks placesModelCallbacks) {
        remoteWebClient.getPlaces(page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<PlacesResponse, List<Place>>() {
                    @Override
                    public List<Place> apply(PlacesResponse placesResponse) throws Exception {
                        return placesResponse.getPlaces();
                    }
                })
                .subscribe(new Consumer<List<Place>>() {
                    @Override
                    public void accept(List<Place> places) throws Exception {
                        placesModelCallbacks.onPlacesRetrieveSuccess(places);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        placesModelCallbacks.onPlacesRetrieveFailure(throwable);
                    }
                });
    }

    /**
     * This method gets {@AutocompletePredictionBufferResponse} and extract info from it into an {@ArrayList<Place>}
     * @param query
     * @param placesModelCallbacks
     */
    @Override
    public void startSearchPlaces(String query, final PlacesModelCallbacks placesModelCallbacks) {
        geoDataClient.getAutocompletePredictions(query,null, null).addOnSuccessListener(new OnSuccessListener<AutocompletePredictionBufferResponse>() {
            @Override
            public void onSuccess(AutocompletePredictionBufferResponse autocompletePredictions) {
                Observable.fromIterable(autocompletePredictions)
                        .subscribeOn(Schedulers.computation())
                        .collect(new Callable<List<Place>>() {
                            @Override
                            public List<Place> call() throws Exception {
                                return new ArrayList<>();
                            }
                        }, new BiConsumer<List<Place>, AutocompletePrediction>() {
                            //Converting each {@AutocompletePrediction} to {@Place} and add it to a {@List<Place>}
                            @Override
                            public void accept(List<Place> places, AutocompletePrediction autocompletePrediction) throws Exception {
                                Place place = new Place();
                                place.setName(autocompletePrediction.getPrimaryText(null).toString());
                                place.setAddress(autocompletePrediction.getSecondaryText(null).toString());
                                places.add(place);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Place>>() {
                                       @Override
                                       public void accept(List<Place> places) throws Exception {
                                            placesModelCallbacks.onPlacesSearchSuccess(places);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        placesModelCallbacks.onPlacesSearchFailure(throwable);
                                    }
                                });
            }
        });
    }

    @Override
    public void setGeoDataClient(GeoDataClient geoDataClient) {
        this.geoDataClient = geoDataClient;
    }
}
