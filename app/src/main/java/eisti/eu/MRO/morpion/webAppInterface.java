package eisti.eu.MRO.morpion;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Michaël on 23/09/2015.
 */
public class WebAppInterface {
    Morpion ctx_;

    WebAppInterface(Morpion c){
        ctx_ = c;
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(ctx_, text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void addGridElem(int row, int col, String el){
        if(!ctx_.getGrid().setValueSafe(row, col, el == "circle" ? Grid.CelElement.Circle : Grid.CelElement.Cross)) {
            showToast("There's already a move on this cell !");
        }
    }
}
