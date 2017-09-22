package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.activity.ComicBookActivity;
import com.ricardosaracino.pulllist.model.ComicBook;

public class ComicBookAdapter extends ArrayAdapter<ComicBook> {

    public ComicBookAdapter(Context context) {
        super(context, R.layout.pull_list_row);
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.pull_list_row, parent, false);
        }

        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        labelView.setText(getItem(position).getTitle());
        valueView.setText(getItem(position).getDescription());

        return rowView;
    }
}
