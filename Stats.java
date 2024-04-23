public class Stats {
    private final double totalWaitTime;
    private final int served;
    private final int notServed;

    Stats(double totalWaitTime, int served, int notServed) {
        this.totalWaitTime = totalWaitTime;
        this.served = served;
        this.notServed = notServed;
    }

    public Stats oneServed() {
        return new Stats(totalWaitTime, served + 1, notServed);
    }
    
    public Stats oneNotServed() {
        return new Stats(totalWaitTime, served, notServed + 1);
    }

    public Stats waitChange(double waitTime) {
        return new Stats(totalWaitTime + waitTime, served, notServed);
    }

    public String avWaitTime() {
        if (served == 0) {
            return "0";
        } else {
            double avTime = totalWaitTime / served;
            return String.format("%.3f", avTime);
        }
    }

    @Override
    public String toString() {
        return "[" + avWaitTime() + " " + Integer.toString(served) + 
            " " + Integer.toString(notServed) + "]";
    }
}
