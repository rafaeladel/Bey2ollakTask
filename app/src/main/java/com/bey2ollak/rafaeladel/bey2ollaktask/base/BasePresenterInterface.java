package com.bey2ollak.rafaeladel.bey2ollaktask.base;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public interface BasePresenterInterface {
    void attachView(BaseViewInterface view);
    void detachView();
}
