package com.mammy.mammydictionary.ui.display;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mammy.mammydictionary.controller.Constants;
import com.mammy.mammydictionary.controller.WordController;
import com.mammy.mammydictionary.controller.restapi.APIService;
import com.mammy.mammydictionary.model.Def;
import com.mammy.mammydictionary.model.Tr;
import com.mammy.mammydictionary.model.Word;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayViewModel extends ViewModel {

    private MutableLiveData<List<String>> meaning;
    private WordController wordController;

    public DisplayViewModel() {
        meaning = new MutableLiveData<>();
    }

    public LiveData<List<String>> getText() {
        return meaning;
    }

    public void addWordButtonClicked(String enteredWord){
        //TODO:Rename this function
        translateWord(enteredWord);
    }

    public void translateWord(String enteredWord){
        wordController = new WordController();
        APIService apiService = wordController.getWordAPI(enteredWord);

        Call<Word> call = apiService.getWord(Constants.apiKey,Constants.lang,enteredWord);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if(response.isSuccessful()) {
                    Word word = response.body();
                    getMeaningTranslatedWord(word);
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
    }
}