package co.techsylvania.marionette.command.game2048;

public class GameModel {

    private int[][][] matrix;
    private int score;
    private int maxTile;
    private boolean isGameOver;
    private boolean won;
    private int[] angles;

    public GameModel(int[][][] matrix, int score, int maxTile, boolean isGameOver, boolean won, int[] angles) {
        this.matrix = matrix;
        this.score = score;
        this.isGameOver = isGameOver;
        this. maxTile = maxTile;
        this.won = won;
        this.angles = angles;
    }

    public int[][][] getMatrix() {
        return matrix;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int[] getAngles() {
        return angles;
    }

    public int getMaxTile() {
        return maxTile;
    }

    public boolean isWon() {
        return won;
    }
}
