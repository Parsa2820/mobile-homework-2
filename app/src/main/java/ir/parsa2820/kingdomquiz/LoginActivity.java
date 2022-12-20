package ir.parsa2820.kingdomquiz;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class LoginActivity extends AppCompatActivity {
    private UserDao userDao;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView errorTextView;

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
        } else {
            errorTextView.setText("Incorrect password");
        }
    }

    private void goToMain() {

    }
}