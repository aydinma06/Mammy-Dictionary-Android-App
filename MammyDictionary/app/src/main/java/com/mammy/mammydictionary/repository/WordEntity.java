package com.mammy.mammydictionary.repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "WordDetails")
public class WordEntity implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "word")
    @NonNull
    private String word;

    @ColumnInfo(name = "meaning")
    private String meaning;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}
