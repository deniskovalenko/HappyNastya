package com.pzhackers.happynastya.presenter;


import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.pzhackers.happynastya.fragment.MainActivityFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainPresenter {
    MainActivityFragment fragment;
    ArrayList<String> adverbs;
    ArrayList<String> adjectives;
    String[] photos;
    private Random randomWord = new Random();
    private Random randomPhoto =  new Random();

    public MainPresenter(MainActivityFragment fragment) {
        this.fragment = fragment;
        init();
    }

    public void newStep() {
        String adverb = newAdverb();
        String adjective = newAdjective();

        Drawable image = newPhoto();

        fragment.setBackground(image);
        fragment.setSentence(adverb, adjective);
    }

    private void init() {
        try {
            BufferedReader readerAdverbs = openTextFile("adverb.txt");
            while (readerAdverbs.ready()) {
                String adverb = readerAdverbs.readLine();
                if (!adverb.endsWith("ий") || !adverb.endsWith("ый")) {
                    adverbs.add(adverb);
                }
            }
            BufferedReader readerAdjectives = openTextFile("adverb.txt");
            while (readerAdjectives.ready()) {
                String adjective = readerAdjectives.readLine();
                if (adjective.endsWith("ий") && adjective.endsWith("ый")) {
                    adverbs.add(adjective);
                }
            }
            openPhotos();
        } catch (Exception e) {
            Toast.makeText(fragment.getContext(),"Failed to load words or photos", Toast.LENGTH_LONG).show();
        }
    }

    private String newAdverb() {
        int maxIndex = adverbs.size();
        int randomIndex = randomWord.nextInt(maxIndex);
        return adverbs.get(randomIndex);
    }

    private String newAdjective() {
        int maxIndex = adjectives.size();
        int randomIndex = randomWord.nextInt(maxIndex);
        return adjectives.get(randomIndex);
    }

    private Drawable newPhoto() {
        int maxIndex = photos.length;
        int randomIndex = randomPhoto.nextInt(maxIndex);
        Drawable photo = null;
        try {
             photo = Drawable.createFromPath(String.valueOf(fragment.getContext().getAssets().openFd("photo/" + photos[randomIndex])));
        } catch (IOException e) {
            Toast.makeText(fragment.getContext(),"Failed to create photo", Toast.LENGTH_LONG).show();
        }
        return photo;
    }


    private BufferedReader openTextFile(String name) throws IOException {
        AssetManager am = fragment.getContext().getAssets();
        return new BufferedReader(new InputStreamReader(am.open(name)));
    }

    private void openPhotos() throws IOException {
        AssetManager am = fragment.getContext().getAssets();
        photos = am.list("photo");
    }
}
