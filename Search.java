/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itis.search;

import java.util.ArrayList;

/**
 *
 * @author Gbemiro Jiboye <gbenroscience@gmail.com>
 */
public abstract class Search<T> {

    /**
     * The user has just stated typing in the field
     */
    private static final int STARTING = 1;
    /**
     * No search, the resting mode of the field
     */
    private static final int NORMAL = 2;
    /**
     * The user is actively searching.
     */
    private static final int SEARCH = 3;

    /**
     * The user has stopped searching the adapter, so we can switch back to
     * normal mode.
     */
    private static final int ENDING = 4;

    private int searchMode = NORMAL;

    private ArrayList<T> data = new ArrayList<>();
    private ArrayList<T> backupData = new ArrayList<>();

    public boolean isStarting(int searchMode) {
        return searchMode == STARTING;
    }

    public boolean isNormal(int searchMode) {
        return searchMode == NORMAL;
    }

    public boolean isSearch(int searchMode) {
        return searchMode == SEARCH;
    }

    public boolean isEnding(int searchMode) {
        return searchMode == ENDING;
    }

    private void setSearchMode(int searchMode) {
        synchronized (data) {

            switch (searchMode) {
                case STARTING:
                    backupData.clear();
                    backupData.addAll(data);
                    data.clear();
                    searchMode = SEARCH;
                    break;
                case ENDING:
                    if (!backupData.isEmpty()) {
                        data.clear();
                        data.addAll(backupData);
                        backupData.clear();
                         update(data);
                    }
                    searchMode = NORMAL;
                    break;

                default:
                    break;

            }
        }
        this.searchMode = searchMode;
    }

    public void resetData(ArrayList<T> data) {
        synchronized (this) {
            this.backupData.clear();
            this.searchMode = NORMAL;
            this.data = data;
        }
    }

    private void setData(ArrayList<T> data) {
        this.data = data;
        update(data);
    }

    public ArrayList<T> getData() {
        return data;
    }

    public int getSearchMode() {
        return searchMode;
    }

    public ArrayList<T> getBackupData() {
        return backupData;
    }

    /**
     *
     * @param searchText The text to search
     */
    public final void find(String searchText) {

        if (searchText.isEmpty()) {
            setSearchMode(ENDING);
            return;
        }
        if (isNormal(searchMode)) {
            setSearchMode(STARTING);
        }

        ArrayList<T> foundData = new ArrayList<>();

        for (T t : backupData) {
            if (match(searchText, t)) {
                foundData.add(t);
            }

        }

        setData(foundData);
    }

    /**
     * Gives the success conditions for the search. If the
     *
     * @param searchText The text to search for
     * @param searchableItem Each item in your collection of data.
     * @return true if there is a match.
     */
    public abstract boolean match(String searchText, T searchableItem);

    /**
     *
     * @param data The data obtained from the search results. Display in your
     * view model/user interface
     */
    public abstract void update(ArrayList<T> data);

}
