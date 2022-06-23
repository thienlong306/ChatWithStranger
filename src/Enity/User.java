package Enity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
    private String username;
    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public User(Socket client, String username, ObjectInputStream ois, ObjectOutputStream oos) {
        this.client = client;
        this.username = username;
        this.ois = ois;
        this.oos = oos;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }
}
