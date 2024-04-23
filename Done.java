public class Done extends Event {
    private final Server server;
    
    Done(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public String toString() {
        return super.toString() + " done serving by " + server.toString();
    }

    @Override
    public Stats updateStats(Stats stats) {
        return stats.oneServed();
    }

    @Override
    String eventType() {
        return "done";
    }
    
    @Override
    public Pair<Event, Receptionist> next(Receptionist receptionist) {
        int serverIndex = server.getServerId() - 1;
        Server server = receptionist.getServers().get(serverIndex);
        Server newServer = server.removeCustomer();
        Server restedServer = newServer.workToRest();
        receptionist = receptionist.replaceServer(serverIndex, restedServer); 
        Event restEvent = new TakeABreak(getCustomer(), this.getEventTime(), restedServer);
        return new Pair<Event, Receptionist>(restEvent, receptionist);     // TakeABreak
    }
}
