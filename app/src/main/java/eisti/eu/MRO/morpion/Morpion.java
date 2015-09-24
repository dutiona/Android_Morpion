package eisti.eu.MRO.morpion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Point d'entrée de l'application.
 * Instancie les modèles, les vues et les controlers et lie le tout entre eux.
 */
public class Morpion extends AppCompatActivity {

    private static final String TAG = "Morpion";

    public static final Grid.CelElement PLAYER_ELEMENT = Grid.CelElement.Circle;

    private Grid grid_;
    private GameEngine gameEngine_;

    private WebAppInterface webAppInterface_;
    private WebView webView_;

    public Grid getGrid(){
        return grid_;
    }
    public GameEngine getGameEngine(){
        return gameEngine_;
    }

    public WebAppInterface getWebAppInterface(){
        return webAppInterface_;
    }
    public WebView getWebView(){
        return webView_;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);

        //Initialisation des modèles
        grid_ = new Grid(3);
        gameEngine_ = new GameEngine(GameEngine.PlayerType.Human, this);


        webAppInterface_ = new WebAppInterface(this);
        webView_ = (WebView) findViewById(R.id.Grid);

        Log.i(TAG, "Initialisation terminée");

        //Chargement de la grille
        webView_.loadUrl("file:///android_res/raw/grid.html");

        Log.i(TAG, "Chargement de la grille terminé");

        //Activation du javascript, on set la classe qui servira de Handler
        WebSettings webSettings = webView_.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView_.addJavascriptInterface(webAppInterface_, "Android");

        Log.i(TAG, "Initialisation du handler webApp terminé");

        TextView score_opponent = (TextView) findViewById(R.id.Score_opponent);
        TextView score_player = (TextView) findViewById(R.id.Score_player);
        TextView game_counter = (TextView) findViewById((R.id.Game_counter));

        score_opponent.setText("0");
        score_player.setText("0");
        game_counter.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_morpion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void notifyNextGame(GameEngine.PlayerType winner){
        TextView score_view;
        if(winner == GameEngine.PlayerType.Computer){
            score_view = (TextView) findViewById(R.id.Score_opponent);
        }else{
            score_view = (TextView) findViewById(R.id.Score_player);
        }
        TextView game_counter = (TextView) findViewById((R.id.Game_counter));
        score_view.setText(String.valueOf(Integer.valueOf(score_view.getText().toString()) + 1));
        game_counter.setText(String.valueOf(Integer.valueOf(game_counter.getText().toString()) + 1));

        gameEngine_ = new GameEngine(GameEngine.getOppositePlayer(winner), this);
    }
}
