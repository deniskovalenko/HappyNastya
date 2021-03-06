package com.pzhackers.happynastya.fragment;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pzhackers.happynastya.R;
import com.pzhackers.happynastya.presenter.MainPresenter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView text;
    private ImageView background;
    private MainPresenter presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        text = (TextView) view.findViewById(R.id.text);
        background = (ImageView) view.findViewById(R.id.bg_view);
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
        background.setImageDrawable(image);
    }

    public void setSentence(String adverb, String adjective){
        text.setText("Ты \n" + adverb + "\n " + adjective + "!!!");
    }

    public void setTextGradient(){
        int[] color = {Color.WHITE,Color.GRAY};
        float[] position = {0, 1};
        Shader.TileMode tile_mode = Shader.TileMode.REPEAT;
        LinearGradient lin_grad = new LinearGradient(0, 20, 0, 300, color, position, tile_mode);
        text.getPaint().setShader(lin_grad);
    }
}
