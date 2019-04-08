package com.nicster34.mealplan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicster34.mealplan.data.IngredientRef;

import java.util.LinkedList;

public class KatiesAdapter extends RecyclerView.Adapter<KatiesAdapter.WordViewHolder> {

    private final LinkedList<IngredientRef> mWordList;
    private LayoutInflater mInflater;

    public KatiesAdapter(Context context, LinkedList<IngredientRef> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @NonNull
    @Override
    public KatiesAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.meallist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(KatiesAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position).toString();
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final KatiesAdapter mAdapter;

        public WordViewHolder(View itemView, KatiesAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }
}