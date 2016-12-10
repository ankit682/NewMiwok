package com.example.pc.newmiwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListiaener =  new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View colorView = inflater.inflate(R.layout.all_activity, container, false);

        //Audio Focus Services
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colorArrayList = new ArrayList<Word>();

        colorArrayList.add(new Word("weṭeṭṭi","red",R.drawable.color_red,R.raw.color_red));
        colorArrayList.add(new Word("chokokki","green",R.drawable.color_green,R.raw.color_green));
        colorArrayList.add(new Word("ṭakaakki","brown",R.drawable.color_brown,R.raw.color_brown));
        colorArrayList.add(new Word("ṭopoppi","gray",R.drawable.color_gray,R.raw.color_gray));
        colorArrayList.add(new Word("kululli","black",R.drawable.color_black,R.raw.color_black));
        colorArrayList.add(new Word("kelelli","white",R.drawable.color_white,R.raw.color_white));
        colorArrayList.add(new Word("ṭopiisә","dusty yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colorArrayList.add(new Word("chiwiiṭә","mustard yellow",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter colorAdapter = new WordAdapter(getActivity(), colorArrayList, R.color.category_colors);

        ListView listView = (ListView) colorView.findViewById(R.id.all_activity);
        listView.setAdapter(colorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = colorArrayList.get(position);

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListiaener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Release media player if it currently exists because we want play different audio
                    releaseMediaPlayer();

                    //MediaPlayer create audio current word
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getMediaId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return colorView;
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){

        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(onAudioFocusChangeListiaener);
        }
    }
}
