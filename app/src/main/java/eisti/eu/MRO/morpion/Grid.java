package eisti.eu.MRO.morpion;

/**
 * Created by Michaël on 24/09/2015.
 */
public class Grid {

    private static final String TAG = "Grid";

    public enum CelElement {
        Circle, Cross, Empty,
        Whatever //utilisé dans le check des combinaisons gagnantes (soit Circle, soit cross -> tableau génériques)
    }

    private CelElement grid_[][];


    public Grid(int size) {
        grid_ = new CelElement[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid_[i][j] = CelElement.Empty;
            }
        }
    }

    public int getSize() {
        return grid_.length;
    }

    public boolean isEmpty(int row, int col) {
        return grid_[row][col] == CelElement.Empty;
    }

    public boolean isCircle(int row, int col) {
        return grid_[row][col] == CelElement.Circle;
    }

    public boolean isCross(int row, int col) {
        return grid_[row][col] == CelElement.Circle;
    }

    public boolean isWhatever(int row, int col) {
        return grid_[row][col] == CelElement.Whatever;
    }

    public CelElement getValue(int row, int col) {
        return grid_[row][col];
    }

    public void setValue(int row, int col, CelElement el) {
        grid_[row][col] = el;
    }

    public boolean setValueSafe(int row, int col, CelElement el) {
        if (isEmpty(row, col)) {
            grid_[row][col] = el;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCelValueEqual(CelElement expected, CelElement lhs, CelElement rhs) {
        return lhs == CelElement.Whatever && rhs == expected;
    }

    public static CelElement getOppositeCelElement(CelElement cel) {
        return cel == CelElement.Circle ? CelElement.Cross : CelElement.Circle;
    }
}
