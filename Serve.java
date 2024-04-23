public class Serve extends Event {
    private final Server server;
    
    Serve(Customer customer, double eventTime, Server server) {     
        super(customer, eventTime);
        this.server = server;
    }

    @Override
    public String toString() {
        return super.toString() + " serves by " + server.toString();
    }

    @Override
    String eventType() {
        return "serve";
    }

    @Override
    public Stats updateStats(Stats stats) {
        double waitTime = this.getEventTime() - this.getCustomer().getArrivalTime();
        return stats.waitChange(waitTime);
    }

    @Override
    public Pair<Event, Receptionist> next(Receptionist receptionist) {
        int serverIndex = server.getServerId() - 1;
        Server server = receptionist.getServers().get(serverIndex);
        Customer cust = this.getCustomer();
        double startTime = server.getTime();
        double time = cust.getServiceTime();
        double endTime = startTime + time;
        Server newServer = server.changeTime(endTime);
        receptionist = receptionist.replaceServer(serverIndex, newServer);
        Event event = new Done(cust, endTime, newServer);
        return new Pair<Event, Receptionist>(event, receptionist);
    }     
}

