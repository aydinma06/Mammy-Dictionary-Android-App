package com.mammy.mammydictionary.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void addWord(WordEntity word);

    @Insert
    void addMoreThanOneWord(List<WordEntity> words);

    @Query("SELECT * FROM WordDetails")
    List<WordEntity> getAllWords();

    @Query("SELECT * FROM WordDetails where word LIKE  :searchingWord")
    WordEntity findWord(String searchingWord);

    @Update
    void updateWord(WordEntity word);

    @Delete
    void deleteWord(WordEntity word);
}
