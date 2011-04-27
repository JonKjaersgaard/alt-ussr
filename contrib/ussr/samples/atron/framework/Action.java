package ussr.samples.atron.framework;

public interface Action {
    
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

    public void action();
}
