package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection {

    private TcpConnection connection;

    public Disconnected(TcpConnection connection) {
        this.connection = connection;
    }

    @Override
    public String getCurrentState() {
        return this.connection.getCurrentState();
    }

    @Override
    public void connect() {
        var connectionToChange = this.connection;
        connectionToChange.setState(new Connected(connectionToChange));
    }

    @Override
    public void disconnect() {
        System.out.println("Error! Connection already disconnected");
    }

    @Override
    public void write(String element) {
        System.out.println("Error! The data cannot be transferred"
                + " due to Disconnected state of TcpConnection.");
    }
}
// END
