package eisti.eu.MRO.morpion;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Michaël on 24/09/2015.
 */
public class DifficultyConsistency implements View.OnTouchListener {

    public enum DifficultyLevel{
        EASY, NORMAL, IMPOSSIBLE
    }

    private ArrayList<ToggleButton> buttons_;
    private DifficultyLevel difficulty_;

    public DifficultyConsistency(DifficultyLevel difficulty, ArrayList<ToggleButton> buttons){
        difficulty_ = difficulty;
        buttons_ = buttons;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}
