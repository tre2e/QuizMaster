package com.example.quizmaster.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quizmaster.data.model.Favorite;
import com.example.quizmaster.data.model.Question;
import com.example.quizmaster.data.model.Wrong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Question.class, Favorite.class, Wrong.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    /** 单例 */
    private static volatile AppDatabase instance;

    /** 线程池：用于所有写操作（insert / update / delete） */
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    /** DAO */
    public abstract QuestionDao questionDao();
    public abstract FavoriteDao favoriteDao();
    public abstract WrongDao wrongDao();

    /** 双重检查锁单例 */
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "quizmaster.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}