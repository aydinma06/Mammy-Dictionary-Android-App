package com.mammy.mammydictionary.ui.dailywords;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.repository.WordEntity;

import java.util.List;

public class DailyWordsAdapter extends RecyclerView.Adapter<DailyWordsAdapter.DailyWordsViewHolder>  {
    private List<WordEntity> wordEntities;


    public static class DailyWordsViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWord;
        public TextView textViewMean;
        public DailyWordsViewHolder(View v) {
            super(v);
            textViewWord = v.findViewById(R.id.textview_display_word);
            textViewMean = v.findViewById(R.id.textview_display_mean);
        }
    }

    public DailyWordsAdapter(List<WordEntity> wordEntities) {
        this.wordEntities = wordEntities;
    }

    @Override
    public DailyWordsAdapter.DailyWordsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_row, parent, false);
        DailyWordsViewHolder vh = new DailyWordsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DailyWordsViewHolder holder, int position) {
        holder.textViewWord.setText(wordEntities.get(position).getWord());
        holder.textViewMean.setText(wordEntities.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return wordEntities.size();
    }
}