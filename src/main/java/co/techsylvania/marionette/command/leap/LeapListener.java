package co.techsylvania.marionette.command.leap;

import com.leapmotion.leap.*;
import com.sun.istack.internal.NotNull;
import static co.techsylvania.marionette.command.leap.LeapGestureListener.GestureType;

/**
 * @author Vitali Vasilioglo <vitali.vasilioglo@iquestgroup.com>
 */
class LeapListener extends Listener {

    public static final int THRESHOLD = 200;
    private boolean skipFrame;

    private long lastGestureTime;
    private GestureType lastSwipeType;

    private LeapGestureListener gestureListener;

    public LeapListener(@NotNull LeapGestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }

    public void onInit(Controller controller) {
        System.out.println("LM initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("LM connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("LM disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("LM exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
      /*  System.out.println("Frame id: " + frame.id()
                         + ", timestamp: " + frame.timestamp()
                         + ", hands: " + frame.hands().count()
                         + ", fingers: " + frame.fingers().count()
                         + ", tools: " + frame.tools().count()
                         + ", gestures " + frame.gestures().count());

        //Get hands
        for(Hand hand : frame.hands()) {
            String handType = hand.isLeft() ? "Left hand" : "Right hand";
            System.out.println("  " + handType + ", id: " + hand.id()
                             + ", palm position: " + hand.palmPosition());

            // Get the hand's normal vector and direction
            Vector normal = hand.palmNormal();
            Vector direction = hand.direction();

            // Calculate the hand's pitch, roll, and yaw angles
            System.out.println("  pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
                             + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
                             + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");

            // Get fingers
            for (Finger finger : hand.fingers()) {
                System.out.println("    " + finger.type() + ", id: " + finger.id()
                                 + ", length: " + finger.length()
                                 + "mm, width: " + finger.width() + "mm");

                //Get Bones
                for(Bone.Type boneType : Bone.Type.values()) {
                    Bone bone = finger.bone(boneType);
                    System.out.println("      " + bone.type()
                                     + " bone, start: " + bone.prevJoint()
                                     + ", end: " + bone.nextJoint()
                                     + ", direction: " + bone.direction());
                }
            }
        }

        // Get tools
        for(Tool tool : frame.tools()) {
            System.out.println("  Tool id: " + tool.id()
                             + ", position: " + tool.tipPosition()
                             + ", direction: " + tool.direction());
        }
*/
        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            if (skipFrame){
                skipFrame = false;
                break;
            }
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);

                    // Calculate clock direction using the angle between circle normal and pointable
                    String clockwiseness;
                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 4) {
                        // Clockwise if angle is less than 90 degrees
                        clockwiseness = "clockwise";
                    } else {
                        clockwiseness = "counterclockwise";
                    }

                    // Calculate angle swept since last frame
                    double sweptAngle = 0;
                    if (circle.state() != Gesture.State.STATE_START) {
                        CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
                        sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
                    }

                    /*System.out.println("  Circle id: " + circle.id()
                               + ", " + circle.state()
                               + ", progress: " + circle.progress()
                               + ", radius: " + circle.radius()
                               + ", angle: " + Math.toDegrees(sweptAngle)
                               + ", " + clockwiseness);*/
                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    if (swipe.state() != Gesture.State.STATE_STOP) {
                        break;
                    }
                    GestureType swipeType;

                    float xDistance = Math.abs(swipe.direction().get(0));
                    float yDistance = Math.abs(swipe.direction().get(1));
                    float zDistance = Math.abs(swipe.direction().get(2));

                    if (zDistance > xDistance && zDistance > yDistance) {
                        if (swipe.direction().get(2)<0) {
                            swipeType = GestureType.PUSH;
                        } else {
                            swipeType = GestureType.PULL;
                        }
                    }  else {
                        boolean isHorizontal = xDistance > yDistance;

                        if (isHorizontal) {
                            if (swipe.direction().get(0) > 0) {
                                swipeType = GestureType.RIGHT;
                            } else {
                                swipeType = GestureType.LEFT;
                            }
                        } else { //vertical
                            if (swipe.direction().get(1) > 0) {
                                swipeType = GestureType.UP;
                            } else {
                                swipeType = GestureType.DOWN;
                            }
                        }
                    }

                    if (swipeType != null) {
                        long dt = System.currentTimeMillis() - lastGestureTime;
                        if (dt > THRESHOLD || lastSwipeType == null || lastSwipeType != swipeType) {
                            gestureListener.onGestureReceived(swipeType);
                            lastGestureTime = System.currentTimeMillis();
                            lastSwipeType = swipeType;
                        }
                        skipFrame = true;
                    }
                    break;
                default:
                    break;
            }
        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
//            System.out.println();
        }
    }

}
