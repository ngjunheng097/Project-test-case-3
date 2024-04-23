// edit next() method for done() event to have a chance to TakeABreak


public class TakeABreak extends Event {
    private final Server server;

    TakeABreak(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public String eventType() {
        return "rest";
    }

    @Override
    public Pair<Event, Receptionist> next(Receptionist receptionist) {
        int serverIndex = server.getServerId() - 1; 
        Server server = receptionist.getServers().get(serverIndex);
        double newTime = this.getEventTime() + server.getRestTime();
        Server newServer = server.changeTime(newTime);
        receptionist = receptionist.replaceServer(serverIndex, newServer);
        Event event = new DoneResting(getCustomer(), newTime, newServer);
        return new Pair<Event, Receptionist>(event, receptionist);    // when done resting
    }

    
    @Override
    public String toString() {
        return "";
    }
}
