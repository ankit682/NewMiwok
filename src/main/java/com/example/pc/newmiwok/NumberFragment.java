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
public class NumberFragment extends Fragment {

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

    public NumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View numberView = inflater.inflate(R.layout.all_activity, container, false);

        //Audio Focus Services
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Create an array of numberList
        final ArrayList<Word> numberList = new ArrayList<Word>();

        numberList.add(new Word("lutti","one",R.drawable.number_one,R.raw.number_one));
        numberList.add(new Word("otiiko","two",R.drawable.number_two,R.raw.number_two));
        numberList.add(new Word("tolookosu","three",R.drawable.number_three,R.raw.number_three));
        numberList.add(new Word("oyyisa","four",R.drawable.number_four,R.raw.number_four));
        numberList.add(new Word("massokka","five",R.drawable.number_five,R.raw.number_five));
        numberList.add(new Word("temmokka","six",R.drawable.number_six,R.raw.number_six));
        numberList.add(new Word("kenekaku","seven",R.drawable.number_seven,R.raw.number_seven));
        numberList.add(new Word("kawinta","eight",R.drawable.number_eight,R.raw.number_eight));
        numberList.add(new Word("wo'e","nine",R.drawable.number_nine,R.raw.number_nine));
        numberList.add(new Word("na'aacha","ten",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter numberAdapter = new WordAdapter(getActivity(), numberList, R.color.category_numbers);

        ListView listView = (ListView) numberView.findViewById(R.id.all_activity);
        listView.setAdapter(numberAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = numberList.get(position);

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
        return numberView;
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
