package com.example.CaliFit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Button pushButton = findViewById(R.id.push_button);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.push_exercise));
                startActivity(intent);
            }
        });

        Button pullButton = findViewById(R.id.pull_button);
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.pull_exercise));

                startActivity(intent);
            }
        });

        Button legsButton = findViewById(R.id.legs_button);
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.legs_exercise));
                startActivity(intent);
            }
        });


        Button coreButton = findViewById(R.id.core_button);
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.core_exercise));
                startActivity(intent);
            }
        });
    }
}