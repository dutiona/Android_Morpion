package eisti.eu.MRO.morpion;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by MR244047 on 24/09/2015.
 */
public class GameEngine{

    public enum PlayerType {
        Human,
        Computer,
        None //Utilisé pour le résultat de GameFinished
    }

    private static ArrayList<Grid> winningGridList_ = new ArrayList<Grid>();

    private PlayerType current_player_;
    private int score_player_;
    private int score_opponent_;
    private int game_counter_;

    private MorpionActivity ctx_;



    public static PlayerType getOppositePlayer(PlayerType player){
        return player == PlayerType.Computer ? PlayerType.Human : PlayerType.Computer;
    }

    public GameEngine(PlayerType starting_player, MorpionActivity ctx){
        current_player_ = starting_player;
        ctx_ = ctx;
    }

    public PlayerType whoseTurnIsIt(){
        return current_player_;
    }

    public PlayerType gameFinished(){
        lazyInit();
        PlayerType whoToCheck = whoseTurnIsIt();
        Grid.CelElement whatToCheck = whoToCheck == PlayerType.Human ? ctx_.PLAYER_ELEMENT : Grid.getOppositeCelElement(ctx_.PLAYER_ELEMENT);
        for(Grid grid : winningGridList_){
            if(compare(grid, ctx_.getGrid(), whatToCheck)) {
                return whoToCheck;
            }
        }

        for(Grid grid : winningGridList_){
            if(compare(grid, ctx_.getGrid(), Grid.getOppositeCelElement(whatToCheck))) {
                return getOppositePlayer(whoToCheck);
            }
        }

        return PlayerType.None;
    }

    public void setCurrentTurn(PlayerType player){
        current_player_ = player;
    }

    public Pair<Integer, Integer> doComputerTurn(){
        //Trouve une stratégie de jeux pour l'IA.

        //On a besoin des combinaisons gagnantes
        lazyInit();

        return null;
    }


    private boolean compare(Grid whateverGrid, Grid testedGrid, Grid.CelElement whatToCheck){
        for(int i = 0; i < whateverGrid.getSize(); i++){
            for(int j = 0; i < whateverGrid.getSize(); j++) {
                if(!Grid.areValueEquals(whatToCheck, whateverGrid.getValue(i, j), testedGrid.getValue(i, j))){
                    return false;
                }
            }
        }
        return true;
    }

    private static void lazyInit(){
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
        if(winningGridList_.isEmpty()){
            /* Lignes */
            Grid win_line_1 = new Grid(3);
            win_line_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_line_1.setValue(0, 1, Grid.CelElement.Whatever);
            win_line_1.setValue(0, 2, Grid.CelElement.Whatever);
            winningGridList_.add(win_line_1);

            Grid win_line_2 = new Grid(3);
            win_line_1.setValue(1, 0, Grid.CelElement.Whatever);
            win_line_1.setValue(1, 1, Grid.CelElement.Whatever);
            win_line_1.setValue(1, 2, Grid.CelElement.Whatever);
            winningGridList_.add(win_line_2);

            Grid win_line_3 = new Grid(3);
            win_line_1.setValue(2, 0, Grid.CelElement.Whatever);
            win_line_1.setValue(2, 1, Grid.CelElement.Whatever);
            win_line_1.setValue(2, 2, Grid.CelElement.Whatever);
            winningGridList_.add(win_line_3);

            /* Colonnes */
            Grid win_col_1 = new Grid(3);
            win_col_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_col_1.setValue(1, 0, Grid.CelElement.Whatever);
            win_col_1.setValue(2, 0, Grid.CelElement.Whatever);
            winningGridList_.add(win_col_1);

            Grid win_col_2 = new Grid(3);
            win_col_2.setValue(0, 1, Grid.CelElement.Whatever);
            win_col_2.setValue(1, 1, Grid.CelElement.Whatever);
            win_col_2.setValue(2, 1, Grid.CelElement.Whatever);
            winningGridList_.add(win_col_2);

            Grid win_col_3 = new Grid(3);
            win_col_3.setValue(0, 2, Grid.CelElement.Whatever);
            win_col_3.setValue(1, 2, Grid.CelElement.Whatever);
            win_col_3.setValue(2, 2, Grid.CelElement.Whatever);
            winningGridList_.add(win_col_3);

            /* Diagonales */
            Grid win_diag_1 = new Grid(3);
            win_diag_1.setValue(0, 0, Grid.CelElement.Whatever);
            win_diag_1.setValue(1, 1, Grid.CelElement.Whatever);
            win_diag_1.setValue(2, 2, Grid.CelElement.Whatever);
            winningGridList_.add(win_diag_1);

            Grid win_diag_2 = new Grid(3);
            win_diag_2.setValue(0, 2, Grid.CelElement.Whatever);
            win_diag_2.setValue(1, 1, Grid.CelElement.Whatever);
            win_diag_2.setValue(2, 0, Grid.CelElement.Whatever);
            winningGridList_.add(win_diag_2);
        }
    }
}
