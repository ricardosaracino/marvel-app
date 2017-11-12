package com.ricardosaracino.pulllist.fragment;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.content.Loader;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.List;


public class ComicBookWeekCurrentListFragment extends BaseComicBookListFragment {

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {


        Calendar c = GregorianCalendar.getInstance();

        int dow = c.get(Calendar.DAY_OF_WEEK);


        if(dow < Calendar.TUESDAY)
        {
            if(dow == Calendar.MONDAY) {
                c.add(Calendar.DATE, -6);
            }
            else if(dow == Calendar.SUNDAY) {
                c.add(Calendar.DATE, -5);
            }
            else if(dow == Calendar.SATURDAY) {
                c.add(Calendar.DATE, -4);
            }
            else if(dow == Calendar.FRIDAY) {
                c.add(Calendar.DATE, -3);
            }
            else if(dow == Calendar.THURSDAY) {
                c.add(Calendar.DATE, -2);
            }
            else if(dow == Calendar.WEDNESDAY) {
                c.add(Calendar.DATE, -1);
            }
        }


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = "", endDate = "";

        startDate = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        endDate = df.format(c.getTime());


        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics");

        marvelDataSource.addIntParam("offset", offset + count);

        marvelDataSource.addStringParam("dateRange", startDate+","+endDate);

        marvelDataSource.addStringParam("formatType", "comic");

        marvelDataSource.addStringParam("format", "comic");

        marvelDataSource.addStringParam("orderBy", "onsaleDate");

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }
}
