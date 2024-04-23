public class Arrival extends Event {

    Arrival(Customer customer, double time) {
        super(customer, time);
    }

    @Override
    String eventType() {
        return "arrival";
    }

    @Override
    public String toString() {
        return super.toString() + " arrives";
    }

    @Override
    public Pair<Event, Receptionist> next(Receptionist receptionist) {
        Server server = receptionist.firstFreeServer();  // assign firstFreeServer to receive cus
        int serverIndex = server.getServerId() - 1;
        if (!server.isResting() && server.getTime() <= getEventTime()) {
            Server newServer = server.changeTime(this.getEventTime());
            Server finalServer = newServer.addCustomer(this.getCustomer());
            receptionist = receptionist.replaceServer(serverIndex, finalServer);
            Event event = new Serve(this.getCustomer(), this.getEventTime(), finalServer);
            return new Pair<Event, Receptionist>(event, receptionist);
        } else {
            if (server.canQueue()) {
                Server newServer = server.addCustomer(this.getCustomer());
                Event event = new Wait(this.getCustomer(), this.getEventTime(), newServer, true);
                receptionist = receptionist.replaceServer(serverIndex, newServer);
                return new Pair<Event, Receptionist>(event, receptionist);      
            } else {
                Event event = new Departure(this.getCustomer(), this.getEventTime());
                return new Pair<Event, Receptionist>(event, receptionist);
            }
        }
    }
}  
