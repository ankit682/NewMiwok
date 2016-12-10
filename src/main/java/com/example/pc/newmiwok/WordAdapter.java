package com.example.pc.newmiwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PC on 02-12-2016.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int backgroundColorId;

    public WordAdapter(Activity context, ArrayList<Word> wordArrayList, int backColorId){
        super(context, 0 , wordArrayList);
        backgroundColorId = backColorId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get {@link Word} Object located at this position in the list
        Word currentWord = getItem(position);

        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_word,parent,false);
        }

        //Find TextView in the list_word.xml with ID miwok_text
        TextView miwokName = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokName.setText(currentWord.getMiwokWord());

        //Find TextView in the list_word.xml with ID english_text
        TextView englishName = (TextView) listItemView.findViewById(R.id.english_text_view);
        englishName.setText(currentWord.getEnglishWord());

        //Find ImageView in the list_word.xml with ID image_view
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);

        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getSrcId());
            //Make sure the View visible
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        //Different background color According to activity
        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),backgroundColorId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
