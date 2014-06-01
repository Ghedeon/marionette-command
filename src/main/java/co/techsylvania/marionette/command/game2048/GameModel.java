package co.techsylvania.marionette.command.game2048;

public class GameModel {

    private int[][][] matrix;
    private int score;
    private boolean isGameOver;
    private int[] angles;

    public GameModel(int[][][] matrix, int score, boolean isGameOver, int[] angles) {
        this.matrix = matrix;
        this.score = score;
        this.isGameOver = isGameOver;
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
}
