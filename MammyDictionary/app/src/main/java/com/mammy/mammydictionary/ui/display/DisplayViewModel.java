package com.mammy.mammydictionary.ui.display;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mammy.mammydictionary.controller.Constants;
import com.mammy.mammydictionary.controller.WordController;
import com.mammy.mammydictionary.controller.restapi.APIService;
import com.mammy.mammydictionary.model.Def;
import com.mammy.mammydictionary.model.Tr;
import com.mammy.mammydictionary.model.Word;
import com.mammy.mammydictionary.repository.WordEntity;
import com.mammy.mammydictionary.repository.WordRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayViewModel extends ViewModel {

    private MutableLiveData<List<String>> meaning;
    private WordController wordController;
    private WordRepository wordRepository;
    private WordEntity wordEntity;

    public DisplayViewModel() {
        meaning = new MutableLiveData<>();
    }

    public LiveData<List<String>> getText() {
        return meaning;
    }

    public void addWordButtonClicked(String enteredWord,WordRepository wordRepository){
        //TODO:Rename this function
        this.wordRepository = wordRepository;
        wordEntity = new WordEntity();
        translateWord(enteredWord);
    }

    public void translateWord(String enteredWord){
        wordEntity.setWord(enteredWord);
        WordEntity foundWord = wordRepository.getWord(enteredWord);
        if(foundWord == null)
            getWordFromAPI(enteredWord);
        else {
            List<String> meanList = convertMeaningtoList(foundWord.getMeaning());
            this.meaning.setValue(meanList);

        }
    }

    private void getWordFromAPI(String enteredWord){
        wordController = new WordController();
        APIService apiService = wordController.getWordAPI(enteredWord);

        Call<Word> call = apiService.getWord(Constants.apiKey,Constants.lang,enteredWord);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if(response.isSuccessful()) {
                    Word word = response.body();
                    getMeaningTranslatedWord(word);
                    addDatabase();
                } else {
                    Log.println(Log.ERROR,"WordController",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMeaningTranslatedWord(Word translatedWord){
        List<String> meaningList = new ArrayList<>();
        List<Def> defList = translatedWord.getDef();
        for (Def def : defList){
            List<Tr> trList = def.getTr();
            for (Tr tr : trList){
                meaningList.add(tr.getText().toLowerCase());
            }
        }
        setMeaning(meaningList);
    }

    public void setMeaning(List<String> meaning) {
        this.meaning.setValue(meaning);
        wordEntity.setMeaning(convertMeaningtoString(meaning));
    }

    private void addDatabase(){
        wordRepository.insertWord(wordEntity);
        List<WordEntity> wordEntityList = wordRepository.getAllWords();
        System.out.println(wordEntityList.toString());
    }

    public String convertMeaningtoString(List<String> meaning) {
        String totalMean = "";
        for (String mean:meaning) {
            totalMean += mean + ", ";
        }
        return totalMean.substring(0,totalMean.length() - 2); // Son virgülü silmek için
        //TODO: Daha iyi bir çözüm araştır
    }

    public List<String> convertMeaningtoList(String meaning) {
        List<String> meaningList = new ArrayList<String>(Arrays
                .asList(meaning.split(",")));
        return meaningList;
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}