package ussr.physics;


public abstract class TimedPhysicsObserver implements PhysicsObserver, Comparable<TimedPhysicsObserver> {
    private float time;
    public TimedPhysicsObserver(float time) {
        this.time = time;
    }
    public int compareTo(TimedPhysicsObserver other) {
        if(other.time<time) return -1;
        if(other.time>time) return 1;
        return 0;
    }
    public float getTime() {
        return time;
    }
}
