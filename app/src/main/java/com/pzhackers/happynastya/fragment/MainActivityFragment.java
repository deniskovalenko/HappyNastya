package com.pzhackers.happynastya.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pzhackers.happynastya.R;
import com.pzhackers.happynastya.presenter.MainPresenter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView text;
    private View background;
    private MainPresenter presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        text = (TextView) view.findViewById(R.id.text);
        background = view.findViewById(R.id.bg_view);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.newStep();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MainPresenter(this);
        presenter.newStep();
    }

    public void setBackground(Drawable image){
        background.setBackground(image);
    }

    public void setSentence(String adverb, String adjective){
        text.setText("Я " + adverb + " " + adjective + "!!!");
        text.setShadowLayer(1, 0, 0, Color.BLACK);
    }
}
