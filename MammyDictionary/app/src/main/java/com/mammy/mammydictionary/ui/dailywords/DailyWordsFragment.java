package com.mammy.mammydictionary.ui.dailywords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.repository.WordEntity;
import com.mammy.mammydictionary.repository.WordRepository;

import java.util.List;

public class DailyWordsFragment extends Fragment {


    private RecyclerView recyclerView;
    private DailyWordsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DailyWordsViewModel dailyWordsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyWordsViewModel =
                ViewModelProviders.of(this).get(DailyWordsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dailywords, container, false);

        recyclerView = root.findViewById(R.id.recycler_view_dailywords);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        WordRepository wordRepository = new WordRepository(getContext());
        List<WordEntity> wordEntities = dailyWordsViewModel.getRandomFiveWords(wordRepository);

        mAdapter = new DailyWordsAdapter(wordEntities);
        recyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        return root;
    }
}