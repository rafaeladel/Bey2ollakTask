package com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;

import java.util.List;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

public interface PlacesModelCallbacks {
    void onPlacesRetrieveSuccess(List<Place> places);
    void onPlacesRetrieveFailure(Throwable throwable);
    void onPlacesSearchSuccess(List<Place> places);
    void onPlacesSearchFailure(Throwable throwable);
}
