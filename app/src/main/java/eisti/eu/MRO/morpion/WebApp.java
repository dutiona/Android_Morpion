package eisti.eu.MRO.morpion;

import android.util.JsonWriter;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;


/**
 * ${PACKAGE_NAME} in Morpion
 * <p/>
 * Created by Michaël on 23/09/2015.
 */
public class WebApp {

    private static final String TAG = "WebApp";

    private MorpionActivity context_;

    public WebApp(MorpionActivity c) {
        context_ = c;
    }

    private static void callJavaScript(WebView view, String methodName, boolean raw, Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{").append(methodName).append("(");
        String separator = "";
        if(raw){
            for (Object param : params) {
                stringBuilder.append(separator);
                separator = ",";
                stringBuilder.append(param.toString());
            }
        }else {
            for (Object param : params) {
                stringBuilder.append(separator);
                separator = ",";
                if (param instanceof Iterable) {
                    stringBuilder.append("[");
                    for (Object p : (Iterable) param) {
                        if (p instanceof Iterable) {
                            stringBuilder.append("[");
                            for (Object _ : (Iterable) p) {
                                stringBuilder.append("'").append(_.toString()).append("'");
                            }
                            stringBuilder.append("]");
                        } else {
                            stringBuilder.append("'").append(p.toString()).append("'");
                        }
                    }
                    stringBuilder.append("]");
                } else {
                    stringBuilder.append("'").append(param.toString()).append("'");
                }
            }
        }
        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        Log.i(TAG, "callJavaScript: call=" + call);

        view.loadUrl(call);
    }

    public void newGame() {
        callJavaScript(context_.getWebView(), "triggerCleanup", false);
    }

    public void showCrossedLine() {
        callJavaScript(context_.getWebView(), "triggerCrossedLine", true, context_.getGameEngine().getWinningCombination().asJSArray());
    }

    @JavascriptInterface
    public void showToast(String text) {
        context_.showToast(text);
    }

    @JavascriptInterface
    public boolean addGridElem(int row, int col, String el) {
        Log.i(TAG, "Ajout index element : (" + row + ", " + col + ")=" + el);
        boolean allowed = context_.getGrid().setValueSafe(row, col, el.equals("circle") ? Grid.CelElement.Circle : Grid.CelElement.Cross);
        if (!allowed) {
            showToast(context_.getResources().getString(R.string.toast_cell_already_used));
        } else {
            context_.getGameEngine().cancelDelayedToaster();
            context_.getWebView().post(new Runnable() {
                @Override
                public void run() {
                    context_.getGameEngine().swapTurn();
                }
            });
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
}
