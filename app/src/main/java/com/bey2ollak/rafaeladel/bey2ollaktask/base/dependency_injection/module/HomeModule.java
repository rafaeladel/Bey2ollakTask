package com.bey2ollak.rafaeladel.bey2ollaktask.base.dependency_injection.module;

import android.content.Context;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.remote.RemoteWebClient;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.model.HomeModel;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.model.HomeModelInterface;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.HomePresenter;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.HomePresenterInterface;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rafael Adel on 9/30/2017.
 */
@Module
public class HomeModule {
    Context context;

    public HomeModule(Context context) {
        this.context = context;
    }

    @Provides
    HomeModelInterface providesHomeModel(RemoteWebClient remoteWebClient) {
        return new HomeModel(remoteWebClient);
    }

    @Provides
    GeoDataClient providesGeoDataClient() {
        return Places.getGeoDataClient(context, null);
    }

    @Provides
    HomePresenterInterface providesHomePresenter(HomeModelInterface homeModel, GeoDataClient geoDataClient){
        return new HomePresenter(context, homeModel, geoDataClient);
    }

    @Provides
    RemoteWebClient providesRemoteWebClient() {
        return RemoteWebClient.getInstance();
    }

}
