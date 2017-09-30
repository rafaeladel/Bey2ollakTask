package com.bey2ollak.rafaeladel.bey2ollaktask.base.dependency_injection.component;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.dependency_injection.module.HomeModule;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.HomePresenter;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.HomePresenterInterface;

import dagger.Component;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

@Component(modules = HomeModule.class)
public interface HomeComponent {
    HomePresenterInterface getHomePresenter();
}
