package co.techsylvania.marionette.command.game2048;

import java.util.Random;

public class Matrix {

    private static final int SIZE = 4;
    private int mValues[] = new int[]{0, 2, 4};
    private int[][][] mMatrix = new int[SIZE][SIZE][SIZE];
    private Random mRandom = new Random(System.currentTimeMillis());

    public Matrix() {
        init();
    }

    public void init() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    mMatrix[i][j][k] = mValues[mRandom.nextInt(mValues.length)];
                }
            }
        }
    }

    public void moveRight() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = SIZE - 1; k >= 0; k--) {

                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = k >= SIZE - 1;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = k+1; l < SIZE; l++) {
                        if (!foundNeighbour) {
                            if (mMatrix[i][j][l] != 0) {
                                if (mMatrix[i][j][l] != mMatrix[i][j][k]) {
                                    uncompNeighPresent = true;
                                } else {
                                    compNeighPresent = true;
                                }
                                neighX = l;
                                foundNeighbour = true;
                            }
                        }
                    }

                    if (!uncompNeighPresent && !compNeighPresent) {
                        swap(k, j, i, SIZE - 1, j, i);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, neighX - 1, j, i);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, neighX, j, i);
                    }
                }
            }
        }
    }

    private void swap(int x, int y, int z, int x1, int y1, int z1) {
        if (!(x == x1 && y == y1 && z == z1)) {
            mMatrix[z1][y1][x1] = mMatrix[z][y][x];
            mMatrix[z][y][x] = 0;
        }
    }

    private void merge(int x, int y, int z, int x1, int y1, int z1) {
        if (!(x == x1 && y == y1 && z == z1)) {
            mMatrix[z1][y1][x1] = 2 * mMatrix[z][y][x];
            mMatrix[z][y][x] = 0;
        }
    }

    public void print() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    System.out.format("%d ", mMatrix[i][j][k]);
                }
                System.out.println("");
            }
            System.out.format("\n");
        }
    }
}
