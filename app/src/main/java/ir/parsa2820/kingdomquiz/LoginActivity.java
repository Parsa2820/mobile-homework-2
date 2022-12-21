package ir.parsa2820.kingdomquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "state";
    private UserDao userDao;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView errorTextView;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        errorTextView = findViewById(R.id.textViewError);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "local-database").allowMainThreadQueries().build();
        userDao = db.userDao();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("currentUser", currentUser);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currentUser = settings.getString("currentUser", null);
        if (currentUser != null) {
            goToMain();
        }
    }

    public void register(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        User user;
        try {
            user = new User(email, password);
        } catch (Exception e) {
            errorTextView.setText("Invalid email format");
            return;
        }
        try {
            userDao.insertAll(user);
            errorTextView.setText("Registered successfully");
        } catch (SQLiteConstraintException e) {
            errorTextView.setText("Email already exists");
        }
    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        User user = userDao.findByEmail(email);
        if (user == null) {
            errorTextView.setText("Email does not exists");
        } else if (user.getPassword().equals(password)) {
            errorTextView.setText("Login successfully");
            currentUser = email;
            goToMain();
        } else {
            errorTextView.setText("Incorrect password");
        }
    }

    private void goToMain() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("currentUser", currentUser);
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}