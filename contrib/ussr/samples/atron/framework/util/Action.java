package ussr.samples.atron.framework.util;

/**
 * Schedulable action, general interface with invocation method, plus local class for value object
 * holding action and a time indicator
 * @author ups
 *
 */
public interface Action {
    
    /**
     * Comparable value object: action and a time
     * @author ups
     */
    public class TimedAction implements Comparable<TimedAction> {
        private float time;
        private Action action;
        
        public TimedAction(float _time, Action _action) {
            time=_time; action=_action;
        }

        @Override
        public int compareTo(TimedAction arg) {
            return time<arg.time ? -1 : (time==arg.time ? 0 : 1);
        }
        
        public float getTime() {
            return time;
        }
        
        public Action getAction() {
            return action;
        }

    }

    /**
     * Method to evaluate when action runs
     */
    public void action();
}
