package com.example.CaliFit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements HomeActivityPresenter.ViewInterface {

    HomeActivityPresenter homeActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivityPresenter.resetCounter();
                Snackbar.make(view, "Counter Reset", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        homeActivityPresenter  = new HomeActivityPresenter(this);
        TextView text = (TextView) findViewById(R.id.countTextView);
        text.setText("Counter: " + homeActivityPresenter.getCounter());
    }

    public void countUpClicked(View view) {
        homeActivityPresenter.incrementCounter();
    }

    public void countDownClicked(View view) {
        homeActivityPresenter.decrementCounter();
    }

    @Override
    public void updateCounter(int nCounter) {
        TextView text = (TextView) findViewById(R.id.countTextView);
        text.setText("Counter: " + nCounter);
        // text.setText("Counter: " + mainActivityPresenter.getCounter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
