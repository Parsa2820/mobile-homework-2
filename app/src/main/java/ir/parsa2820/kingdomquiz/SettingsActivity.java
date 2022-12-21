package ir.parsa2820.kingdomquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Arrays;

import ir.parsa2820.kingdomquiz.model.Category;
import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class SettingsActivity extends AppCompatActivity {
    private UserDao userDao;
    private final String[] difficulties = {"ANY", "EASY", "MEDIUM", "HARD"};
    private final String[] numberOfQuestions = {"5", "10", "15", "20", "25", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent baseIntent = getIntent();
        String currentUser = baseIntent.getStringExtra("currentUser");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "local-database").allowMainThreadQueries().build();
        userDao = db.userDao();
        User user = userDao.findByEmail(currentUser);

        SwitchMaterial themeSwitch = findViewById(R.id.switchTheme);
        themeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        Spinner difficultySpinner = findViewById(R.id.spinnerDifficulty);
        difficultySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties));
        difficultySpinner.setSelection(Arrays.asList(difficulties).indexOf(user.getDifficulty()));
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setDifficulty(difficulties[i]);
                userDao.update(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner categorySpinner = findViewById(R.id.spinnerCategory);
        String[] categories = new String[Category.values().length];
        for (int i = 0; i < Category.values().length; i++) {
            categories[i] = Category.values()[i].name();
        }
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));
        categorySpinner.setSelection(Arrays.asList(categories).indexOf(user.getCategory()));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setCategory(categories[i]);
                userDao.update(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner numberOfQuestionsSpinner = findViewById(R.id.spinnerNumberOfQuestions);
        numberOfQuestionsSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, numberOfQuestions));
        numberOfQuestionsSpinner.setSelection(Arrays.asList(numberOfQuestions).indexOf(user.getNumberOfQuestions()));
        numberOfQuestionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setNumberOfQuestions(numberOfQuestions[i]);
                userDao.update(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}