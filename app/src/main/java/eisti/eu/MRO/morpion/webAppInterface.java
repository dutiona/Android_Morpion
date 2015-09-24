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
public class WebAppInterface {

    private static final String TAG = "Morpion_WebAppInterface";

    private Morpion ctx_;
    private Handler handler_;

    WebAppInterface(Morpion c){
        ctx_ = c;
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(ctx_, text, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Toast call : " + text);
    }

    @JavascriptInterface
    public boolean addGridElem(int row, int col, String el){
        Log.i(TAG, "Ajout grid element : (" + row + ", " + col + ")=" + el);
        boolean allowed = ctx_.getGrid().setValueSafe(row, col, el == "circle" ? Grid.CelElement.Circle : Grid.CelElement.Cross);
        if(!allowed) {
            showToast(ctx_.getResources().getString(R.string.toast_cell_already_used));
        }
        return allowed;
    }

    @JavascriptInterface
    public boolean didIWin(){
        return ctx_.getGameEngine().gameFinished() == GameEngine.PlayerType.Human;
    }

    @JavascriptInterface
    public boolean didComputerWin(){
        return ctx_.getGameEngine().gameFinished() == GameEngine.PlayerType.Computer;
    }

    @JavascriptInterface
    public void doComputerTurn(){
        if(didIWin()){
            showToast(ctx_.getResources().getString(R.string.player_win));

            // Lance une exécution différée
            if(handler_ != null) {
                handler_ = new Handler();
                handler_.postDelayed(new Runnable() {
                    public void run() {
                        ctx_.notifyNextGame(GameEngine.PlayerType.Human);
                        callJavaScript(ctx_.getWebView(), "triggerCleanup");
                    }
                }, 3000);
            }
        }else {
            ctx_.getGameEngine().setCurrentTurn(GameEngine.PlayerType.Computer);
            Pair<Integer, Integer> pos = ctx_.getGameEngine().doComputerTurn();
            callJavaScript(ctx_.getWebView(), "addGridElem", pos.first, pos.second, Grid.getOppositeCelElement(ctx_.PLAYER_ELEMENT));
            ctx_.getGameEngine().setCurrentTurn(GameEngine.PlayerType.Human);
        }
    }

    @JavascriptInterface
    public String getPlayerElement(){
        return ctx_.PLAYER_ELEMENT == Grid.CelElement.Circle ? "circle" : "cross";
    }

    @JavascriptInterface
    public boolean canIPlay(){
        return ctx_.getGameEngine().whoseTurnIsIt() == GameEngine.PlayerType.Human;
    }

    private static void callJavaScript(WebView view, String methodName, Object...params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        String separator = "";
        for (Object param : params) {
            stringBuilder.append(separator);
            separator = ",";
            if(param instanceof String){
                stringBuilder.append("'");
            }
            stringBuilder.append(param);
            if(param instanceof String){
                stringBuilder.append("'");
            }

        }
        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        Log.i(TAG, "callJavaScript: call="+call);

        view.loadUrl(call);
    }
}
