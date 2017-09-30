package com.bey2ollak.rafaeladel.bey2ollaktask.home.view;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.BaseViewInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;

import java.util.List;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public interface HomeViewInterface extends BaseViewInterface {
    void onGetPlacesSuccess(List<Place> places);
    void onGetPlacesFailed(Throwable throwable);
}
