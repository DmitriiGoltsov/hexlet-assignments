package exercise;

import exercise.connections.Connected;
import exercise.connections.Connection;
import exercise.connections.Disconnected;

import java.util.List;
import java.util.ArrayList;

// BEGIN
public class TcpConnection {

    private String ip;
    private int port;
    private Connection connection;
    private List<String> data;

    public TcpConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.connection = new Disconnected(this);
        this.data = new ArrayList<>();
    }

    public void connect() {
        if (connection instanceof Disconnected) {
            connection = new Connected(this);
        } else {
            System.out.println("Error! Connection is already established.");
        }
    }

    public void disconnect() {
        if (connection instanceof Connected) {
            connection = new Disconnected(this);
        } else {
            System.out.println("Error! Connection is already disconnected");
        }
    }

    public void write(String element) {
        if (this.connection instanceof Connected) {
            this.data.add(element);
        } else {
            System.out.println("Error. TcpConnection state is disconnected!");
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setCurrentState(Connection currentState) {
        this.connection = currentState;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(String element) {
        this.data.add(element);
    }

    public int getPort() {
        return port;
    }

    public void setState(Connection currentState) {
        this.connection = currentState;
    }

    public String getCurrentState() {
        if (this.connection instanceof Connected) {
            return "connected";
        } else {
            return "disconnected";
        }
    }
}
// END
