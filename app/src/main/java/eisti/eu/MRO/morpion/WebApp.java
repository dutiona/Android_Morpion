package eisti.eu.MRO.morpion;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Michaël on 23/09/2015.
 */
public class WebApp {

    private static final String TAG = "WebApp";

    private MorpionActivity context_;

    public WebApp(MorpionActivity c) {
        context_ = c;
    }


    public void newGame() {
        callJavaScript(context_.getWebView(), "triggerCleanup");
    }

    public void showCrossedLine() {
        callJavaScript(context_.getWebView(), "triggerCrossedLine", context_.getGameEngine().getWinningCombination());
    }

    @JavascriptInterface
    public void showToast(String text) {
        context_.showToast(text);
    }

    @JavascriptInterface
    public boolean addGridElem(int row, int col, String el) {
        Log.i(TAG, "Ajout index element : (" + row + ", " + col + ")=" + el);
        boolean allowed = context_.getGrid().setValueSafe(row, col, el == "circle" ? Grid.CelElement.Circle : Grid.CelElement.Cross);
        if (!allowed) {
            showToast(context_.getResources().getString(R.string.toast_cell_already_used));
        } else {
            context_.getGameEngine().cancelDelayedToaster();
            context_.getGameEngine().swapTurn();
        }
        return allowed;
    }

    @JavascriptInterface
    public String getPlayerElement() {
        return context_.PLAYER_ELEMENT == Grid.CelElement.Circle ? "circle" : "cross";
    }

    @JavascriptInterface
    public boolean canIPlay() {
        return context_.getGameEngine().getCurrentPlayer() == GameEngine.PlayerType.Human;
    }

    private static void callJavaScript(WebView view, String methodName, Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        String separator = "";
        for (Object param : params) {
            stringBuilder.append(separator);
            separator = ",";
            if (param instanceof String) {
                stringBuilder.append("'");
            }
            stringBuilder.append(param);
            if (param instanceof String) {
                stringBuilder.append("'");
            }

        }
        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        Log.i(TAG, "callJavaScript: call=" + call);

        view.loadUrl(call);
    }
}
