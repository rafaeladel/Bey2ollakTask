package com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.BasePresenterInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;

import java.util.List;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public interface HomePresenterInterface extends BasePresenterInterface {
    void getPlaces(int page, int perPage);
    void searchPlaces(String query);
    List<Place> getStoredPlaces();
    void setPlaces(List<Place> places);
}
