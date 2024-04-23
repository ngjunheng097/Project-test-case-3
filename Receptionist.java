
public class Receptionist {
    private final ImList<Server> servers;

    public Receptionist(ImList<Server> servers) {
        this.servers = servers; 
    }

    public ImList<Server> getServers() {
        return servers;
    }

    public Receptionist replaceServer(int oldServerIndex, Server newServer) {
        ImList<Server> newServerList = servers.set(oldServerIndex, newServer);
        return new Receptionist(newServerList);
    }

    public Server firstFreeServer() {       // take into account rest time?
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (!server.isServing() && !server.isResting()) {   // !serving!resting, resting!serving
                return server;
            }
        }
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (server.canQueue()) {
                return server;
            }
        }
        return servers.get(0);  // depart
    }

    @Override
    public String toString() {
        return servers.toString();
    }
}
