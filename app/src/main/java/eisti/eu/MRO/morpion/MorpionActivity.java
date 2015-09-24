package eisti.eu.MRO.morpion;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Point d'entrée de l'application.
 * Instancie les modèles, les vues et les controlers et lie le tout entre eux.
 */
public class MorpionActivity extends AppCompatActivity {

    private static final String TAG = "MorpionActivity";

    public static final Grid.CelElement PLAYER_ELEMENT = Grid.CelElement.Circle;
    public static final DifficultyConsistency.DifficultyLevel DEFAULT_DIFFICULTY = DifficultyConsistency.DifficultyLevel.NORMAL;
    public static final GameEngine.PlayerType DEFAULT_FIRST_PLAYER = GameEngine.PlayerType.Human;

    //Models
    private Grid grid_;
    private GameEngine game_engine_;

    //Controler
    private WebApp web_app_;
    private DifficultyConsistency difficulty_consistency_;

    //Vues
    private WebView web_view_;

    private ToggleButton button_easy_;
    private ToggleButton button_normal_;
    private ToggleButton button_impossible_;

    private TextView text_game_counter_;
    private TextView text_score_player_;
    private TextView text_score_opponent_;

    /*
    Getters
     */

    //Models
    public Grid getGrid() {
        return grid_;
    }

    public GameEngine getGameEngine() {
        return game_engine_;
    }

    //Controler
    public WebApp getWebApp() {
        return web_app_;
    }

    public DifficultyConsistency getDifficultyConsistency() {
        return difficulty_consistency_;
    }

    //Vues
    public WebView getWebView() {
        return web_view_;
    }

    public ToggleButton getButtonEasy() {
        return button_easy_;
    }

    public ToggleButton getButtonNormal() {
        return button_normal_;
    }

    public ToggleButton getButtonImpossible() {
        return button_impossible_;
    }

    public TextView getGameCounter() {
        return text_game_counter_;
    }

    public TextView getScorePlayer() {
        return text_score_player_;
    }

    public TextView getScoreOpponent() {
        return text_score_opponent_;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);

        //Initialisation des models
        grid_ = new Grid(3);
        game_engine_ = new GameEngine(this, DEFAULT_FIRST_PLAYER);

        Log.i(TAG, "Initialisation des modèles terminée.");

        //Chargement des vues score
        text_score_opponent_ = (TextView) findViewById(R.id.score_opponent);
        text_score_player_ = (TextView) findViewById(R.id.score_player);
        text_game_counter_ = (TextView) findViewById((R.id.game_counter));

        Log.i(TAG, "Chargement des vues des scores terminé.");

        //Initialisation de la web view et de son controler
        web_app_ = new WebApp(this);
        web_view_ = (WebView) findViewById(R.id.web_view);
        web_view_.loadUrl("file:///android_res/raw/grid.html");
        WebSettings webSettings = web_view_.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_view_.addJavascriptInterface(web_app_, "Android");

        Log.i(TAG, "Chargement de la vue de la grille terminé.");

        //Initialisation des vues button et association du controler de cohérence
        //Le projet est trop petit pour penser à faire un mediator
        button_easy_ = (ToggleButton) findViewById(R.id.button_easy);
        button_normal_ = (ToggleButton) findViewById(R.id.button_normal);
        button_impossible_ = (ToggleButton) findViewById(R.id.button_impossible);

        difficulty_consistency_ = new DifficultyConsistency(this, DEFAULT_DIFFICULTY, new ArrayList<>(Arrays.asList(button_easy_, button_impossible_, button_normal_)));

        Log.i(TAG, "Chargement du controler de difficulté terminé.");

        //On lance la première partie 2 secondes après le lancement
        showToast(getString(R.string.first_game_message));
        Handler launcher = new Handler();
        launcher.postDelayed(new Runnable() {
            public void run() {
                Log.i(TAG, "Lancement de la première partie...");
                newGame();
            }
        }, 2000);
    }

    //Méthode générée par défaut
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_morpion, menu);
        return true;
    }

    //Méthode générée par défaut
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

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void finishGame() {
        //Mettre la bare dans la webView
        web_app_.showCrossedLine();


        //Mettre à jour les scores
        GameEngine.PlayerType winner = game_engine_.getWinner();

        int incr_score_player = 0;
        int incr_score_opponent = 0;
        int incr_game_counter = 0;

        if (winner == GameEngine.PlayerType.None)
            incr_game_counter++;

        if (winner == GameEngine.PlayerType.Human) {
            incr_score_player++;
            incr_game_counter++;
        }

        if (winner == GameEngine.PlayerType.Computer) {
            incr_score_opponent++;
            incr_game_counter++;
        }

        text_game_counter_.setText(String.valueOf(Integer.valueOf(text_game_counter_.getText().toString()) + incr_game_counter));
        text_score_player_.setText(String.valueOf(Integer.valueOf(text_score_player_.getText().toString()) + incr_score_player));
        text_score_opponent_.setText(String.valueOf(Integer.valueOf(text_score_opponent_.getText().toString()) + incr_score_opponent));

        //Lance la partie suivante
        showToast(getString(R.string.next_game_message));
        Handler launcher = new Handler();
        launcher.postDelayed(new Runnable() {
            public void run() {
                Log.i(TAG, "Lancement de la partie suivante...");
                newGame();
            }
        }, 3000);
    }

    public void newGame() {
        web_app_.newGame();
        game_engine_.newGame();
    }
}
