package com.example.CaliFit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> exercisesToShow = new ArrayList<>();
    private int PushCounter;
    private int PullCounter;
    private int LegsCounter;
    private int CoreCounter;

    private TextView exerciseName;
    private TextView exerciseDescription;
    private VideoView videoView;
    private DatabaseReference ref;
    private DatabaseReference lengthRef;
    private String eName = "";
    private String eDescription = "";
    private String eLink = "";


    ExerciseActivityPresenter exerciseActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent receivedIntent = getIntent();
        String screenCat = receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT);
        System.out.println("screen category is: " + screenCat);

        exerciseActivityPresenter = new ExerciseActivityPresenter(this);

        //Set the top name TextView to push/pull/legs/core
        TextView namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        namePreviewExercise.setText(screenCat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        lengthRef = database.getReference("AllLengths");
        ref = database.getReference(screenCat);

        exerciseName = (TextView) findViewById(R.id.exerciseName);
        exerciseDescription = (TextView) findViewById(R.id.exerciseDescription);

        exercisesToShow.clear();
        lengthRef.child(screenCat).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(ExerciseActivity.this, "database error", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i <= Integer.parseInt((String) task.getResult().getValue()); i++) {
                        Exercise exerciseToAdd = new Exercise();
                        ref.child(String.valueOf(i)).child("dbName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ExerciseActivity.this, "database error", Toast.LENGTH_SHORT).show();

                                } else {
                                    exerciseToAdd.setName((String) task.getResult().getValue());
                                    //name.valueOf(task.getResult().getValue());
                                    //eName.replace("", (String) task.getResult().getValue());
                                    //String eeeName = (String) task.getResult().getValue();
                                    //setString((String) task.getResult().getValue(), ename);
                                    //eName = eeeName;
                                }
                            }
                        });
                        ref.child(String.valueOf(i)).child("dbDescription").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ExerciseActivity.this, "database error", Toast.LENGTH_SHORT).show();
                                } else {
                                    exerciseToAdd.setDescription((String) task.getResult().getValue());
                                }
                            }
                        });
                        ref.child(String.valueOf(i)).child("dbLinkToVideo").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ExerciseActivity.this, "database error", Toast.LENGTH_SHORT).show();
                                } else {
                                    exerciseToAdd.setLinkToVideo((String) task.getResult().getValue());
                                }
                            }
                        });
                        Exercise.Category exerciseCategory = null;
                        switch (screenCat) {
                            case "Push":
                                exerciseCategory = Exercise.Category.Push;
                                break;
                            case "Pull":
                                exerciseCategory = Exercise.Category.Pull;
                                break;
                            case "Legs":
                                exerciseCategory = Exercise.Category.Legs;
                                break;
                            case "Core":
                                exerciseCategory = Exercise.Category.Core;
                                break;
                        }
                        exerciseToAdd.setCategory(exerciseCategory);
                        exerciseToAdd.printName();
                        exercisesToShow.add(exerciseToAdd);
                        //System.out.println(exerciseToAdd.name);
                    }
                }
            }
        });



        //get all exercises from respective category and prepare to show them

    }

    public void setString(String StringToSave, String WhereToSave) {
        WhereToSave = StringToSave;
    }

    @Override
    public void finish() {

        Intent toGive = new Intent();
        toGive.putExtra("Exercises", exercises);
        System.out.println(exercises);
        setResult(RESULT_OK, toGive);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}