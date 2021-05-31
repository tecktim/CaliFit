package com.example.CaliFit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements HomeActivityPresenter.ViewInterface {

    //Dev branch init
    HomeActivityPresenter homeActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutIntent();
            }
        });
        homeActivityPresenter  = new HomeActivityPresenter(this);
        TextView text = (TextView) findViewById(R.id.namePreviewHome);



        Button addWorkoutOne = findViewById(R.id.addWorkoutOne);
        addWorkoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Workout one");
                startActivity(intent);
            }
        });
        Button addWorkoutTwo = findViewById(R.id.addWorkoutTwo);
        addWorkoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Workout two");
                startActivity(intent);
            }
        });
        Button addWorkoutThree = findViewById(R.id.addWorkoutThree);
        addWorkoutThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Workout three");
                startActivity(intent);
            }
        });
    }

    public void openAboutIntent() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/tecktim/CaliFit"));
        startActivity(browserIntent);
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
