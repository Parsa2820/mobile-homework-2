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

    public User(@NonNull String email, @NonNull String password) throws Exception {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception();
        }
        this.email = email;
        this.password = password;
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
}
