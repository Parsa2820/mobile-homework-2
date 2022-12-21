package ir.parsa2820.kingdomquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ir.parsa2820.kingdomquiz.model.Question;
import ir.parsa2820.kingdomquiz.model.QuizResult;
import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String response = intent.getStringExtra("questions");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        Question[] questions = gson.fromJson(jsonObject.get("results"), Question[].class);
        String currentUser = intent.getStringExtra("currentUser");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "local-database").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        User user = userDao.findByEmail(currentUser);

        final RecyclerView questionsRecyclerView = findViewById(R.id.recyclerViewQuestions);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final QuizResult quizResult = new QuizResult();
        questionsRecyclerView.setAdapter(new QuestionsAdapter(questions, quizResult));

        final TextView scoreTextView = findViewById(R.id.textViewScore);
        final Button closeButton = findViewById(R.id.buttonClose);
        closeButton.setEnabled(false);
        closeButton.setOnClickListener(v -> {
            finish();
        });

        final Button doneButton = findViewById(R.id.buttonDoneQuiz);
        doneButton.setOnClickListener(v -> {
            user.setScore(user.getScore() + quizResult.getScore());
            userDao.update(user);
            StringBuilder sb = new StringBuilder();
            sb.append("Your score is: ").append(quizResult.getScore());
            if (user.getScore() >= userDao.getHighestScore().getScore()) {
                sb.append("(You have the highest score!)");
            }
            scoreTextView.setText(sb.toString());
            doneButton.setEnabled(false);
            closeButton.setEnabled(true);
        });

    }
}