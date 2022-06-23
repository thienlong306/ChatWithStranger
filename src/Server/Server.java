package Server;

import Enity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static boolean status=true;
    public static ServerSocket svr = null;
    private static int PORT = 52233;
    private static Socket cl;

    public static ArrayList<RoomServer> listRoom=new ArrayList<RoomServer>();
    public static ArrayList<User> listWait=new ArrayList<User>();
    public static ArrayList<String> listUser=new ArrayList<String>();
    public Server(){
        try {
            svr=new ServerSocket(PORT);
            while (status) {
                cl = svr.accept();
                new HandlerClient(cl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
