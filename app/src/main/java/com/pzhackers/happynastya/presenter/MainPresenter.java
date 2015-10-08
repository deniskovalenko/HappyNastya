package com.pzhackers.happynastya.presenter;


import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.pzhackers.happynastya.fragment.MainActivityFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainPresenter {
    MainActivityFragment fragment;
    ArrayList<String> adverbs = new ArrayList<>();
    ArrayList<String> adjectives = new ArrayList<>();
    String[] photos;
    private Random randomWord = new Random();
    private Random randomPhoto =  new Random();

    public MainPresenter(MainActivityFragment fragment) {
        this.fragment = fragment;
        fragment.setTextGradient();
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
                if (!adverb.endsWith("ий") && !adverb.endsWith("ый") && !adverb.endsWith("ой")) {
                    adverbs.add(adverb);
                }
            }
            BufferedReader readerAdjectives = openTextFile("adjective.txt");
            while (readerAdjectives.ready()) {
                String adjective = readerAdjectives.readLine();
                if (adjective.endsWith("ий") || adjective.endsWith("ый") || adjective.endsWith("ой")) {
                    String womanAdjective = adjective.substring(0,adjective.length()-2) + "ая";
                    adjectives.add(womanAdjective);
                }
            }
            readPhotosName();
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
            InputStream stream = fragment.getActivity().getAssets().open("photo/" + photos[randomIndex]);
            photo = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            Toast.makeText(fragment.getActivity(),"Failed to create photo", Toast.LENGTH_LONG).show();
        }
        return photo;
    }


    private BufferedReader openTextFile(String name) throws IOException {
        AssetManager am = fragment.getActivity().getAssets();
        return new BufferedReader(new InputStreamReader(am.open(name)));
    }

    private void readPhotosName() throws IOException {
        AssetManager am = fragment.getActivity().getAssets();
        photos = am.list("photo");
    }
}
