package eisti.eu.MRO.morpion;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

import android.os.Handler;

/**
 * ${PACJAGE_NAME} in Morpion
 *
 * Created by MR244047 on 24/09/2015.
 *
 */
public class GameEngine {

    private static final String TAG = "GameEngine";

    public enum PlayerType {
        Human,
        Computer,
        None //Utilisé pour le résultat de GameFinished
    }

    private static ArrayList<Grid> winning_grid_list_ = new ArrayList<>();


    private PlayerType current_player_;
    private PlayerType starting_player_;

    private PlayerType winner_;
    private Grid winning_combination_;
    private int score_player_;
    private int score_opponent_;
    private int game_counter_;

    private MorpionActivity context_;

    private Handler delayed_handler_;
    private Runnable player_toaster_;


    public GameEngine(MorpionActivity ctx, PlayerType starting_player) {
        starting_player_ = starting_player;
        current_player_ = starting_player_;
        winner_ = PlayerType.None;
        context_ = ctx;

        score_player_ = 0;
        score_opponent_ = 0;
        game_counter_ = 0;
    }

    public PlayerType getWinner() {
        return winner_;
    }

    public Grid getWinningCombination() {
        return winning_combination_;
    }

    public int getScorePlayer() {
        return score_player_;
    }

    public int getScoreOpponent() {
        return score_opponent_;
    }

    public int getGameCounter() {
        return game_counter_;
    }

    public void newGame() {
        starting_player_ = getOppositePlayer(starting_player_);
        current_player_ = starting_player_;
        winner_ = PlayerType.None;
        winning_combination_ = null;

        delayed_handler_ = new Handler();


        //On lance avec swapTurn (donc il faut inverser le current_player, sauf que c'est déjà fait avec newGame() !)
        swapTurn();
    }

    public void swapTurn() {
        current_player_ = getOppositePlayer(current_player_);

        //Il peut y avoir match nul
        if (isFinished()) {
            game_counter_++;
            if (getWinner() == PlayerType.None) {
                context_.showToast(context_.getString(R.string.noone_win));
            } else if (getWinner() == PlayerType.Computer) {
                context_.showToast(context_.getString(R.string.computer_win));
                score_opponent_++;
            } else if (getWinner() == PlayerType.Human) {
                context_.showToast(context_.getString(R.string.player_win));
                score_player_++;
            }
            context_.finishGame();
        } else {

            if (current_player_ == PlayerType.Computer) {
                context_.showToast(context_.getString(R.string.computer_about_to_play));
                delayed_handler_.postDelayed(new Runnable() {
                    public void run() {
                        doComputerTurn();
                        swapTurn();
                    }
                }, 2000);
            } else {
                doPlayerTurn();
                //C'est le engin WebApp qui appellera le swapTurn quand le joueur aura joué
            }

        }
    }

    private void doPlayerTurn() {
        player_toaster_ = new Runnable() {
            public void run() {
                Log.i(TAG, "Lancement de la première partie...");
                context_.showToast(context_.getString(R.string.player_turn_to_play));
                doPlayerTurn();
            }
        };
        delayed_handler_.postDelayed(player_toaster_, 8000);
    }

    public void cancelDelayedToaster() {
        delayed_handler_.removeCallbacks(player_toaster_);
    }

    public Pair<Integer, Integer> doComputerTurn() {
        //On a besoin des combinaisons gagnantes
        lazyInit();

        //TODO Trouve une stratégie de jeux pour l'IA.

        return null;
    }

    public PlayerType getCurrentPlayer() {
        return current_player_;
    }

    public boolean isFinished() {
        if (hasWinner()) {
            return true;
        } else {
            for (int i = 0; i < context_.getGrid().getSize(); i++) {
                for (int j = 0; j < context_.getGrid().getSize(); j++) {
                    if (context_.getGrid().isEmpty(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private boolean hasWinner() {
        lazyInit();

        context_.getGrid().dumpToConsole();

        PlayerType whoToCheck = getCurrentPlayer();
        Grid.CelElement whatToCheck = whoToCheck == PlayerType.Human ? MorpionActivity.PLAYER_ELEMENT : Grid.getOppositeCelElement(MorpionActivity.PLAYER_ELEMENT);
        for (Grid grid : winning_grid_list_) {
            if (compare(grid, context_.getGrid(), whatToCheck)) {
                winner_ = whoToCheck;
                winning_combination_ = grid;
                return true;
            }
        }

        for (Grid grid : winning_grid_list_) {
            if (compare(grid, context_.getGrid(), Grid.getOppositeCelElement(whatToCheck))) {
                winner_ = getOppositePlayer(whoToCheck);
                winning_combination_ = grid;
                return true;
            }
        }

        return false;
    }


    private boolean compare(Grid whateverGrid, Grid testedGrid, Grid.CelElement whatToCheck) {
        for (int i = 0; i < whateverGrid.getSize(); i++) {
            for (int j = 0; j < whateverGrid.getSize(); j++) {
                if (!Grid.isCelValueEqual(whatToCheck, whateverGrid.getValue(i, j), testedGrid.getValue(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static PlayerType getOppositePlayer(PlayerType player) {
        return player == PlayerType.Computer ? PlayerType.Human : PlayerType.Computer;
    }

    private static void lazyInit() {
        /*
        Sur une grille type
        [[0,0], [0,1], [0,2]]
        [[1,0], [1,1], [1,2]]
        [[2,0], [2,1], [2,2]]
        Les combinaisons gagnantes sont :
        [[0,0], [0,1], [0,2]] ou [[1,0], [1,1], [1,2]] ou [[2,0], [2,1], [2,2]] (en ligne) ou
        [[0,0], [1,0], [2,0]] ou [[0,1], [1,1], [2,1]] ou [[0,2], [1,2], [2,2]] (en colonne) ou
        [[0,0], [1,1], [2,2]] ou [[0,2], [1,1], [2,0] en diagonale
         */

        //Lazy initialisation du tableau
        if (winning_grid_list_.isEmpty()) {
            /* Lignes */
            Grid win_line_1 = new Grid(3);
            win_line_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_line_1.setValue(0, 1, Grid.CelElement.Whatever);
            win_line_1.setValue(0, 2, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_line_1);

            Grid win_line_2 = new Grid(3);
            win_line_2.setValue(1, 0, Grid.CelElement.Whatever);
            win_line_2.setValue(1, 1, Grid.CelElement.Whatever);
            win_line_2.setValue(1, 2, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_line_2);

            Grid win_line_3 = new Grid(3);
            win_line_3.setValue(2, 0, Grid.CelElement.Whatever);
            win_line_3.setValue(2, 1, Grid.CelElement.Whatever);
            win_line_3.setValue(2, 2, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_line_3);

            /* Colonnes */
            Grid win_col_1 = new Grid(3);
            win_col_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_col_1.setValue(1, 0, Grid.CelElement.Whatever);
            win_col_1.setValue(2, 0, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_col_1);

            Grid win_col_2 = new Grid(3);
            win_col_2.setValue(0, 1, Grid.CelElement.Whatever);
            win_col_2.setValue(1, 1, Grid.CelElement.Whatever);
            win_col_2.setValue(2, 1, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_col_2);

            Grid win_col_3 = new Grid(3);
            win_col_3.setValue(0, 2, Grid.CelElement.Whatever);
            win_col_3.setValue(1, 2, Grid.CelElement.Whatever);
            win_col_3.setValue(2, 2, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_col_3);

            /* Diagonales */
            Grid win_diag_1 = new Grid(3);
            win_diag_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_diag_1.setValue(1, 1, Grid.CelElement.Whatever);
            win_diag_1.setValue(2, 2, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_diag_1);

            Grid win_diag_2 = new Grid(3);
            win_diag_2.setValue(0, 2, Grid.CelElement.Whatever);
            win_diag_2.setValue(1, 1, Grid.CelElement.Whatever);
            win_diag_2.setValue(2, 0, Grid.CelElement.Whatever);
            winning_grid_list_.add(win_diag_2);
        }
    }
}
