package com.example.pc.newmiwok;

/**
 * Created by PC on 02-12-2016.
 */

public class Word {


    private String miwokWord;
    private String englishWord;
    private int srcId = NO_IMAGE_PROVIDED;
    private int mediaId;

    private static final int NO_IMAGE_PROVIDED  = -1;

    public Word(String miwokWord, String englishWord, int srcId, int mediaId) {
        this.miwokWord = miwokWord;
        this.englishWord = englishWord;
        this.srcId = srcId;
        this.mediaId = mediaId;
    }

    public Word(String miwokWord, String englishWord, int mediaId) {
        this.miwokWord = miwokWord;
        this.englishWord = englishWord;
        this.mediaId = mediaId;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public void setMiwokWord(String miwokWord) {
        this.miwokWord = miwokWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public boolean hasImage(){
        return srcId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "miwokWord='" + miwokWord + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", srcId=" + srcId +
                ", mediaId=" + mediaId +
                '}';
    }
}
