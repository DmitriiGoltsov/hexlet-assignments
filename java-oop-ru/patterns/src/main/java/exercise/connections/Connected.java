package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection {

    private TcpConnection connection;

    public Connected(TcpConnection connection) {
        this.connection = connection;
    }

    @Override
    public String getCurrentState() {
        return this.connection.getCurrentState();
    }

    @Override
    public void connect() {
        System.out.println("Error! Connection already connected");
    }

    @Override
    public void disconnect() {
        var connectionToChange = this.connection;
        connectionToChange.setState(new Disconnected(connectionToChange));
    }

    @Override
    public void write(String element) {
        if (this.connection.getCurrentState().equals("connected")) {
            this.connection.setData(element);
        } else {
            System.out.println("Error! The data cannot be transferred"
                    + " due to Disconnected state of TcpConnection.");
        }
    }
}
// END
