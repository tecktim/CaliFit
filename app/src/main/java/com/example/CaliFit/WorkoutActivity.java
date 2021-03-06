package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

//@SuppressWarnings("unchecked")
public class WorkoutActivity extends AppCompatActivity{

    Model model;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private ConstraintLayout constraintLayout;
    private TextView namePreviewWorkout;
    private Button pushButton, pullButton, legsButton, coreButton;
    private ArrayList<Exercise> PushList;
    private ArrayList<Exercise> PullList;
    private ArrayList<Exercise> LegsList;
    private ArrayList<Exercise> CoreList;
    private int tableRowCount;
    private String whichWorkout;
    private Intent receivedIntent;
    private EditText editName;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        receivedIntent = getIntent();

        whichWorkout = (String) receivedIntent.getCharSequenceExtra(Intent.EXTRA_TEXT);

        model = new Model();

        findViewsById();
        HomeActivity.getDB().putString("whichWorkout", whichWorkout);

        getListsFromTinyDB();
        fillLists(model);
        if(whichWorkout.equals("Anfänger") || whichWorkout.equals("Fortgeschritten")) {
            checkIfBeginnerOrAdvanced();
        }else {Toast.makeText(this, "Wähle eine Kategorie, um Übungen hinzuzufügen!", Toast.LENGTH_LONG).show();}
        setButtonHandlers();
        showWorkoutTables();
    }

    private void checkIfBeginnerOrAdvanced() {
        if (whichWorkout.equals("Anfänger")) {
            if(PushList.isEmpty()) {
                addFixedExercise("Liegestütze", "Push");
            }
            if(PullList.isEmpty()) {
                addFixedExercise("Superman", "Pull");
            }
            if(LegsList.isEmpty()) {
                addFixedExercise("Kniebeugen", "Legs");
            }
            if(CoreList.isEmpty()) {
                addFixedExercise("Umgekehrte Bauchpressen", "Core");
            }
        }
        if (whichWorkout.equals("Fortgeschritten")) {
            if(PushList.isEmpty()) {
                addFixedExercise("Diamant Liegestütze", "Push");
                addFixedExercise("Military Press", "Push");
            }
            if(PullList.isEmpty()) {
                addFixedExercise("Superman", "Pull");
                addFixedExercise("Vierfüßlerstand", "Pull");
            }
            if(LegsList.isEmpty()) {
                addFixedExercise("Kniebeugen mit Sprung", "Legs");
                addFixedExercise("Bulgarian Split Squats", "Legs");
            }
            if(CoreList.isEmpty()) {
                addFixedExercise("Liegendes Beinheben", "Core");
                addFixedExercise("Bauchpressen", "Core");
            }
        }
    }

    private void addFixedExercise(String exeName, String category) {
        ArrayList<Exercise> list;
        list = (ArrayList)HomeActivity.getDB().getListObject(category+"ListToShow", Exercise.class);
        for(Exercise e : list) {
            if(e.name.equals(exeName)) {
                switch (category) {
                    case "Push":
                        PushList.add(e);
                        break;
                    case "Pull":
                        PullList.add(e);
                        break;
                    case "Legs":
                        LegsList.add(e);
                        break;
                    case "Core":
                            CoreList.add(e);
                        break;
                }
            }
        }
    }

    private void findViewsById() {
        editName = findViewById(R.id.editName);
        constraintLayout = findViewById(R.id.constraintLayout);
        tableLayout = findViewById(R.id.tableLayout);
        scrollView = findViewById(R.id.scrollView);
        namePreviewWorkout = (TextView) findViewById(R.id.namePreviewWorkout);
        namePreviewWorkout.setText(HomeActivity.getDB().getString("titleOf"+whichWorkout));
        namePreviewWorkout.setTypeface(Typeface.DEFAULT_BOLD);
        if(HomeActivity.getDB().getString("titleOf"+whichWorkout).equals("")){
            namePreviewWorkout.setText(whichWorkout);
        }
        pushButton = (Button) findViewById(R.id.pushButton);
        pullButton = (Button) findViewById(R.id.pullButton);
        legsButton = (Button) findViewById(R.id.legsButton);
        coreButton = (Button) findViewById(R.id.coreButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setButtonHandlers() {
        if(whichWorkout.equals("Anfänger") || whichWorkout.equals("Fortgeschritten")){
            editName.setEnabled(false);
        }
        //https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext/1489895
        editName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&(
                        (keyCode == KeyEvent.KEYCODE_ENTER)) ||  (keyCode == KeyEvent.KEYCODE_SPACE) || (keyCode == KeyEvent.KEYCODE_TAB) || (keyCode == KeyEvent.KEYCODE_COPY)) {
                    // Perform action on key press
                   editName.setText("");

                    return true;
                }
                return false;
            }
        });

        editName.addTextChangedListener(new TextWatcher() {
            private String before = whichWorkout;
            String message;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                before = editName.getText().toString();
                namePreviewWorkout.setText("");
                if(String.valueOf(s).contains("\n") && String.valueOf(s).contains("\r")){
                    s = "";
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                message = editName.getText().toString();
                if(s.toString().compareTo(String.valueOf(before))!=0){
                    editName.setText(message);
                    editName.setSelection(editName.getText().length());
                }
                editName.clearFocus();
                HomeActivity.getDB().putString("titleOf"+whichWorkout, message);
            }
        });
        //push
        pushButton.setOnClickListener(v -> {

            Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Push");
            startActivityForResult(intent, 1);
            setListsToTinyDB();
        });
        //pull
        pullButton.setOnClickListener(v -> {

            Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Pull");
            startActivityForResult(intent, 2);
            setListsToTinyDB();
        });
        //legs
        legsButton.setOnClickListener(v -> {

            Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Legs");
            startActivityForResult(intent, 3);
            setListsToTinyDB();
        });
        //core
        coreButton.setOnClickListener(v -> {

            Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, "Core");
            startActivityForResult(intent, 4);
            setListsToTinyDB();
        });
    }

    private void fillLists(Model model) {
        PushList = (ArrayList) model.getModel().getList(Exercise.Category.Push);
        PullList = (ArrayList) model.getModel().getList(Exercise.Category.Pull);
        LegsList = (ArrayList) model.getModel().getList(Exercise.Category.Legs);
        CoreList = (ArrayList) model.getModel().getList(Exercise.Category.Core);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent toReceive) {
        super.onActivityResult(requestCode, resultCode, toReceive);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ArrayList<Exercise> pushToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    if (!pushToAdd.isEmpty()) {
                        for (Exercise exercise : pushToAdd) {
                            model.getModel().addListItem(exercise);
                            if(model.getModel().PushList.size() == 3) {
                                break;
                            }
                        }
                        HomeActivity.getDB().putListObject("PushExercises"+whichWorkout, pushToAdd);
                    }
                    break;
                case 2:
                    ArrayList<Exercise> pullToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    if (!pullToAdd.isEmpty()) {
                        for (Exercise exercise : pullToAdd) {
                            model.getModel().addListItem(exercise);
                            if(model.getModel().PullList.size() == 3) {
                                break;
                            }
                        }
                        HomeActivity.getDB().putListObject("PullExercises"+whichWorkout, pullToAdd);
                    }
                    break;
                case 3:
                    ArrayList<Exercise> legsToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    if (!legsToAdd.isEmpty()) {
                        for (Exercise exercise : legsToAdd) {
                            model.getModel().addListItem(exercise);
                            if(model.getModel().LegsList.size() == 3) {
                                break;
                            }
                        }
                        HomeActivity.getDB().putListObject("LegsExercises"+whichWorkout, legsToAdd);
                    }
                    break;
                case 4:
                    ArrayList<Exercise> coreToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    if (!coreToAdd.isEmpty()) {
                        for (Exercise exercise : coreToAdd) {
                            model.getModel().addListItem(exercise);
                            if(model.getModel().CoreList.size() == 3) {
                                break;
                            }
                        }
                        HomeActivity.getDB().putListObject("CoreExercises"+whichWorkout, coreToAdd);
                    }
                    break;
                default:
                    return;
            }
        }
    }

    private void showWorkout(ArrayList<Exercise> list){
        for(Exercise e : list) {
            //table row erstellen + params setzen
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            tableRow.setLayoutParams(lp);
            //text view für name
            TextView exerciseName = new TextView(this);
            exerciseName.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            exerciseName.setText(e.name);
            exerciseName.setLayoutParams(lp);
            exerciseName.setTextColor(Color.BLACK);
            exerciseName.setPadding(tableLayout.getLeft(), 50, 20, 20);
            tableRow.addView(exerciseName);
            //text view sets
            TextView viewSets = new TextView(this);
            viewSets.setText("Sets: "+e.sets);
            viewSets.setTextColor(Color.BLACK);
            viewSets.setLayoutParams(lp);
            viewSets.setPadding(0, 50, 10, 20);
            tableRow.addView(viewSets);
            //text view reps
            TextView viewReps = new TextView(this);
            viewReps.setText("Reps: "+e.reps);
            viewReps.setTextColor(Color.BLACK);
            viewReps.setLayoutParams(lp);
            viewReps.setPadding(10, 50, 40, 20);
            tableRow.addView(viewReps);
            //button um zu removen
            Button exeRemove = new Button(this);
            exeRemove.setText("X");
            exeRemove.setTypeface(Typeface.DEFAULT_BOLD);

            exeRemove.setLayoutParams(new TableRow.LayoutParams(100, 150));
            tableRow.addView(exeRemove);
            exeRemove.setBackgroundColor(Color.parseColor("#e56b6f"));
            exeRemove.setOnClickListener(v -> {
                if (whichWorkout.equals("Anfänger") || whichWorkout.equals("Fortgeschritten")) {
                    Toast.makeText(this, "Dieses Workout lässt sich nicht bearbeiten.", Toast.LENGTH_SHORT).show();
                }else{
                model.getModel().getList(e.category).remove(e);
                switch (e.category) {
                    case Push:
                        PushList.remove(e);
                        break;
                    case Pull:
                        PullList.remove(e);
                        break;
                    case Legs:
                        LegsList.remove(e);
                        break;
                    case Core:
                        CoreList.remove(e);
                        break;
                }
                setListsToTinyDB();
                tableLayout.removeView(tableRow);
                tableLayout.removeView(exeRemove);
                tableRowCount--;}
            });
            tableRow.setPadding(8, 10, 8, 10);
            tableLayout.addView(tableRow, tableRowCount);
            tableRowCount++;

        }
    }

    private void showTitle(Exercise.Category cat) {

        TextView header = new TextView(this);
        header.setText(cat + " Übungen:");
        header.setTypeface(Typeface.DEFAULT_BOLD);
        header.setTextColor(Color.BLACK);
        header.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.addView(header, tableRowCount);
        tableRowCount++;
    }

    private void showWorkoutTables(){
        showTitle(Exercise.Category.Push);
        showWorkout(PushList);
        showTitle(Exercise.Category.Pull);
        showWorkout(PullList);
        showTitle(Exercise.Category.Legs);
        showWorkout(LegsList);
        showTitle(Exercise.Category.Core);
        showWorkout(CoreList);

        TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        tableLayout.setLayoutParams(lp2);

        constraintLayout.removeView(scrollView);
        constraintLayout.removeView(namePreviewWorkout);

        scrollView.removeAllViewsInLayout();

        constraintLayout.addView(scrollView);
        scrollView.addView(tableLayout);
        constraintLayout.addView(namePreviewWorkout);

        setContentView(constraintLayout);
    }

    @Override
    public void onRestart(){
        super.onRestart();


        whichWorkout = (String) receivedIntent.getCharSequenceExtra(Intent.EXTRA_TEXT);
        clearScreen();
        showWorkoutTables();
        setListsToTinyDB();
    }

    private void setListsToTinyDB(){
        HomeActivity.getDB().putListObject("PushExercises"+whichWorkout, PushList);
        HomeActivity.getDB().putListObject("PullExercises"+whichWorkout, PullList);
        HomeActivity.getDB().putListObject("LegsExercises"+whichWorkout, LegsList);
        HomeActivity.getDB().putListObject("CoreExercises"+whichWorkout, CoreList);
    }

    private void getListsFromTinyDB(){
        model.setPushList((ArrayList)HomeActivity.getDB().getListObject("PushExercises"+whichWorkout, Exercise.class));
        model.setPullList((ArrayList) HomeActivity.getDB().getListObject("PullExercises"+whichWorkout, Exercise.class));
        model.setLegsList((ArrayList)HomeActivity.getDB().getListObject("LegsExercises"+whichWorkout, Exercise.class));
        model.setCoreList((ArrayList)HomeActivity.getDB().getListObject("CoreExercises"+whichWorkout, Exercise.class));
    }

    private void clearScreen() {
        for(int i = tableRowCount - 1; i >= 0; i--){
            tableLayout.removeViewAt(i);
            tableRowCount--;
        }
    }
}