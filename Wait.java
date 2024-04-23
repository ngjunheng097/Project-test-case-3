public class Wait extends Event {
    private final Server server;
    private final boolean printString;
    
    Wait(Customer customer, double eventTime, Server server, boolean printString) {
        super(customer, eventTime);
        this.server = server;
        this.printString = printString;
    }

    @Override
    public String toString() {
        if (printString) {
            return super.toString() + " waits at " + server.toString();
        }
        return "";
    }

    @Override
    String eventType() {
        return "wait";
    }

    @Override 
    public Pair<Event, Receptionist> next(Receptionist receptionist) {
        for (Server updatedServer : receptionist.getServers()) {
            if (this.server.getServerId() == updatedServer.getServerId()) {
                if (this.getEventTime() >= updatedServer.getTime()) {
                    Event event = new Serve(this.getCustomer(), this.getEventTime(), updatedServer);
                    int serverIndex = updatedServer.getServerId() - 1;
                    receptionist = receptionist.replaceServer(serverIndex, updatedServer);
                    return new Pair<Event, Receptionist>(event, receptionist);
                }
                Event event = new Wait(this.getCustomer(), updatedServer.getTime(), 
                    server, false);
                int serverIndex = updatedServer.getServerId() - 1;
                receptionist = receptionist.replaceServer(serverIndex, updatedServer);
                return new Pair<Event, Receptionist>(event, receptionist);
            }
        }
        return new Pair<Event, Receptionist>(this, receptionist);
    }
}
