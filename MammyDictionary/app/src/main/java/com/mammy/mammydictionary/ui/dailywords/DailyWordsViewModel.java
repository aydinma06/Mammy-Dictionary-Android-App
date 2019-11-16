package com.mammy.mammydictionary.ui.dailywords;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mammy.mammydictionary.repository.WordEntity;
import com.mammy.mammydictionary.repository.WordRepository;

import java.util.List;

public class DailyWordsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DailyWordsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<WordEntity> getRandomFiveWords (WordRepository wordRepository){
        return wordRepository.getRandomFiveWords();
    }
}