package com.bey2ollak.rafaeladel.bey2ollaktask.home.view;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bey2ollak.rafaeladel.bey2ollaktask.R;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.broadcast_receiver.NetworkBroadcastReceiver;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.dependency_injection.component.DaggerHomeComponent;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.dependency_injection.module.HomeModule;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.presenter.HomePresenterInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public class HomeFragment extends Fragment implements HomeViewInterface {
    private static final String PLACES_DATA = "places.data";
    private static final String IS_FIRST_RUN = "is.first.run";
    private int PAGE = 0;
    private int PER_PAGE = 25;
    private boolean isFirstRun;
    private HomePresenterInterface homePresenterInterface;
    private ProgressBar placesProgressBar;
    private RecyclerView placesRecyclerView;
    private PlacesAdapter placesAdapter;
    private NetworkBroadcastReceiver networkReceiver;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMvp();
        //to control re-retrieving places
        isFirstRun = true;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            isFirstRun = savedInstanceState.getBoolean(IS_FIRST_RUN, true);
            List<Place> places = savedInstanceState.getParcelableArrayList(PLACES_DATA);
            homePresenterInterface.setPlaces(places);
            onGetPlacesSuccess(places);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FIRST_RUN, isFirstRun);
        outState.putParcelableArrayList(PLACES_DATA, (ArrayList<? extends Parcelable>) homePresenterInterface.getStoredPlaces());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        placesRecyclerView = (RecyclerView) view.findViewById(R.id.places_recyclerView);
        placesProgressBar = (ProgressBar) view.findViewById(R.id.home_progressbar);
        placesAdapter = new PlacesAdapter();
        placesAdapter.setPlaces(homePresenterInterface.getStoredPlaces());
        placesRecyclerView.setAdapter(placesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        placesRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initMvp() {
        homePresenterInterface = DaggerHomeComponent.builder()
                .homeModule(new HomeModule(getActivity()))
                .build()
                .getHomePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homePresenterInterface != null) {
            homePresenterInterface.attachView(this);
            registerNetworkReciever();
            //to avoid recalling getPlaces()
            if (isFirstRun)
                homePresenterInterface.getPlaces(PAGE, PER_PAGE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isFirstRun = false;
        if (networkReceiver != null)
            getActivity().unregisterReceiver(networkReceiver);
        if (homePresenterInterface != null) {
            homePresenterInterface.detachView();
        }
    }



    public void doSearch(String query) {
        homePresenterInterface.searchPlaces(query);
    }

    @Override
    public void showLoading() {
        placesProgressBar.setVisibility(View.VISIBLE);
        placesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        placesProgressBar.setVisibility(View.GONE);
        placesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnectionError() {
        hideLoading();
        Toast.makeText(getActivity(), R.string.internet_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPlacesSuccess(List<Place> places) {
        placesAdapter.setPlaces(places);
        placesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPlacesFailed(Throwable throwable) {
        hideLoading();
        Toast.makeText(getActivity(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
    }


    private void registerNetworkReciever() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        networkReceiver = new NetworkBroadcastReceiver();
        networkReceiver.setActionToTake(new Runnable() {
            @Override
            public void run() {
                //if app opened when offline, load API places
                if (homePresenterInterface.getStoredPlaces().size() == 0) {
                    homePresenterInterface.getPlaces(PAGE, PER_PAGE);
                }
            }
        });
        getActivity().registerReceiver(networkReceiver, intentFilter);
    }
}
