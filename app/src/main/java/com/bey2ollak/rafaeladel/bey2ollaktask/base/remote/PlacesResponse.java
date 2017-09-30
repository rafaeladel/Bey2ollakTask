package com.bey2ollak.rafaeladel.bey2ollaktask.base.remote;

import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

public class PlacesResponse {
    @SerializedName("content")
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @SerializedName("last")
    private boolean isLast;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalElements")
    private int totalElements;

    @SerializedName("first")
    private boolean isFirst;

    @SerializedName("numberOfElements")
    private int numberOfElements;

    @SerializedName("size")
    private int size;

    @SerializedName("number")
    private int page;
}
