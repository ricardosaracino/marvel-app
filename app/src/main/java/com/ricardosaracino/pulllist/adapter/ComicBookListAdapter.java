package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.activity.ComicBookActivity;
import com.ricardosaracino.pulllist.model.ComicBook;

public class ComicBookListAdapter extends ArrayAdapter<ComicBook> {

    Context c;

    public ComicBookListAdapter(Context context) {
        super(context, R.layout.comic_list_row);

        c = context;
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.comic_list_row, parent, false);
        }

        TextView labelView = rowView.findViewById(R.id.label);
        TextView valueView = rowView.findViewById(R.id.value);

        labelView.setText(getItem(position).getTitle());
        valueView.setText(getItem(position).getDescription());

        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ComicBook comicBook = getItem(position);

                    Intent intent = new Intent(c, ComicBookActivity.class);

                    Bundle b = new Bundle();

                    b.putInt("comic_id", comicBook.getId());

                    intent.putExtras(b);

                    c.startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        return rowView;
    }
}
