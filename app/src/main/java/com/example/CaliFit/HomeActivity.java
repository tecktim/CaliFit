package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class HomeActivity extends AppCompatActivity {

    //Dev branch init
    private DatabaseReference pushRef;
    private DatabaseReference pullRef;
    private DatabaseReference legsRef;
    private DatabaseReference coreRef;
    private final ArrayList<Exercise> PushList = new ArrayList<>();
    private final ArrayList<Exercise> PullList = new ArrayList<>();
    private final ArrayList<Exercise> LegsList = new ArrayList<>();
    private final ArrayList<Exercise> CoreList = new ArrayList<>();
    private Button addWorkoutOne;
    private Button addWorkoutTwo;
    private Button addWorkoutThree;
    private static TinyDB tinyDB;
    private static Toolbar toolbar;

    public static TinyDB getDB(){
        return tinyDB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#656565"));
        setSupportActionBar(toolbar);
        setButtons();

        if (tinyDB == null) {
            tinyDB = new TinyDB(this);
        }
        handleDataOnStart();
        setWorkoutNames();
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
                    String sets = s.child("dbSets").getValue(String.class);
                    String reps = s.child("dbReps").getValue(String.class);

                    e.setCategory(setCategory(dbKey));
                    e.setName(name);
                    e.setDescription(description);
                    e.setLinkToVideo(linkToVideo);
                    e.setSets(sets);
                    e.setReps(reps);
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
        Button addWorkoutBeginner = findViewById(R.id.addWorkoutBeginner);
        addWorkoutBeginner.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Anfänger");
            startActivity(intent);
        });
        Button addWorkoutAdvanced = findViewById(R.id.addWorkoutAdvanced);
        addWorkoutAdvanced.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Fortgeschritten");
            startActivity(intent);
        });
        Button aboutButton = findViewById(R.id.aboutButton);

        aboutButton.setOnClickListener(view -> openAboutIntent());

        TextView text = (TextView) findViewById(R.id.namePreviewHome);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setPadding(8, 40,8,0);


        addWorkoutOne = findViewById(R.id.addWorkoutOne);
        addWorkoutOne.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Workout 1");
            startActivity(intent);
        });
        addWorkoutTwo = findViewById(R.id.addWorkoutTwo);
        addWorkoutTwo.setOnClickListener(v -> {
            Intent intent  = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Workout 2");
            startActivity(intent);
        });
        addWorkoutThree = findViewById(R.id.addWorkoutThree);
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
    public void onRestart(){
        super.onRestart();
        setWorkoutNames();
    }

    private void setWorkoutNames() {
        if(!tinyDB.getString("titleOf"+"Workout 1").isEmpty()){addWorkoutOne.setText(tinyDB.getString("titleOf"+"Workout 1"));}
        if(!tinyDB.getString("titleOf"+"Workout 2").isEmpty()){addWorkoutTwo.setText(tinyDB.getString("titleOf"+"Workout 2")); }
        if(!tinyDB.getString("titleOf"+"Workout 3").isEmpty()){addWorkoutThree.setText(tinyDB.getString("titleOf"+"Workout 3"));}
    }
}