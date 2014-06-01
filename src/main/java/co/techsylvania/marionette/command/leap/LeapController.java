package co.techsylvania.marionette.command.leap;

import com.leapmotion.leap.Controller;
import com.sun.istack.internal.NotNull;

import java.io.IOException;

/**
 * @author Vitali Vasilioglo <vitali.vasilioglo@iquestgroup.com>
 */
public class LeapController {

    private final Controller controller;
    private final LeapListener listener;

    public LeapController(@NotNull LeapGestureListener gestureListener) {
        listener = new LeapListener(gestureListener);
        controller = new Controller();
    }

    public void start(){
        controller.addListener(listener);

        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        controller.removeListener(listener);
    }

    public static void main(String[] args) {
        LeapController controller = new LeapController(new LeapGestureListener() {

            @Override
            public void onGestureReceived(GestureType gestureType) {
                System.out.println(gestureType);
            }
        });

        controller.start();
    }
}
