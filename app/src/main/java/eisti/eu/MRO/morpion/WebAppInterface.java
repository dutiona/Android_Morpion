package eisti.eu.MRO.morpion;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Michaël on 23/09/2015.
 */
public class WebAppInterface {

    private static final String TAG = "Morpion_WebAppInterface";

    private Morpion ctx_;

    WebAppInterface(Morpion c){
        ctx_ = c;
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(ctx_, text, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Toast call : " + text);
    }

    @JavascriptInterface
    public void addGridElem(int row, int col, String el){
        Log.i(TAG, "Ajout grid element : (" + row + ", " + col + ")=" + el);
        if(!ctx_.getGrid().setValueSafe(row, col, el == "circle" ? Grid.CelElement.Circle : Grid.CelElement.Cross)) {
            showToast("Quelqu'un a déjà joué a sur cette case !");
        }
    }
}
