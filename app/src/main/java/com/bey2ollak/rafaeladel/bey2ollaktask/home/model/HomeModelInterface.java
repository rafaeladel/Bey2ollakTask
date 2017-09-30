package com.bey2ollak.rafaeladel.bey2ollaktask.home.model;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.BaseModelInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.PlacesModelCallbacks;
import com.google.android.gms.location.places.GeoDataClient;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public interface HomeModelInterface extends BaseModelInterface {
    void loadSavedPlaces(int page, int perPage, PlacesModelCallbacks placesModelCallbacks);
    void startSearchPlaces(String query, PlacesModelCallbacks placesModelCallbacks);
    void setGeoDataClient(GeoDataClient geoDataClient);
}
