public abstract class Event {
    private final Customer customer;
    private final double eventTime;
    
    public Event(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    double getEventTime() {
        return this.eventTime;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.getEventTime()) + " " + this.getCustomer().toString();
    }

    abstract String eventType();

    public Stats updateStats(Stats stats) {
        return stats;
    }

    abstract Pair<Event, Receptionist> next(Receptionist receptionist);      
}  // edit all next() in events,return pair of (next event) and (next receptionist state)
