package eisti.eu.MRO.morpion;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Michaël on 24/09/2015.
 */
public class DifficultyConsistency implements View.OnTouchListener {

    private static final String TAG = "DifficultyConsistency";

    public enum DifficultyLevel {
        EASY, NORMAL, IMPOSSIBLE
    }

    private ArrayList<ToggleButton> buttons_;
    private DifficultyLevel difficulty_;
    private MorpionActivity context_;

    public DifficultyConsistency(MorpionActivity ctx, DifficultyLevel difficulty, ArrayList<ToggleButton> buttons) {
        difficulty_ = difficulty;
        buttons_ = buttons;
        context_ = ctx;

        for (ToggleButton button : buttons_) {
            button.setOnTouchListener(this);
        }

        //Première consistence visuelle manuelle
        if (difficulty_ == DifficultyLevel.EASY) {
            context_.getButtonEasy().setChecked(true);
        } else if (difficulty_ == DifficultyLevel.NORMAL) {
            context_.getButtonNormal().setChecked(true);
        } else if (difficulty_ == DifficultyLevel.IMPOSSIBLE) {
            context_.getButtonImpossible().setChecked(true);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ToggleButton current_button = (ToggleButton) v;
        if (current_button == context_.getButtonEasy()) {
            difficulty_ = DifficultyLevel.EASY;
        } else if (current_button == context_.getButtonNormal()) {
            difficulty_ = DifficultyLevel.NORMAL;
        } else if (current_button == context_.getButtonImpossible()) {
            difficulty_ = DifficultyLevel.IMPOSSIBLE;
        } else {
            return false;
        }

        Log.i(TAG, "Difficulty set to : " + String.valueOf(difficulty_));

        return updateViewConsistency(current_button);
    }

    private boolean updateViewConsistency(ToggleButton current_button) {
        for (ToggleButton button : buttons_) {
            if (button.isChecked()) {
                if (button == current_button) {
                    Log.i(TAG, "Button '" + String.valueOf(button.getText()) + "' unchanged.");
                    //Si return false alors on peut n'avoir aucun bouton d'activé -> mauvais comportement
                    return true;
                } else {
                    button.setChecked(false);
                    Log.i(TAG, "Button '" + String.valueOf(button.getText()) + "' disabled.");
                }
            }
        }
        current_button.setChecked(true);
        Log.i(TAG, "Button '" + String.valueOf(current_button.getText()) + "' enabled.");
        return true;
    }
}
