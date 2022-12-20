package ir.parsa2820.kingdomquiz;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ir.parsa2820.kingdomquiz.model.*;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

