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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private WordController wordController;

    public DisplayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void addWordButtonClicked(String enteredWord){
        //TODO:Rename this function
        wordController = new WordController();
        APIService apiService = wordController.getWordAPI(enteredWord);

        Call<Word> call = apiService.getWord(Constants.apiKey,Constants.lang,enteredWord);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if(response.isSuccessful()) {
                    Word word = response.body();
                    Def translatedWord = word.getDef().get(0);
                    parseTranslatedWord(translatedWord);
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

    public void parseTranslatedWord(Def translatedWord){
        List<Tr> tr = translatedWord.getTr();
        System.out.println(tr);
    }
}