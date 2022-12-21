package ir.parsa2820.kingdomquiz.model;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String difficulty;
    private String category;
    private String numberOfQuestions;

    public User(@NonNull String email, @NonNull String password) throws Exception {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception();
        }
        this.email = email;
        this.password = password;
        difficulty = "any";
        category = "any";
        numberOfQuestions = "10";
    }

    public User() {

    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
