package eisti.eu.MRO.morpion;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Michaël on 23/09/2015.
 */
public class WebAppInterface {
    Context ctx_;

    WebAppInterface(Context c){
        ctx_ = c;
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(ctx_, text, Toast.LENGTH_SHORT).show();
    }
}
