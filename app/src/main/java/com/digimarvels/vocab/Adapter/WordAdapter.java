package com.digimarvels.vocab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digimarvels.vocab.Model.WordsModel;
import com.digimarvels.vocab.R;

import java.util.ArrayList;

import static com.digimarvels.vocab.R.id.lblDefine;

/**
 * Created by Rakeshk on 16-08-2017.
 */

public class WordAdapter extends BaseAdapter{
    Context context;
    ArrayList<WordsModel> wordList;

    public WordAdapter(Context context, ArrayList<WordsModel> wordList) {
        this.context = context;
        this.wordList = wordList;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_vocab,parent,false);
            holder = new ViewHolder();
            assert convertView != null;

            holder.lblDefine = (TextView) convertView.findViewById(lblDefine);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblDefine.setText(wordList.get(position).getStrWord() + " -> " + wordList.get(position).getStrDefine());

        return convertView;
    }

    static class ViewHolder{
        TextView lblDefine;
    }
}