package co.techsylvania.marionette.command.game2048;

public class GameMain {

    public static void main(String args[]) {
        final Matrix matrix = new Matrix();


        matrix.print();

        matrix.moveForward();

        System.out.println("after");
        matrix.print();


    }
}
