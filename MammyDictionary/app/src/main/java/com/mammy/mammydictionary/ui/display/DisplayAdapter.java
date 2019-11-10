package com.mammy.mammydictionary.ui.display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.mammy.mammydictionary.R;
import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.DisplayViewHolder> {
    private List<String> mDataset;

    public static class DisplayViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public DisplayViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.word_meaning);
        }

    }

    public DisplayAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DisplayAdapter.DisplayViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_meaning_row, parent, false);
        DisplayViewHolder vh = new DisplayViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DisplayViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
