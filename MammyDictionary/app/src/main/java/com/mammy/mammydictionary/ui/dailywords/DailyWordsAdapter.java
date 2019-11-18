package com.mammy.mammydictionary.ui.dailywords;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.model.Word;
import com.mammy.mammydictionary.repository.WordEntity;
import com.mammy.mammydictionary.repository.WordRepository;

import java.util.ArrayList;
import java.util.List;

public class DailyWordsAdapter extends RecyclerView.Adapter<DailyWordsAdapter.DailyWordsViewHolder> {
    private List<WordEntity> wordEntities;
    private String targetText;
    private List<Boolean> isWord;
    Context context;

    public static class DailyWordsViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWord;
        public DailyWordsViewHolder(View v) {
            super(v);
            textViewWord = v.findViewById(R.id.textview_dailywords);
        }
    }

    public DailyWordsAdapter(Context context, List<WordEntity> wordEntities) {
        this.context = context;
        this.wordEntities = wordEntities;
        isWord = new ArrayList<>();
        for(int i=0;i<wordEntities.size();i++){
            isWord.add(i,true);
        }
    }

    @Override
    public DailyWordsAdapter.DailyWordsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_word_row, parent, false);
        int height = parent.getMeasuredHeight() / 5;
        int width = parent.getMeasuredWidth();
        v.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        DailyWordsViewHolder vh = new DailyWordsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final DailyWordsViewHolder holder, final int position) {
        setAnimation(holder);
        holder.textViewWord.setText(wordEntities.get(position).getWord());
        holder.textViewWord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isWord.get(position)){
                    holder.textViewWord.setText(wordEntities.get(position).getMeaning());
                    holder.textViewWord.setTextColor(context.getResources().getColor(R.color.brightBackgroundText));
                    isWord.set(position,false);
                    setAnimation(holder);
                }
                else {
                    holder.textViewWord.setText(wordEntities.get(position).getWord());
                    holder.textViewWord.setTextColor(context.getResources().getColor(R.color.darkPurple));
                    isWord.set(position,true);
                    setAnimation(holder);
                }
            }
        });
        holder.textViewWord.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
                alertDialogBuilder.setTitle("Well Done.");
                alertDialogBuilder.setMessage("Are you sure you want to delete the word ?");
                alertDialogBuilder.setNegativeButton("Nooooo!",null);
                alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositiveOption(position);
                    }
                } );
                alertDialogBuilder.show();
                return true;
            }
        });
    }

    private void onPositiveOption(int position){
        WordEntity deletedWord = wordEntities.get(position);
        WordRepository wordRepository = new WordRepository(context);
        wordRepository.deleteWord(deletedWord);
        wordEntities.remove(position);
        notifyDataSetChanged();
    }

    private void setAnimation(DailyWordsViewHolder holder){
        final Animation animation = AnimationUtils.loadAnimation(holder.textViewWord.getContext(), R.anim.alpha);
        animation.reset();
        holder.textViewWord.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return wordEntities.size();
    }
}