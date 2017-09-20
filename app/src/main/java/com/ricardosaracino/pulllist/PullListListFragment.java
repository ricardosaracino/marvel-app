package com.ricardosaracino.pulllist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.ricardosaracino.pulllist.datasource.HttpJsonDataSource;
import com.ricardosaracino.pulllist.hydrator.PullListJsonHydrator;
import com.ricardosaracino.pulllist.loader.PullListDataLoader;
import com.ricardosaracino.pulllist.model.PullList;

import java.util.List;

/**
 *
 */
public class PullListListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<PullList>> {

    private static final String TAG = "PullListListFragment";

    private static final int LOADER_ID = 1;

    private ArrayAdapter<PullList> mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        setEmptyText("No data, please add from menu.");

        setListAdapter(mAdapter);

        setListShown(false);


        getLoaderManager().initLoader(LOADER_ID, null, this);   // calls onCreateLoader
    }

    @Override
    public Loader<List<PullList>> onCreateLoader(int id, Bundle args) {

        HttpJsonDataSource httpJsonDataSource = new HttpJsonDataSource(new PullListJsonHydrator(), "https://app.wilma.ca/api/data.php");

        return new PullListDataLoader(getActivity(), httpJsonDataSource);
    }

    @Override
    public void onLoadFinished(Loader<List<PullList>> loader, List<PullList> data) {

        Log.i(TAG, "+++ onLoadFinished() called! +++");

        mAdapter.clear();

        for (PullList pullList : data) {
            mAdapter.add(pullList);
        }

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<PullList>> arg0) {
        mAdapter.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
