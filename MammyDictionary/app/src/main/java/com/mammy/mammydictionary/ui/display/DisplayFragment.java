package com.mammy.mammydictionary.ui.display;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.controller.WordController;

public class DisplayFragment extends Fragment {

    private DisplayViewModel displayViewModel;
    private TextInputEditText textInputWord;
    private Button buttonAddWord;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        displayViewModel =
                ViewModelProviders.of(this).get(DisplayViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initializeComponents();
        return root;
    }

    private void initializeComponents(){
        textInputWord = root.findViewById(R.id.text_input_word);
        buttonAddWord = root.findViewById(R.id.button_add_word);
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredWord = textInputWord.getText().toString();
                displayViewModel.addWordButtonClicked(enteredWord);
                Log.println(Log.DEBUG,"DisplayFragment",enteredWord);
            }
        });
    }

}