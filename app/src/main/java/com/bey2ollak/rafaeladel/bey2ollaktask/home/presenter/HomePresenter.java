package com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.BaseViewInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.model.HomeModelInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.view.HomeViewInterface;
import com.google.android.gms.location.places.GeoDataClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public class HomePresenter implements HomePresenterInterface, PlacesModelCallbacks {
    private HomeViewInterface homeViewInterface;
    private HomeModelInterface homeModelInterface;
    private List<Place> places;
    private Context context;

    public HomePresenter(Context context, HomeModelInterface homeModelInterface, GeoDataClient geoDataClient) {
        this.context = context;
        this.homeModelInterface = homeModelInterface;
        this.homeModelInterface.setGeoDataClient(geoDataClient);
        this.places = new ArrayList<>();
    }

    @Override
    public void attachView(BaseViewInterface view) {
        homeViewInterface = (HomeViewInterface) view;
    }

    @Override
    public void detachView() {
        homeViewInterface = null;
    }

    @Override
    public void getPlaces(int page, int perPage) {
        if(!isConnected()) {
            homeViewInterface.showConnectionError();
            return;
        }
        homeViewInterface.showLoading();
        homeModelInterface.loadSavedPlaces(page, perPage, this);
    }

    @Override
    public void searchPlaces(String query) {
        if(!isConnected()) {
            homeViewInterface.showConnectionError();
            return;
        }
        homeModelInterface.startSearchPlaces(query, this);
    }

    @Override
    public void onPlacesRetrieveSuccess(List<Place> places) {
        this.places = places;
        //Adding a separator on top
        this.places.add(0, null);
        homeViewInterface.hideLoading();
        homeViewInterface.onGetPlacesSuccess(places);
    }

    @Override
    public void onPlacesRetrieveFailure(Throwable throwable) {
        homeViewInterface.onGetPlacesFailed(throwable);
    }

    /**
     * This method returns the search result, also it modifies the array with results, separator and API places
     * @param places
     */
    @Override
    public void onPlacesSearchSuccess(List<Place> places) {
        if(this.places == null) {
            setPlaces(new ArrayList<Place>());
        }
        int indexOfSeparaotr = this.places.indexOf(null);
        if(indexOfSeparaotr != -1) {
            //removes the previous search results
            this.places.subList(0, indexOfSeparaotr + 1).clear();
        }

        //null as a separator
        this.places.add(0, null);
        this.places.addAll(0, places);
        homeViewInterface.hideLoading();
        homeViewInterface.onGetPlacesSuccess(this.places);
    }

    @Override
    public void onPlacesSearchFailure(Throwable throwable) {
        homeViewInterface.onGetPlacesFailed(throwable);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public List<Place> getStoredPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
