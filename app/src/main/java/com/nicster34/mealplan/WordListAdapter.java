//package com.nicster34.mealplan;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.LinkedList;
//
//public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {
//
//    class WordViewHolder extends RecyclerView.ViewHolder {
//        public final TextView wordItemView;
//        final WordListAdapter mAdapter;
//
//        public WordViewHolder(View itemView, WordListAdapter adapter) {
//            super(itemView);
//            wordItemView = itemView.findViewById(R.id.word);
//            this.mAdapter = adapter;
//        }
//
//    }
//
//    private final LinkedList<String> mWordList;
//    private LayoutInflater mInflater;
//
//    public WordListAdapter(Context context,
//                           LinkedList<String> wordList) {
//        mInflater = LayoutInflater.from(context);
//        this.mWordList = wordList;
//    }
//
//    @Override
//    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
//                                             int viewType) {
//        View mItemView = mInflater.inflate(R.layout.meallist_item,
//                parent, false);
//        return new WordViewHolder(mItemView, this);
//    }
//
//    @Override
//    public void onBindViewHolder(WordViewHolder holder, int position) {
//        String mCurrent = mWordList.get(position);
//        holder.wordItemView.setText(mCurrent);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mWordList.size();
//    }
//}
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

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);

            //get the meal type
            mIntent.putExtra("mealSelection", element);
            mAct.setResult(Activity.RESULT_OK, mIntent);
            mAct.finish();
//            //Change the word in the mWordList.
//            mWordList.set(mPosition, "Clicked! " + element);
//            // Notify the adapter, that the data has changed so it can
//            // update the RecyclerView to display the data.
//            mAdapter.notifyDataSetChanged();
        }
    }

    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    private Context mContext;
    private Intent mIntent;
    private MealChoiceActivity mAct;

    public WordListAdapter(Context context, LinkedList<String> wordList, Intent intent, MealChoiceActivity act) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mWordList = wordList;
        this.mIntent = intent;
        this.mAct = act;
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.meallist_item,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}

