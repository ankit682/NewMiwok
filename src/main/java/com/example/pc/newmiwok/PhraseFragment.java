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
public class PhraseFragment extends Fragment {

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

    public PhraseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View phraseView = inflater.inflate(R.layout.all_activity, container, false);

        //Audio Focus Services
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phraseArrayList = new ArrayList<Word>();

        phraseArrayList.add(new Word("minto wuksus","Where are you going?",R.raw.phrase_where_are_you_going));
        phraseArrayList.add(new Word("tinnә oyaase'nә","What is your name?",R.raw.phrase_what_is_your_name));
        phraseArrayList.add(new Word("oyaaset...","My name is...",R.raw.phrase_my_name_is));
        phraseArrayList.add(new Word("michәksәs?","How are you feeling?",R.raw.phrase_how_are_you_feeling));
        phraseArrayList.add(new Word("kuchi achit","I’m feeling good.",R.raw.phrase_im_feeling_good));
        phraseArrayList.add(new Word("әәnәs'aa?","Are you coming?",R.raw.phrase_are_you_coming));
        phraseArrayList.add(new Word("hәә’ әәnәm","Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        phraseArrayList.add(new Word("әәnәm","I’m coming.",R.raw.phrase_im_coming));
        phraseArrayList.add(new Word("yoowutis","Let’s go.",R.raw.phrase_lets_go));
        phraseArrayList.add(new Word("әnni'nem","Come here.",R.raw.phrase_come_here));

        WordAdapter phraseAdapter = new WordAdapter(getActivity(), phraseArrayList, R.color.category_phrases);

        ListView listView = (ListView) phraseView.findViewById(R.id.all_activity);
        listView.setAdapter(phraseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = phraseArrayList.get(position);

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

        return phraseView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
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
