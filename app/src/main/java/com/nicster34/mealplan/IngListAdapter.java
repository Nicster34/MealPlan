package com.nicster34.mealplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class IngListAdapter extends RecyclerView.Adapter<IngListAdapter.IngViewHolder> {

    class IngViewHolder extends RecyclerView.ViewHolder {

        public IngViewHolder(View itemView, IngListAdapter adapter) {
            super(itemView);
            IngItemView = itemView.findViewById(R.id.word);
            IngItemNum = itemView.findViewById(R.id.num);
            this.mAdapter = adapter;
        }

        public final TextView IngItemView;
        public final TextView IngItemNum;
        final IngListAdapter mAdapter;
    }

    private final LinkedList<String> mWordList;
    private final LinkedList<Number> mNumList;
    private LayoutInflater mInflater;
    private Context mContext;
    private Intent mIntent;

    public IngListAdapter(Context context, LinkedList<String> wordList, LinkedList<Number> numList, Intent intent) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mWordList = wordList;
        this.mNumList = numList;
        this.mIntent = intent;
    }

    @NonNull
    @Override
    public IngListAdapter.IngViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.inglist_item,
                parent, false);
        return new IngViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(IngViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        Number mCurrNum = mNumList.get(position);
        holder.IngItemView.setText(mCurrent);
        holder.IngItemNum.setText(mCurrNum.toString());
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}

