package co.techsylvania.marionette.command.game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Matrix {

    private static final int SIZE = 4;
    private static final int N_START_TILES = 4;
    private static final double FOUR_PROBABILITY = 0.1;
    private int[][][] mMatrix = new int[SIZE][SIZE][SIZE];
    private Random mRandom = new Random(System.currentTimeMillis());

    public Matrix() {
        init();
    }

    private void init() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    mMatrix[i][j][k] = 0;
                }
            }
        }

        int nStartTiles = 0;
        while (nStartTiles < N_START_TILES) {
            insertNewTile();
            nStartTiles++;
        }
    }

    private void insertNewTile() {
        List<int[]> freeCoordinates = new ArrayList<int[]>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (mMatrix[i][j][k] == 0) {
                        freeCoordinates.add(new int[] {i, j, k});
                    }
                }
            }
        }
        Collections.shuffle(freeCoordinates);

        int value = 2;
        if (mRandom.nextDouble() < FOUR_PROBABILITY) {
            value = 4;
        }

        int[] newTileCoordinates = freeCoordinates.get(0);
        mMatrix[newTileCoordinates[0]][newTileCoordinates[1]][newTileCoordinates[2]] = value;
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
        insertNewTile();
    }

    public void moveLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {

                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = k <= 0;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = k-1; l >= 0; l--) {
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
                        swap(k, j, i, 0, j, i);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, neighX + 1, j, i);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, neighX, j, i);
                    }
                }
            }
        }
        insertNewTile();
    }

    public void moveDown() {
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0 ; k < SIZE; k++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = j >= SIZE - 1;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = j+1; l < SIZE; l++) {
                        if (!foundNeighbour) {
                            if (mMatrix[i][l][k] != 0) {
                                if (mMatrix[i][l][k] != mMatrix[i][j][k]) {
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
                        swap(k, j, i, k, SIZE - 1, i);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, k, neighX - 1, i);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, k, neighX, i);
                    }
                }
            }
        }
        insertNewTile();
    }

    public void moveUp() {
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0 ; k < SIZE; k++) {
                for (int j = 0; j < SIZE; j++) {
                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = j <= 0;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = j-1; l >= 0; l--) {
                        if (!foundNeighbour) {
                            if (mMatrix[i][l][k] != 0) {
                                if (mMatrix[i][l][k] != mMatrix[i][j][k]) {
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
                        swap(k, j, i, k, 0, i);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, k, neighX + 1, i);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, k, neighX, i);
                    }
                }
            }
        }
        insertNewTile();
    }

    public void moveBackward() {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                for (int i = SIZE - 1; i >= 0; i--) {
                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = i >= SIZE - 1;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = i+1; l < SIZE; l++) {
                        if (!foundNeighbour) {
                            if (mMatrix[l][j][k] != 0) {
                                if (mMatrix[l][j][k] != mMatrix[i][j][k]) {
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
                        swap(k, j, i, k, j, SIZE - 1);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, k, j, neighX - 1);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, k, j, neighX);
                    }
                }
            }
        }
        insertNewTile();
    }

    public void moveForward() {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                for (int i = 0; i < SIZE; i++) {
                    boolean isTile = mMatrix[i][j][k] != 0;
                    if (!isTile) {
                        continue;
                    }

                    boolean onMargin = i <= 0;
                    if (onMargin) {
                        continue;
                    }

                    boolean uncompNeighPresent = false;
                    boolean compNeighPresent = false;
                    boolean foundNeighbour = false;
                    int neighX = 0;
                    for (int l = i-1; l >= 0; l--) {
                        if (!foundNeighbour) {
                            if (mMatrix[l][j][k] != 0) {
                                if (mMatrix[l][j][k] != mMatrix[i][j][k]) {
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
                        swap(k, j, i, k, j, 0);
                    }
                    if (uncompNeighPresent) {
                        swap(k, j, i, k, j, neighX + 1);
                    }
                    if (compNeighPresent) {
                        merge(k, j, i, k, j, neighX);
                    }
                }
            }
        }
        insertNewTile();
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

    public int getScore() {
        int s = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    s += mMatrix[i][j][k];
                }
            }
        }
        return s;
    }

    public int getMaxTile() {
        int max = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (max < mMatrix[i][j][k]) {
                        max = mMatrix[i][j][k];
                    }
                }
            }
        }
        return max;
    }

    public int[][][] getMatrix() {
        return mMatrix.clone();
    }

    public boolean isWon() {
        return getMaxTile() >= 2048;
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
