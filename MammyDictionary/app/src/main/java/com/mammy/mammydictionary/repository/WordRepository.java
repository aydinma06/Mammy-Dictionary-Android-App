package com.mammy.mammydictionary.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;

public class WordRepository {
    private String DB_NAME = "word-database";

    private WordDatabase wordDatabase;

    public WordRepository(Context context) {
        wordDatabase = Room.databaseBuilder(context, WordDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void insertWord(String word,
                           String mean) {

        WordEntity wordEntity = new WordEntity();
        wordEntity.setWord(word);
        wordEntity.setMeaning(mean);

        insertWord(wordEntity);
    }

    public void insertWord(final WordEntity word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().addWord(word);
                return null;
            }
        }.execute();
    }

    public void updateWord(final WordEntity word) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().updateWord(word);
                return null;
            }
        }.execute();
    }


    public void deleteWord(final WordEntity word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().deleteWord(word);
                return null;
            }
        }.execute();
    }

    public WordEntity getWord(String word) {
        return wordDatabase.wordDao().findWord(word);
    }

    public List<WordEntity> getAllWords() {
        return wordDatabase.wordDao().getAllWords();
    }
}
