package com.mammy.mammydictionary.ui.display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.mammy.mammydictionary.R;
import com.mammy.mammydictionary.repository.WordEntity;

import java.util.ArrayList;
import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.DisplayViewHolder> implements Filterable {
    private List<WordEntity> wordEntities;
    private List<WordEntity> filteredWordEntities;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();
                if (searchString.isEmpty()) {
                    filteredWordEntities = wordEntities;
                } else {
                    List<WordEntity> tempFilteredList = new ArrayList<>();
                    for (WordEntity wordEntity : wordEntities) {
                        // search for user name
                        if (wordEntity.getWord().toLowerCase().contains(searchString)) {
                            tempFilteredList.add(wordEntity);
                        }
                    }
                    filteredWordEntities = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredWordEntities;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredWordEntities = (List<WordEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class DisplayViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWord;
        public TextView textViewMean;
        public DisplayViewHolder(View v) {
            super(v);
            textViewWord = v.findViewById(R.id.textview_display_word);
            textViewMean = v.findViewById(R.id.textview_display_mean);
        }

    }

    public DisplayAdapter(List<WordEntity> wordEntities) {
        this.filteredWordEntities = wordEntities;
        this.wordEntities = wordEntities;
    }

    @Override
    public DisplayAdapter.DisplayViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_row, parent, false);
        DisplayViewHolder vh = new DisplayViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {
        holder.textViewWord.setText(filteredWordEntities.get(position).getWord());
        holder.textViewMean.setText(filteredWordEntities.get(position).getMeaning());

    }

    @Override
    public int getItemCount() {
        return filteredWordEntities.size();
    }
}
