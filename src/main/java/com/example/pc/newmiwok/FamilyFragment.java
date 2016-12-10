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
public class FamilyFragment extends Fragment {

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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View familyView = inflater.inflate(R.layout.all_activity, container, false);

        //Audio Focus Services
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> familyArrayList = new ArrayList<Word>();

        familyArrayList.add(new Word("әpә","father",R.drawable.family_father,R.raw.family_father));
        familyArrayList.add(new Word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        familyArrayList.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        familyArrayList.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        familyArrayList.add(new Word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyArrayList.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyArrayList.add(new Word("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyArrayList.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyArrayList.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyArrayList.add(new Word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));


        WordAdapter familyAdapter = new WordAdapter(getActivity(), familyArrayList, R.color.category_family);

        ListView listView = (ListView) familyView.findViewById(R.id.all_activity);
        listView.setAdapter(familyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = familyArrayList.get(position);

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
        return familyView;
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
