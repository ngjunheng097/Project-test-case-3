// simulator should not be a God class, should not run the logic
// Let events and receptionist run the logic

// new stuff: numOfSelfChecks, restTime
// random resting
// all SelfCheckouts have the same queue
// what edits SelfCheckout queues?
// arrival adds, done removes
// edit receptionist states there such that the queue of all SelfCheckouts are the same

import java.util.function.Supplier;

public class Simulator {
    private final ImList<Customer> customerList;
    private final ImList<Server> serverList;

    public Simulator(int numOfServers, int numOfSelfChecks, int qmax, 
        ImList<Double> arrTime, Supplier<Double> sTime, Supplier<Double> restTime) {

        ImList<Customer> customerList = new ImList<Customer>();
        for (int i = 0; i < arrTime.size(); i++) {
            Customer cust = new Customer(i + 1, arrTime.get(i), sTime);
            customerList = customerList.add(cust);
        }
        this.customerList = customerList;

        ImList<Server> listOfServers = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {   
            Server server = new Server(new ImList<Customer>(), i + 1, qmax + 1, 0, 
                restTime, false, "");
            listOfServers = listOfServers.add(server);
        }
        for (int i = numOfServers; i < numOfServers + numOfSelfChecks; i++) {   
            SelfCheckout sco = new SelfCheckout(new ImList<Customer>(), i + 1, 
                qmax + 1, 0, restTime);
            listOfServers = listOfServers.add(sco);
        }
        this.serverList = listOfServers;
    }

    public String simulate() { 
        String output = "";
        Stats stats = new Stats(0,0,0);
        Receptionist receptionist = new Receptionist(this.serverList);
        PQ<Event> pq = new PQ<Event>(new EventPairComparator());

        for (int i = 0; i < this.customerList.size(); i++) {   // add all Arrival Events first
            Customer cust = this.customerList.get(i);
            double arrivalTime = cust.getArrivalTime();
            Arrival arriveEvent = new Arrival(cust, arrivalTime); 
            pq = pq.add(arriveEvent);
        }

        Pair<Event, PQ<Event>> pair;
        Event event;
        while (!pq.isEmpty()) {
            pair = pq.poll();
            event = pair.first();
            // removes event from pq, which is the first executed
            pq = pair.second();     

            Pair<Event, Receptionist> nextState = event.next(receptionist);
            Event nextEvent = nextState.first();
            receptionist = nextState.second();              // receptionist new state
            stats = event.updateStats(stats);               // update stats
            if (event.toString() != "") {
                output += event.toString() + "\n";
            }
            if (event.eventType() == "departure") {  // don't add Departure into PQ
                continue;
            } else if (event.eventType() == "restedAlr") {   // don't add DoneResting into PQ
                continue;
            } else {
                pq = pq.add(nextEvent);
            }
        }   
        output += stats.toString();
        return output;     
    }
}
