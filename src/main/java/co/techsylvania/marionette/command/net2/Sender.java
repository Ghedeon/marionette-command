package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.game2048.GameModel;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender implements Runnable {

    private Socket socket;
    BlockingQueue<GameModel> q = new LinkedBlockingQueue<GameModel>();

    public Sender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            boolean always = true;
            while (always) {
                sendGameModelThrows(q.take());
            }
        } catch (IOException ex) {
            try {
                System.out.println("IOEx. Closing socket.");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException ex) {
            try {
                System.out.println("Interrupted. Closing socket.");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendGameModel(GameModel gameModel) {
            q.offer(gameModel);
    }

    private void sendGameModelThrows(GameModel gameModel) throws IOException {
        final DataOutputStream doStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        System.out.println("Writing :" + gameModel.getScore());
        write3DMatrix(doStream, gameModel.getMatrix());
        doStream.writeInt(gameModel.getScore());
        doStream.writeInt(gameModel.getMaxTile());
        doStream.writeInt(booleanToInt(gameModel.isGameOver()));
        doStream.writeInt(booleanToInt(gameModel.isWon()));
        writeAngles(doStream, gameModel.getAngles());
        doStream.flush();
    }

    private void write3DMatrix(DataOutputStream doStream, int[][][] source) throws IOException {
        final int size = 4;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    doStream.writeInt(source[i][j][k]);
                }
            }
        }
    }

    private void writeAngles(DataOutputStream doStream, int[] angles) throws IOException {
        for (int i = 0; i < 3; i++) {
            doStream.writeInt(angles[i]);
        }
    }

    private int booleanToInt(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
    }
}
