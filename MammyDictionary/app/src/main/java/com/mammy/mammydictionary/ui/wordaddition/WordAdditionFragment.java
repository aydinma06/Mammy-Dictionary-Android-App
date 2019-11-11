package com.mammy.mammydictionary.ui.wordaddition;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.repository.WordRepository;

import java.util.List;

public class WordAdditionFragment extends Fragment {

    private WordAdditionViewModel wordAdditionViewModel;
    private TextInputEditText textInputWord;
    private MaterialButton buttonAddWord;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wordAdditionViewModel =
                ViewModelProviders.of(this).get(WordAdditionViewModel.class);
        root = inflater.inflate(R.layout.fragment_addition, container, false);
        initializeComponents();
        return root;
    }

    private void initializeComponents(){
        textInputWord = root.findViewById(R.id.text_input_word);
        buttonAddWord = root.findViewById(R.id.button_add_word);
        recyclerView = root.findViewById(R.id.recycler_view_meaning_word);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);


        wordAdditionViewModel.getText().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> s) {
                mAdapter = new WordAdditionAdapter(s);
                recyclerView.setAdapter(mAdapter);
            }
        });
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordAdditionViewModel.hideKeyboardFrom(getContext(),root);
                String enteredWord = textInputWord.getText().toString();
                WordRepository wordRepository = new WordRepository(getContext());
                if(enteredWord.isEmpty())
                    textInputWord.setError("This field cannot be empty.");
                else
                    wordAdditionViewModel.addWordButtonClicked(enteredWord,wordRepository); //TODO : Daha iyi bir çözüm ara
                Log.println(Log.DEBUG,"WordAdditionFragment",enteredWord);

            }
        });
    }

}