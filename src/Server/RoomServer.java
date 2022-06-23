package Server;

import Enity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static Server.Server.listUser;
import static Server.Server.listWait;

public class RoomServer extends Thread {
    private Socket cl1 = null;
    private Socket cl2 = null;
    private String username1 = null;
    private String username2 = null;
    private ObjectInputStream ois1 = null;
    private ObjectInputStream ois2 = null;
    private ObjectOutputStream oos1 = null;
    private ObjectOutputStream oos2 = null;
    private User u1 = null;
    private User u2 = null;
    private boolean status=true;
    public RoomServer(User u1, User u2) {
        this.cl1 = u1.getClient();
        this.cl2 = u2.getClient();
        this.username1 = u1.getUsername();
        this.username2 = u2.getUsername();
        this.ois1 = u1.getOis();
        this.ois2 = u2.getOis();
        this.oos1 = u1.getOos();
        this.oos2 = u2.getOos();
        this.u1 = u1;
        this.u2 = u2;
        start();
    }

    public void run() {
        try {
            oos1.writeObject("893e101a9e5e6486c713830c915383d8");
            oos1.writeObject("Bắt đầu chat với:" + username2);
            listen();
        } catch (IOException e) {
            try {
                System.out.println(username1 + " Mất kết nối");
                oos2.writeObject("724a9f13c8569552344c0648b74e784f");
                oos2.writeObject(username1 + " Mất kết nối");
                listUser.remove(username1);
                listUser.remove(username2);
            } catch (IOException ioException) {
                listUser.remove(username2);
            }
        }
    }

    private void listen() {
        try {
            while (status) {
                String text = "Hello";
                Thread.sleep(1);
                text = (String) ois1.readObject();
                send(text);
            }
        } catch (IOException | ClassNotFoundException e) {
            try {
                System.out.println(username1 + " Mất kết nối");
                oos2.writeObject("724a9f13c8569552344c0648b74e784f");
                oos2.writeObject(username1 + " Mất kết nối");
                listUser.remove(username1);
                listUser.remove(username2);
            } catch (IOException ioException) {
                listUser.remove(username2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void send(String text) {
        try {
            oos2.writeObject(username1 + ":" + text);
            oos2.flush();
        } catch (IOException e) {
//            Disconnect();
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
