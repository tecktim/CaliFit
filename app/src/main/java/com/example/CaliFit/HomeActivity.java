package com.example.CaliFit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeActivityPresenter.ViewInterface {

    //Dev branch init
    HomeActivityPresenter homeActivityPresenter;
    private DatabaseReference pushRef;
    private DatabaseReference pullRef;
    private DatabaseReference legsRef;
    private DatabaseReference coreRef;
    private ArrayList<Exercise> PushList = new ArrayList<>();
    private ArrayList<Exercise> PullList = new ArrayList<>();
    private ArrayList<Exercise> LegsList = new ArrayList<>();
    private ArrayList<Exercise> CoreList = new ArrayList<>();

    private static TinyDB tinyDB;

    public static TinyDB getDB(){
        return tinyDB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setButtons();

        if (tinyDB == null) {
            tinyDB = new TinyDB(this);
        }
        handleDataOnStart();
    }

    private void handleDataOnStart() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        pushRef = database.getReference("Push");
        pullRef = database.getReference("Pull");
        legsRef = database.getReference("Legs");
        coreRef = database.getReference("Core");
        getExercisesFromFirebaseAndSetToTinyDB(pushRef, PushList, "PushListToShow");
        getExercisesFromFirebaseAndSetToTinyDB(pullRef, PullList, "PullListToShow");
        getExercisesFromFirebaseAndSetToTinyDB(legsRef, LegsList, "LegsListToShow");
        getExercisesFromFirebaseAndSetToTinyDB(coreRef, CoreList, "CoreListToShow");
    }

    private void getExercisesFromFirebaseAndSetToTinyDB(DatabaseReference ref, ArrayList<Exercise> list, String dbKey){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    Exercise e = new Exercise();
                    String name = s.child("dbName").getValue(String.class);
                    String description = s.child("dbDescription").getValue(String.class);
                    String linkToVideo = s.child("dbLinkToVideo").getValue(String.class);
                    e.setCategory(setCategory(dbKey));
                    e.setName(name);
                    e.setDescription(description);
                    e.setLinkToVideo(linkToVideo);
                    list.add(e);
                }
                tinyDB.putListObject(dbKey, list);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("##################", "failed to handle data", error.toException());
            }
        });
    }

    private Exercise.Category setCategory(String category) {
        Exercise.Category exerciseCategory = null;
        switch (category) {
            case "PushListToShow":
                exerciseCategory = Exercise.Category.Push;
                break;
            case "PullListToShow":
                exerciseCategory = Exercise.Category.Pull;
                break;
            case "LegsListToShow":
                exerciseCategory = Exercise.Category.Legs;
                break;
            case "CoreListToShow":
                exerciseCategory = Exercise.Category.Core;
                break;
        }
        return exerciseCategory;
    }

    private void setButtons() {
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
        addWorkoutOne.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Workout 1");
            startActivity(intent);
        });
        Button addWorkoutTwo = findViewById(R.id.addWorkoutTwo);
        addWorkoutTwo.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Workout 2");
            startActivity(intent);
        });
        Button addWorkoutThree = findViewById(R.id.addWorkoutThree);
        addWorkoutThree.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Workout 3");
            startActivity(intent);
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