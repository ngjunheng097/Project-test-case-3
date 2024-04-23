public class DoneResting extends Event {
    private final Server server;

    DoneResting(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    String eventType() {
        return "backToWork";
    }

    @Override
    Pair<Event, Receptionist> next(Receptionist receptionist) {
        int serverIndex = server.getServerId() - 1;
        Server workingServer = server.restToWork();
        Receptionist newReceptionist = receptionist.replaceServer(serverIndex, workingServer);
        Event event = new RestedAlr(getCustomer(), getEventTime(), workingServer);
        return new Pair<Event, Receptionist>(event, newReceptionist);   // no need to change Event
    }

    @Override
    public String toString() {
        return "";
    }
}
