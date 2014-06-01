package co.techsylvania.marionette.command.leap;

/**
 * @author Vitali Vasilioglo <vitali.vasilioglo@iquestgroup.com>
 */
public interface LeapGestureListener {
    public enum GestureType {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        PUSH,
        PULL
    }

    void onGestureReceived(GestureType gesture);
}
