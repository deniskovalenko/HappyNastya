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
    ArrayList<String> adverbs;
    ArrayList<String> adjectives;
    ArrayList<Drawable> photos;
    private Random random = new Random();

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
            int count = 1;
            int elements = countPhotos();
            while (count <= elements){
                InputStream photosReader = openImageFile(count);
                Drawable photo = Drawable.createFromStream(photosReader, null);
                photos.add(photo);
                count++;
            }
        } catch (Exception e) {
            Toast.makeText(fragment.getContext(),"Failed to load words", Toast.LENGTH_LONG).show();
        }
    }

    private String newAdverb() {
        int maxIndex = adverbs.size();
        int randomIndex = random.nextInt(maxIndex);
        return adverbs.get(randomIndex);
    }

    private String newAdjective() {
        int maxIndex = adjectives.size();
        int randomIndex = random.nextInt(maxIndex);
        return adjectives.get(randomIndex);
    }

    private Drawable newPhoto() {
        int maxIndex = photos.size();
        int randomIndex = random.nextInt(maxIndex);
        return photos.get(randomIndex);
    }

    private BufferedReader openTextFile(String name) throws IOException {
        AssetManager am = fragment.getContext().getAssets();
        return new BufferedReader(new InputStreamReader(am.open(name)));
    }

    private InputStream openImageFile(int number) throws IOException {
        return fragment.getContext().getAssets().open("photo/" + number + ".jpg");
    }

    private int countPhotos() throws IOException {
        AssetManager am = fragment.getContext().getAssets();
        return am.list("photo").length;
    }
}
