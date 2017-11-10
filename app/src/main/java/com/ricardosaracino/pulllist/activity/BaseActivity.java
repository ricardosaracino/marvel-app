package com.ricardosaracino.pulllist.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.ricardosaracino.pulllist.R;

/*
http://hmkcode.com/material-design-app-android-design-support-library-appcompat/
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;

    protected Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createContentView();

        createNavigationView();

        createToolBarView();

        setTitle("Comics");
    }

    public void createContentView() {
    }

    private void createNavigationView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void createToolBarView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object

        if (toolbar != null) {

            setSupportActionBar(toolbar);

            // Show menu icon
            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
