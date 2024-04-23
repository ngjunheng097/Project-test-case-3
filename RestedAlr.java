public class RestedAlr extends Event {
    private final Server server;

    RestedAlr(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    String eventType() {
        return "restedAlr";
    }

    @Override
    Pair<Event, Receptionist> next(Receptionist receptionist) {
        return new Pair<Event, Receptionist>(this, receptionist);   // no need to change Event
    }

    @Override
    public String toString() {
        return "";
    }
}

