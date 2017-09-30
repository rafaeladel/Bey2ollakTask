package com.bey2ollak.rafaeladel.bey2ollaktask.base.remote;

import com.bey2ollak.rafaeladel.bey2ollaktask.BuildConfig;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

public class RemoteWebClient {
    private static RemoteWebClient remoteWebClient;
    private static final String BASE_URL = "http://bey2ollak-places-task.eu-west-1.elasticbeanstalk.com/api/json/";
    private RemoteEndpoints remoteEndpoints;

    private RemoteWebClient() {
        Retrofit retrofit = createRetrofitBuilder().build();
        remoteEndpoints = retrofit.create(RemoteEndpoints.class);
    }

    public static RemoteWebClient getInstance() {
        if(remoteWebClient == null) {
            remoteWebClient = new RemoteWebClient();
        }

        return remoteWebClient;
    }

    /**
     * This method is use to create retrofit adapter.
     * Any Retrofit setup should be performed here
     *
     * @return Instance of retrofit builder.
     */
    private Retrofit.Builder createRetrofitBuilder() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        GsonBuilder gsonBuilder = new GsonBuilder();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));
    }

    public Single<PlacesResponse> getPlaces(int page, int perPage) {
        return remoteEndpoints.getPlaces(page, perPage);
    }
}
