import java.util.function.Supplier;

// edit customer input into ImList<Customer>
// maxQueueSize should be a constant, which should be given in the new Main Class

public class Server {
    private final ImList<Customer> customers;
    private final Integer serverId;
    private final int maxQueueSize;
    private final double time;
    private final Supplier<Double> restTime;
    private final boolean isResting;
    private final String type;

    public Server(ImList<Customer> customers, Integer serverId, int maxQueueSize, 
        double time, Supplier<Double> restTime, boolean isResting, String type) {
        this.customers = customers;
        this.serverId = serverId;
        this.maxQueueSize = maxQueueSize;
        this.time = time;
        this.restTime = restTime;
        this.isResting = isResting;
        this.type = type;
    }

    public int getServerId() {
        return serverId;
    }

    String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + serverId.toString();
    }

    public ImList<Customer> getCustomers() {
        return customers;
    }

    public int getMaxQ() {
        return maxQueueSize;
    }

    public boolean isServing() {
        if (customers.isEmpty() || isResting) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canQueue() {
        if (customers.size() < this.maxQueueSize) {
            return true;
        }
        return false;
    }

    public double getTime() {
        return time;
    }
    
    public Server addCustomer(Customer newCust) {
        ImList<Customer> newCustList = customers.add(newCust);
        return new Server(newCustList, serverId, maxQueueSize, time, restTime, isResting, type); 
    }

    public Server changeTime(double newTime) {
        return new Server(customers, serverId, maxQueueSize, newTime, restTime, isResting, type);
    }

    public Server removeCustomer() {     // removes 1st customer, used when Done Event executed
        if (this.isServing()) {
            ImList<Customer> newCustomers = customers.remove(0);
            return new Server(newCustomers, serverId, maxQueueSize, 
                time, restTime, isResting, type);    
        }
        return this;
    }

    public double getRestTime() {
        return restTime.get();
    }

    public boolean isResting() {
        return isResting;
    }

    public Server workToRest() {
        if (!isResting) {       // working? go take a break!
            return new Server(customers, serverId, maxQueueSize - 1, time, restTime, true, type);
        } else {
            return this;
        }
    }

    public Server restToWork() {
        if (isResting) {        // resting? back to work!
            return new Server(customers, serverId, maxQueueSize + 1, time, restTime, false, type);
        } else {
            return this;
        }
    }
}

